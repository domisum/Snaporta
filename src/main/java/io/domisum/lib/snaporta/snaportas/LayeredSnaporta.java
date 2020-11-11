package io.domisum.lib.snaporta.snaportas;

import com.google.common.collect.Iterables;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.contracts.SmartComparatorComparable;
import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.color.ColorComponent;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.ARGBUtil;
import io.domisum.lib.snaporta.util.Sized;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@API
@RequiredArgsConstructor
public class LayeredSnaporta
	implements Snaporta
{
	
	// ATTRIBUTES
	@Getter
	private final int width;
	@Getter
	private final int height;
	
	// COMPONENTS
	private final List<Layer> layersTopDown = new ArrayList<>();
	
	
	// INIT
	public LayeredSnaporta(List<Snaporta> layersBottomUp)
	{
		this(getDimensions(layersBottomUp).getWidth(), getDimensions(layersBottomUp).getHeight());
		
		for(var component : layersBottomUp)
			addLayerOnTop(component);
	}
	
	public LayeredSnaporta(Snaporta... layersBottomUp)
	{
		this(Arrays.asList(layersBottomUp));
	}
	
	private static Sized getDimensions(List<Snaporta> layersBottomUp)
	{
		if(layersBottomUp.isEmpty())
			throw new IllegalArgumentException("Can't determine dimensions of layered snaporta "
				+"when empty list of layers is given");
		
		return layersBottomUp.get(0);
	}
	
	
	// GETTERS
	@API
	public int getNumberOfLayers()
	{
		return layersTopDown.size();
	}
	
	
	// LAYERS
	@API
	public void setLayerOnZ(Snaporta snaporta, int x, int y, double z)
	{
		var component = new Layer(snaporta, x, y, z);
		layersTopDown.removeIf(c->c.getZ() == component.getZ());
		addLayer(component);
	}
	
	@API
	public void addLayer(Layer layer)
	{
		validateUniqueZ(layer);
		
		layersTopDown.add(layer);
		Collections.sort(layersTopDown);
	}
	
	@API
	public void addLayerOnTop(Snaporta snaporta)
	{
		addLayerOnTop(snaporta, 0, 0);
	}
	
	@API
	public void addLayerOnTop(Snaporta snaporta, int x, int y)
	{
		var maxZ = layersTopDown.stream().mapToDouble(Layer::getZ).max();
		double onTopZ = maxZ.isPresent() ? (maxZ.getAsDouble()+1) : 0;
		
		var component = new Layer(snaporta, x, y, onTopZ);
		addLayer(component);
	}
	
	@API
	public void addLayerBelow(Snaporta snaporta, int x, int y)
	{
		double zBelow = layersTopDown.isEmpty() ? -1 : Iterables.getLast(layersTopDown).getZ()-1;
		setLayerOnZ(snaporta, x, y, zBelow);
	}
	
	private void validateUniqueZ(Layer component)
	{
		boolean uniqueZ = layersTopDown.stream().noneMatch(c->c.getZ() == component.getZ());
		Validate.isTrue(uniqueZ, "Layer z values have to be unique, already have a layer with z="+component.getZ());
	}
	
	
	// SNAPORTA
	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return getArgbAtDepth(x, y, 0);
	}
	
	
	// COLOR MIXING
	private int getArgbAtDepth(int x, int y, int depth)
	{
		if(depth >= layersTopDown.size())
			return Colors.TRANSPARENT.toARGBInt();
		
		var component = layersTopDown.get(depth);
		int componentARGB = component.getARGBAt(x, y);
		
		if(ARGBUtil.getAlphaComponent(componentARGB) == Color.ALPHA_OPAQUE)
			return componentARGB;
		
		int backgroundARGB = getArgbAtDepth(x, y, depth+1);
		if(ARGBUtil.getAlphaComponent(componentARGB) == Color.ALPHA_TRANSPARENT)
			return backgroundARGB;
		
		return mixArgb(backgroundARGB, componentARGB);
	}
	
	private int mixArgb(int backgroundArgb, int foregroundArgb)
	{
		if(ARGBUtil.getAlphaComponent(backgroundArgb) == Color.ALPHA_TRANSPARENT)
			return foregroundArgb;
		
		int redCombined = getColorComponentCombined(ColorComponent.RED, backgroundArgb, foregroundArgb);
		int greenCombined = getColorComponentCombined(ColorComponent.GREEN, backgroundArgb, foregroundArgb);
		int blueCombined = getColorComponentCombined(ColorComponent.BLUE, backgroundArgb, foregroundArgb);
		
		double foregroundOpacity = ARGBUtil.getOpacity(foregroundArgb);
		double backgroundOpacity = ARGBUtil.getOpacity(backgroundArgb);
		double opacityCombined = foregroundOpacity+((1-foregroundOpacity)*backgroundOpacity);
		int alphaCombined = ARGBUtil.getAlphaFromOpacity(opacityCombined);
		
		return ARGBUtil.toARGB(alphaCombined, redCombined, greenCombined, blueCombined);
	}
	
	private static int getColorComponentCombined(ColorComponent colorComponent, int background, int foreground)
	{
		double foregroundOpacity = ARGBUtil.getOpacity(foreground);
		double mixed = MathUtil.mix(
			ARGBUtil.getComponent(colorComponent, foreground), foregroundOpacity,
			ARGBUtil.getComponent(colorComponent, background), 1-foregroundOpacity);
		
		return (int) Math.round(mixed);
	}
	
	
	// LAYER
	@RequiredArgsConstructor
	public static class Layer
		implements SmartComparatorComparable<Layer>
	{
		
		private final Snaporta snaporta;
		private final int x;
		private final int y;
		
		@Getter
		private final double z;
		
		
		// CONSTANT METHODS
		@Override
		public Comparator<Layer> COMPARATOR()
		{
			return Comparator.comparing(Layer::getZ).reversed();
		}
		
		
		// COMPONENT
		public int getARGBAt(int x, int y)
		{
			int inSnaportaX = x-this.x;
			int inSnaportaY = y-this.y;
			
			if(snaporta.isOutOfBounds(inSnaportaX, inSnaportaY))
				return Colors.TRANSPARENT.toARGBInt();
			
			return snaporta.getARGBAt(inSnaportaX, inSnaportaY);
		}
		
	}
	
}
