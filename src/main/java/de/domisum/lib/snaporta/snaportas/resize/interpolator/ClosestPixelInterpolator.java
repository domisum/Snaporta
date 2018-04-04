package de.domisum.lib.snaporta.snaportas.resize.interpolator;

import de.domisum.lib.snaporta.Snaporta;

public class ClosestPixelInterpolator implements Interpolator
{

	@Override public int interpolateARGBAt(Snaporta snaporta, double x, double y)
	{
		int closestPixelX = (int) Math.round(x);
		int closestPixelY = (int) Math.round(y);

		return snaporta.getARGBAt(closestPixelX, closestPixelY);
	}

}
