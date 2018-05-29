package de.domisum.lib.snaporta.snaportas.transform.resize;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.snaportas.transform.resize.interpolator.ClosestPixelInterpolator;
import de.domisum.lib.snaporta.snaportas.transform.resize.interpolator.Interpolator;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public final class ResizedSnaporta implements Snaporta
{

	// DEFAULTS
	private static final Interpolator DEFAULT_INTERPOLATOR = new ClosestPixelInterpolator();

	// ATTRIBUTES
	@Getter private final int width;
	@Getter private final int height;

	// REFERENCES
	private final Snaporta baseSnaporta;
	private final Interpolator interpolator;


	// INIT
	public ResizedSnaporta(int width, int height, Snaporta baseSnaporta)
	{
		this(width, height, baseSnaporta, DEFAULT_INTERPOLATOR);
	}


	// SNAPORTA
	@Override public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		double relativeX = x/(double) width;
		double relativeY = y/(double) height;

		double inChildX = baseSnaporta.getWidth()*relativeX;
		double inChildY = baseSnaporta.getHeight()*relativeY;
		return interpolator.interpolateARGBAt(baseSnaporta, inChildX, inChildY);
	}

}
