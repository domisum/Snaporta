package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.datacontainers.bound.IntBounds2D;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.snaportas.GeneratedSnaporta;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class CropToContentSnaporta
	extends GeneratedSnaporta
{
	
	private final Snaporta base;
	private final double thresholdOpacity;
	
	
	// INIT
	@API
	public CropToContentSnaporta(Snaporta base)
	{
		this(base, 0);
	}
	
	
	// SNAPORTA
	@Override
	protected Snaporta generate()
	{
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		int contentMinAlpha = (int) Math.round(thresholdOpacity * Color.COLOR_COMPONENT_MAX);
		for(int y = 0; y < base.getHeight(); y++)
			for(int x = 0; x < base.getWidth(); x++)
				if(base.getAlphaAt(x, y) > contentMinAlpha)
				{
					if(x < minX)
						minX = x;
					if(x > maxX)
						maxX = x;
					if(y < minY)
						minY = y;
					if(y > maxY)
						maxY = y;
				}
		
		if(minX == Integer.MAX_VALUE || minY == Integer.MAX_VALUE)
			return base;
		var bounds = IntBounds2D.fromBounds(minX, maxX, minY, maxY);
		return ViewportSnaporta.boundsSubsection(base, bounds);
	}
	
}
