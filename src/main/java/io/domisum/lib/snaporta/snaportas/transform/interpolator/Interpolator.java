package io.domisum.lib.snaporta.snaportas.transform.interpolator;

import io.domisum.lib.snaporta.Snaporta;

public interface Interpolator
{
	
	/**
	 * Interpolate the pixel color value at the absolute, but decimal coordinates specified.
	 *
	 * @param snaporta the image to take values from
	 * @param x        the absolute x coordinate
	 * @param y        the absolute y coordinate
	 * @return the interpolated color as an ARGB int
	 */
	int interpolateARGBAt(Snaporta snaporta, double x, double y);
	
}
