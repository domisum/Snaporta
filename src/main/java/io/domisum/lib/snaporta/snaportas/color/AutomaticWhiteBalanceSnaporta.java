package io.domisum.lib.snaporta.snaportas.color;

import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.color.ColorComponent;
import io.domisum.lib.snaporta.snaportas.GeneratedSnaporta;
import io.domisum.lib.snaporta.snaportas.SnaportaPainter;
import io.domisum.lib.snaporta.util.ArgbUtil;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AutomaticWhiteBalanceSnaporta
	extends GeneratedSnaporta
{
	
	private final Snaporta baseSnaporta;
	
	
	// GENERATE
	@Override
	protected Snaporta generate()
	{
		var componentBounds = getComponentBounds();
		
		var painter = new SnaportaPainter(baseSnaporta.getWidth(), baseSnaporta.getHeight());
		for(int y = 0; y < baseSnaporta.getHeight(); y++)
			for(int x = 0; x < baseSnaporta.getWidth(); x++)
			{
				int baseArgb = baseSnaporta.getArgbAt(x, y);
				int newAlpha = ArgbUtil.getAlphaComponent(baseArgb);
				
				int newRed = adjustComponent(baseArgb, componentBounds, ColorComponent.RED);
				int newGreen = adjustComponent(baseArgb, componentBounds, ColorComponent.GREEN);
				int newBlue = adjustComponent(baseArgb, componentBounds, ColorComponent.BLUE);
				
				int newArgb = ArgbUtil.toArgb(newAlpha, newRed, newGreen, newBlue);
				painter.setARGBAt(x, y, newArgb);
			}
		
		return painter.toSnaporta();
	}
	
	private Map<ColorComponent, int[]> getComponentBounds()
	{
		final double ignorePercentage = 0.05;
		
		var componentBounds = new HashMap<ColorComponent, int[]>();
		for(var component : ColorComponent.values())
		{
			if(component == ColorComponent.ALPHA)
				continue;
			
			int[] values = new int[baseSnaporta.getWidth()*baseSnaporta.getHeight()];
			for(int y = 0; y < baseSnaporta.getHeight(); y++)
				for(int x = 0; x < baseSnaporta.getWidth(); x++)
				{
					int index = y*baseSnaporta.getWidth()+x;
					values[index] = ArgbUtil.getComponent(component, baseSnaporta.getArgbAt(x, y));
				}
			
			Arrays.sort(values);
			int lowestConsideredIndex = (int) Math.floor(values.length*ignorePercentage/(double) 100);
			int highestConsideredIndex = (int) Math.floor(values.length*(1-ignorePercentage/(double) 100));
			
			int lowest = values[lowestConsideredIndex];
			int highest = values[highestConsideredIndex];
			componentBounds.put(component, new int[] {lowest, highest});
		}
		
		return componentBounds;
	}
	
	private int adjustComponent(int baseArgb, Map<ColorComponent, int[]> componentBounds, ColorComponent colorComponent)
	{
		int baseComponent = ArgbUtil.getComponent(colorComponent, baseArgb);
		int[] bounds = componentBounds.get(colorComponent);
		
		if(baseComponent <= bounds[0])
			return 0;
		else if(baseComponent >= bounds[1])
			return Color.COLOR_COMPONENT_MAX;
		
		double oldRange = bounds[1]-bounds[0];
		double newComponentDouble = (baseComponent-bounds[0])/oldRange*Color.COLOR_COMPONENT_MAX;
		return (int) Math.floor(newComponentDouble);
	}
	
}
