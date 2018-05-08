package de.domisum.lib.snaporta.snaportas;

import de.domisum.lib.auxilium.util.PHR;
import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;

public final class BasicSnaporta implements Snaporta
{

	/**
	 * Pixels represented as a 2D array of ARGB values. 2D array: array of horizontal rows of pixels.
	 */
	private final int[][] argbPixels;


	// INIT
	@API public static BasicSnaporta blankOfWidthAndHeight(int width, int height)
	{
		if(width < 0)
			throw new IllegalArgumentException("width can't be negative, was "+width);

		if(height < 0)
			throw new IllegalArgumentException("height can't be negative, was "+height);

		return fromARGBPixels(new int[height][width]);
	}

	@API public static BasicSnaporta fromARGBPixels(int[][] argbPixels)
	{
		return new BasicSnaporta(argbPixels);
	}

	@API public static BasicSnaporta copyOf(Snaporta snaporta)
	{
		int[][] argbPixels = new int[snaporta.getHeight()][snaporta.getWidth()];
		for(int y = 0; y < snaporta.getHeight(); y++)
			for(int x = 0; x < snaporta.getWidth(); x++)
				argbPixels[y][x] = snaporta.getARGBAt(x, y);

		return fromARGBPixels(argbPixels);
	}


	// BASIC INIT
	private BasicSnaporta(int[][] argbPixels)
	{
		// validate minimum dimension
		Validate.isTrue(argbPixels.length > 0, "snaporta has to have a minimum height of 1");
		Validate.noNullElements(argbPixels);
		Validate.isTrue(argbPixels[0].length > 0, "snaporta has to have a minimum width of 1");

		// deep copy to avoid modification through array reference
		int width = argbPixels[0].length;

		int[][] argbPixelsCopy = new int[argbPixels.length][];
		for(int y = 0; y < argbPixels.length; y++)
		{
			int[] row = argbPixels[y];
			if(row.length != width)
				throw new IllegalArgumentException(PHR.r("rows 0 and {} have different lengths: {} vs {}", y, width, row.length));

			argbPixelsCopy[y] = Arrays.copyOf(row, row.length);
		}

		this.argbPixels = argbPixelsCopy;
	}


	// OBJECT
	@Override public String toString()
	{
		return PHR.r("BasicSnaporta(width={},height={})", getWidth(), getHeight());
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

}
