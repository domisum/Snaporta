package de.domisum.lib.snaporta;

import de.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

public final class BasicEditableSnaporta implements EditableSnaporta
{

	/**
	 * Pixels represented as a 2D array of ARGB values. 2D array: array of horizontal rows of pixels.
	 */
	private final int[][] argbPixels;


	// INIT
	public static BasicEditableSnaporta fromARGBPixels(int[][] argbPixels)
	{
		return new BasicEditableSnaporta(argbPixels);
	}

	private BasicEditableSnaporta(int[][] argbPixels)
	{
		Validate.isTrue(argbPixels.length > 0, "snaporta has to have a minimum height of 1");
		Validate.isTrue(argbPixels[0].length > 0, "snaporta has to have a minimum width of 1");

		this.argbPixels = argbPixels; // TODO do deep copy to prevent outside access
	}


	// SNAPORTA
	@Override public int getWidth()
	{
		return argbPixels[0].length;
	}

	@Override public int getHeight()
	{
		return argbPixels.length;
	}

	@Override public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		return argbPixels[y][x];
	}


	// EDITABLE SNAPORTA
	@Override public void setARGBAt(int x, int y, int argb)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		argbPixels[y][x] = argb;
	}

}
