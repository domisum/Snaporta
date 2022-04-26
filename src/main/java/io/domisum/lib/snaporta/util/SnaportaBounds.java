package io.domisum.lib.snaporta.util;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.datacontainers.bound.IntBounds2D;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaBounds
{
	
	@API
	public static IntBounds2D getOpaqueSquareBounds(Snaporta icon)
	{
		var bounds = SnaportaBounds.getOpaqueBounds(icon);
		
		if(bounds.getWidth() < bounds.getHeight())
		{
			int diff = bounds.getWidth()-bounds.getHeight();
			bounds = bounds.expandLeft((int) Math.floor(diff/2d));
			bounds = bounds.expandRight((int) Math.ceil(diff/2d));
		}
		
		if(bounds.getHeight() < bounds.getWidth())
		{
			int diff = bounds.getHeight()-bounds.getWidth();
			bounds = bounds.expandTop((int) Math.floor(diff/2d));
			bounds = bounds.expandBottom((int) Math.ceil(diff/2d));
		}
		
		return bounds;
	}
	
	@API
	public static IntBounds2D getOpaqueBounds(Snaporta snaporta)
	{
		int minX = snaporta.getWidth();
		int maxX = -1;
		int minY = snaporta.getHeight();
		int maxY = -1;
		for(int y = 0; y < snaporta.getHeight(); y++)
			for(int x = 0; x < snaporta.getWidth(); x++)
				if(snaporta.getAlphaAt(x, y) != Color.ALPHA_TRANSPARENT)
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
		
		return IntBounds2D.fromBounds(minX, maxX, minY, maxY);
	}
	
}
