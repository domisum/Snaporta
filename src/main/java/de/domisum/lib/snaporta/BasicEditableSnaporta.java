package de.domisum.lib.snaporta;

import de.domisum.lib.auxilium.util.PHR;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;

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
		// validate minimum dimension
		Validate.isTrue(argbPixels.length > 0, "snaporta has to have a minimum height of 1");
		Validate.noNullElements(argbPixels);
		Validate.isTrue(argbPixels[0].length > 0, "snaporta has to have a minimum width of 1");

		// validate data integrity
		int width = argbPixels[0].length;

		int[][] argbPixelsCopy = new int[argbPixels.length][];
		for(int y = 0; y < argbPixels.length; y++)
		{
			int[] row = argbPixels[y];
			String invalidLengthMessage = PHR.r("rows 0 and {} have different lengths: {} vs {}", y, width, row.length);
			Validate.isTrue(row.length == width, invalidLengthMessage);

			argbPixelsCopy[y] = Arrays.copyOf(row, row.length);
		}

		this.argbPixels = argbPixelsCopy;
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
