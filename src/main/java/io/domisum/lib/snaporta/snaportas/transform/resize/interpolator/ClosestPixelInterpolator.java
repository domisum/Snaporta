package io.domisum.lib.snaporta.snaportas.transform.resize.interpolator;

import io.domisum.lib.snaporta.Snaporta;

public class ClosestPixelInterpolator
	implements Interpolator
{
	
	@Override
	public int interpolateARGBAt(Snaporta snaporta, double x, double y)
	{
		int closestPixelX = (int) Math.floor(x);
		int closestPixelY = (int) Math.floor(y);
		
		int argb = snaporta.getArgbAt(closestPixelX, closestPixelY);
		return argb;
	}
	
}
