package io.domisum.lib.snaporta.snaportas.composite;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@API
public class CompositeSnaporta
		implements Snaporta
{
	
	// ATTRIBUTES
	@Getter
	private final int width;
	@Getter
	private final int height;
	
	// COMPONENTS
	private final List<CompositeSnaportaComponent> componentsTopDown = new ArrayList<>();
	
	
	// INIT
	public CompositeSnaporta(Snaporta bottomMostComponent, Snaporta... componentsBottomUp)
	{
		this(bottomMostComponent.getWidth(), bottomMostComponent.getHeight());
		
		addComponentOnTop(bottomMostComponent);
		for(var component : componentsBottomUp)
			addComponentOnTop(component);
	}
	
	
	// COMPONENTS
	@API
	public void setComponentOnZ(Snaporta snaporta, int x, int y, double z)
	{
		var component = new CompositeSnaportaComponent(snaporta, x, y, z);
		componentsTopDown.removeIf(c->c.getZ() == component.getZ());
		addComponent(component);
	}
	
	@API
	public void addComponent(CompositeSnaportaComponent component)
	{
		validateUniqueZ(component);
		
		componentsTopDown.add(component);
		Collections.sort(componentsTopDown);
	}
	
	@API
	public void addComponentOnTop(Snaporta snaporta)
	{
		addComponentOnTop(snaporta, 0, 0);
	}
	
	@API
	public void addComponentOnTop(Snaporta snaporta, int x, int y)
	{
		var maxZ = componentsTopDown.stream().mapToDouble(CompositeSnaportaComponent::getZ).max();
		double onTopZ = maxZ.isPresent() ? (maxZ.getAsDouble()+1) : 0;
		
		var component = new CompositeSnaportaComponent(snaporta, x, y, onTopZ);
		addComponent(component);
	}
	
	@API
	public void addComponentBelow(Snaporta snaporta, int x, int y)
	{
		double zBelow = componentsTopDown.isEmpty() ? -1 : Iterables.getLast(componentsTopDown).getZ()-1;
		setComponentOnZ(snaporta, x, y, zBelow);
	}
	
	private void validateUniqueZ(CompositeSnaportaComponent component)
	{
		boolean uniqueZ = componentsTopDown.stream().noneMatch(c->c.getZ() == component.getZ());
		Validate.isTrue(uniqueZ, "Component z values have to be unique, already have component with z="+component.getZ());
	}
	
	public int getNumberOfComponents()
	{
		return componentsTopDown.size();
	}
	
	
	// SNAPORTA
	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return getARGBAtDepth(x, y, 0);
	}
	
	
	// COLOR MIXING
	private int getARGBAtDepth(int x, int y, int depth)
	{
		if(depth >= componentsTopDown.size())
			return Colors.TRANSPARENT.toARGBInt();
		
		var component = componentsTopDown.get(depth);
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
	
}
