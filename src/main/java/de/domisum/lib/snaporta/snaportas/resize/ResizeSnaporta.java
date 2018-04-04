package de.domisum.lib.snaporta.snaportas.resize;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.snaportas.resize.interpolator.Interpolator;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public final class ResizeSnaporta implements Snaporta
{

	// ATTRIBUTES
	@Getter private final int width;
	@Getter private final int height;

	// REFERENCES
	private final Snaporta baseSnaporta;

	// SETTINGS
	private final Interpolator interpolator;


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
