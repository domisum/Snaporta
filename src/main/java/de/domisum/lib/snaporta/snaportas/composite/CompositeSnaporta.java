package de.domisum.lib.snaporta.snaportas.composite;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.auxilium.util.math.MathUtil;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.color.ColorComponent;
import de.domisum.lib.snaporta.color.Colors;
import de.domisum.lib.snaporta.util.ARGBUtil;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

@RequiredArgsConstructor
@API
public class CompositeSnaporta implements Snaporta
{

	// ATTRIBUTES
	@Getter private final int width;
	@Getter private final int height;

	// COMPONENTS
	private final List<CompositeSnaportaComponent> componentsTopDown = new ArrayList<>();


	// COMPONENTS
	@API public void addComponent(CompositeSnaportaComponent component)
	{
		validateUniqueZ(component);

		componentsTopDown.add(component);
		Collections.sort(componentsTopDown);
	}

	@API public void addComponentOnTop(Snaporta snaporta)
	{
		addComponentOnTop(snaporta, 0, 0);
	}

	@API public void addComponentOnTop(Snaporta snaporta, int x, int y)
	{
		OptionalDouble maxZ = componentsTopDown.stream().mapToDouble(CompositeSnaportaComponent::getZ).max();
		double onTopZ = maxZ.isPresent() ? (maxZ.getAsDouble()+1) : 0;

		CompositeSnaportaComponent component = new CompositeSnaportaComponent(snaporta, x, y, onTopZ);
		addComponent(component);
	}

	private void validateUniqueZ(CompositeSnaportaComponent component)
	{
		boolean uniqueZ = componentsTopDown.stream().noneMatch(c->c.getZ() == component.getZ());
		Validate.isTrue(uniqueZ, "component z values have to be unique, already have component with z="+component.getZ());
	}


	// SNAPORTA
	@Override public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return getARGBAtDepth(x, y, 0);
	}


	// COLOR MIXING
	private int getARGBAtDepth(int x, int y, int depth)
	{
		if(depth >= componentsTopDown.size())
			return Colors.TRANSPARENT.toARGBInt();

		CompositeSnaportaComponent component = componentsTopDown.get(depth);
		int componentARGB = component.getARGBAt(x, y);


		if(ARGBUtil.getAlphaComponent(componentARGB) == Color.ALPHA_OPAQUE)
			return componentARGB;

		int backgroundARGB = getARGBAtDepth(x, y, depth+1);
		if(ARGBUtil.getAlphaComponent(componentARGB) == Color.ALPHA_TRANSPARENT)
			return backgroundARGB;

		return mixARGB(backgroundARGB, componentARGB);
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


		return ARGBUtil.toARGB(
				ARGBUtil.getAlphaFromOpacity(opacityCombined),
				(int) Math.round(redCombined),
				(int) Math.round(greenCombined),
				(int) Math.round(blueCombined)
		);
	}

	private static double getColorComponentCombined(ColorComponent colorComponent, int background, int foreground)
	{
		double foregroundOpacity = ARGBUtil.getOpacity(foreground);

		return MathUtil.mix(
				ARGBUtil.getComponent(colorComponent, foreground),
				foregroundOpacity,
				ARGBUtil.getComponent(colorComponent, background),
				1-foregroundOpacity
		);
	}

}
