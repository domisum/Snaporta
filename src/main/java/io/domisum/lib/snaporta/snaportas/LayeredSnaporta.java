package io.domisum.lib.snaporta.snaportas;

import com.google.common.collect.Iterables;
import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.contracts.SmartComparatorComparable;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.color.ColorComponent;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.ArgbUtil;
import io.domisum.lib.snaporta.util.Sized;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.util.*;

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
	
	
	// HOUSEKEEPING
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
				+ "when empty list of layers is given");
		
		int maxWidth = -1;
		int maxHeight = -1;
		
		for(var snaporta : layersBottomUp)
		{
			maxWidth = Math.max(maxWidth, snaporta.getWidth());
			maxHeight = Math.max(maxHeight, snaporta.getHeight());
		}
		
		return Sized.sized(maxWidth, maxHeight);
	}
	
	@Override
	public String toString()
	{
		var innerSb = new StringBuilder();
		for(var layer : layersTopDown)
			innerSb.append("\n").append(layer.snaporta.toString());
		String inner = innerSb.length() > 0
			? StringUtil.indent(innerSb.toString(), "\t")
			: " <empty>";
		return PHR.r("{}(w={} x h={}{})", getClass().getSimpleName(),
			width, height, inner);
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
		layersTopDown.removeIf(c -> c.getZ() == component.getZ());
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
		if(snaporta.isBlank())
			return;
		
		var maxZ = layersTopDown.stream().mapToDouble(Layer::getZ).max();
		double onTopZ = maxZ.isPresent() ? (maxZ.getAsDouble() + 1) : 0;
		
		var component = new Layer(snaporta, x, y, onTopZ);
		addLayer(component);
	}
	
	@API
	public void addLayerBelow(Snaporta snaporta, int x, int y)
	{
		double zBelow = layersTopDown.isEmpty() ? -1 : Iterables.getLast(layersTopDown).getZ() - 1;
		setLayerOnZ(snaporta, x, y, zBelow);
	}
	
	private void validateUniqueZ(Layer component)
	{
		boolean uniqueZ = layersTopDown.stream().noneMatch(c -> c.getZ() == component.getZ());
		Validate.isTrue(uniqueZ, "Layer z values have to be unique, already have a layer with z=" + component.getZ());
	}
	
	
	// SNAPORTA
	@Override
	public int getArgbAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return getArgbAtDepth(x, y, 0);
	}
	
	@Override
	public boolean isBlank()
	{
		if(layersTopDown.isEmpty())
			return true;
		return Snaporta.super.isBlank();
	}
	
	
	// COLOR MIXING
	private int getArgbAtDepth(int x, int y, int depth)
	{
		if(depth >= layersTopDown.size())
			return Colors.TRANSPARENT.toARGBInt();
		
		var componentAtThisDepth = layersTopDown.get(depth);
		int componentAtThisDepthArgbAt = componentAtThisDepth.getARGBAt(x, y);
		
		if(ArgbUtil.getAlphaComponent(componentAtThisDepthArgbAt) == Color.ALPHA_OPAQUE)
			return componentAtThisDepthArgbAt;
		
		int backgroundArgbAt = getArgbAtDepth(x, y, depth + 1);
		if(ArgbUtil.getAlphaComponent(componentAtThisDepthArgbAt) == Color.ALPHA_TRANSPARENT)
			return backgroundArgbAt;
		
		return mixArgb(backgroundArgbAt, componentAtThisDepthArgbAt);
	}
	
	private int mixArgb(int backgroundArgb, int foregroundArgb)
	{
		if(ArgbUtil.getAlphaComponent(backgroundArgb) == Color.ALPHA_TRANSPARENT)
			return foregroundArgb;
		
		int redCombined = getColorComponentCombined(ColorComponent.RED, backgroundArgb, foregroundArgb);
		int greenCombined = getColorComponentCombined(ColorComponent.GREEN, backgroundArgb, foregroundArgb);
		int blueCombined = getColorComponentCombined(ColorComponent.BLUE, backgroundArgb, foregroundArgb);
		
		double foregroundTransparency = 1 - ArgbUtil.getOpacity(foregroundArgb);
		double backgroundTransparency = 1 - ArgbUtil.getOpacity(backgroundArgb);
		double transparencyCombined = foregroundTransparency * backgroundTransparency;
		int alphaCombined = ArgbUtil.getAlphaFromOpacity(1 - transparencyCombined);
		
		return ArgbUtil.toArgb(alphaCombined, redCombined, greenCombined, blueCombined);
	}
	
	private static int getColorComponentCombined(ColorComponent colorComponent, int background, int foreground)
	{
		int foregroundComponent = ArgbUtil.getComponent(colorComponent, foreground);
		double foregroundOpacity = ArgbUtil.getOpacity(foreground);
		
		int backgroundComponent = ArgbUtil.getComponent(colorComponent, background);
		double backgroundOpacity = ArgbUtil.getOpacity(background);
		
		double mixed = MathUtil.mix(
			foregroundComponent, foregroundOpacity,
			backgroundComponent, (1 - foregroundOpacity) * backgroundOpacity);
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
			int inSnaportaX = x - this.x;
			int inSnaportaY = y - this.y;
			
			// not using in bounds method for performance reasons
			if((inSnaportaX >= 0) && (inSnaportaX < snaporta.getWidth()))
				if((inSnaportaY >= 0) && (inSnaportaY < snaporta.getHeight()))
					return snaporta.getArgbAt(inSnaportaX, inSnaportaY);
			
			return Colors.TRANSPARENT.toARGBInt();
		}
		
	}
	
}
