package io.domisum.lib.snaporta.snaportas;

import com.google.common.collect.Iterables;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.color.ColorComponent;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.ARGBUtil;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@API
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
	public LayeredSnaporta(Snaporta bottomMostLayer, Snaporta... layersBottomUp)
	{
		this(bottomMostLayer.getWidth(), bottomMostLayer.getHeight());
		
		addLayerOnTop(bottomMostLayer);
		for(var component : layersBottomUp)
			addLayerOnTop(component);
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
		
		int argbAt = getARGBAtDepth(x, y, 0);
		return argbAt;
	}
	
	
	// COLOR MIXING
	private int getARGBAtDepth(int x, int y, int depth)
	{
		if(depth >= layersTopDown.size())
			return Colors.TRANSPARENT.toARGBInt();
		
		var component = layersTopDown.get(depth);
		int componentARGB = component.getARGBAt(x, y);
		
		
		if(ARGBUtil.getAlphaComponent(componentARGB) == Color.ALPHA_OPAQUE)
			return componentARGB;
		
		int backgroundARGB = getARGBAtDepth(x, y, depth+1);
		if(ARGBUtil.getAlphaComponent(componentARGB) == Color.ALPHA_TRANSPARENT)
			return backgroundARGB;
		
		int argb = mixARGB(backgroundARGB, componentARGB);
		return argb;
	}
	
	private int mixARGB(int background, int foreground)
	{
		if(ARGBUtil.getAlphaComponent(background) == Color.ALPHA_TRANSPARENT)
			return foreground;
		
		double redCombined = getColorComponentCombined(ColorComponent.RED, background, foreground);
		double greenCombined = getColorComponentCombined(ColorComponent.GREEN, background, foreground);
		double blueCombined = getColorComponentCombined(ColorComponent.BLUE, background, foreground);
		
		double foregroundOpacity = ARGBUtil.getOpacity(foreground);
		double backgroundOpacity = ARGBUtil.getOpacity(background);
		double opacityCombined = foregroundOpacity+((1-foregroundOpacity)*backgroundOpacity);
		
		int argb = ARGBUtil.toARGB(
			ARGBUtil.getAlphaFromOpacity(opacityCombined),
			(int) Math.round(redCombined),
			(int) Math.round(greenCombined),
			(int) Math.round(blueCombined));
		
		return argb;
	}
	
	private static double getColorComponentCombined(ColorComponent colorComponent, int background, int foreground)
	{
		double foregroundOpacity = ARGBUtil.getOpacity(foreground);
		double combined = MathUtil.mix(
			ARGBUtil.getComponent(colorComponent, foreground), foregroundOpacity,
			ARGBUtil.getComponent(colorComponent, background), 1-foregroundOpacity);
		
		return combined;
	}
	
	
	// LAYER
	@RequiredArgsConstructor
	public static class Layer
		implements Comparable<Layer>
	{
		
		private final Snaporta snaporta;
		private final int x;
		private final int y;
		
		@Getter
		private final double z;
		
		
		// COMPARE
		@Override
		public int compareTo(@Nonnull Layer other)
		{
			return Comparator.comparing(Layer::getZ).reversed().compare(this, other);
		}
		
		
		// COMPONENT
		public int getARGBAt(int x, int y)
		{
			int inSnaportaX = x-this.x;
			int inSnaportaY = y-this.y;
			
			if(snaporta.isInBounds(inSnaportaX, inSnaportaY))
				return snaporta.getARGBAt(inSnaportaX, inSnaportaY);
			else
				return Colors.TRANSPARENT.toARGBInt();
		}
		
	}
	
}
