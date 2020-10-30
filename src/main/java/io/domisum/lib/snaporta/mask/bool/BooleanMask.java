package io.domisum.lib.snaporta.mask.bool;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.datacontainers.bound.IntBounds2D;
import io.domisum.lib.snaporta.util.Sized;

public interface BooleanMask
	extends Sized
{
	
	boolean getValueAt(int x, int y);
	
	
	// UTIL
	default IntBounds2D getBounds()
	{
		Integer minX = null;
		Integer maxX = null;
		Integer minY = null;
		Integer maxY = null;
		
		for(int y = 0; y < getHeight(); y++)
			for(int x = 0; x < getWidth(); x++)
				if(getValueAt(x, y))
				{
					minX = minX == null ? x : Math.min(minX, x);
					maxX = maxX == null ? x : Math.max(maxX, x);
					minY = minY == null ? y : Math.min(minY, y);
					maxY = maxY == null ? y : Math.max(maxY, y);
				}
		
		if(minX == null)
			throw new IllegalStateException(PHR.r("This {} only contains false, can't determine bounds",
				getClass().getSimpleName()));
		
		return IntBounds2D.fromBounds(minX, maxX, minY, maxY);
	}
	
}
