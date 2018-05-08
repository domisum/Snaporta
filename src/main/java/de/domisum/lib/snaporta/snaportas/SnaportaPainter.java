package de.domisum.lib.snaporta.snaportas;

import de.domisum.lib.auxilium.util.PHR;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.util.Sized;
import de.domisum.lib.snaporta.util.SnaportaValidate;

public class SnaportaPainter implements Sized
{

	/**
	 * Pixels represented as a 2D array of ARGB values. 2D array: array of horizontal rows of pixels.
	 */
	private final int[][] argbPixels;


	// INIT
	public SnaportaPainter(Snaporta base)
	{
		int[][] argbPixelsCopy = new int[base.getHeight()][base.getWidth()];
		for(int y = 0; y < base.getHeight(); y++)
			for(int x = 0; x < base.getWidth(); x++)
				argbPixelsCopy[y][x] = base.getARGBAt(x, y);

		argbPixels = argbPixelsCopy;
	}


	// OBJECT
	@Override public String toString()
	{
		return PHR.r("SnaportaPainter(width={},height={})", getWidth(), getHeight());
	}


	// CONVERSION
	public Snaporta toSnaporta()
	{
		return new BasicSnaporta(argbPixels);
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

	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return argbPixels[y][x];
	}


	// EDITABLE
	public void setARGBAt(int x, int y, int argb)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		argbPixels[y][x] = argb;
	}

	public void setColorAt(int x, int y, Color color)
	{
		setARGBAt(x, y, color.toARGBInt());
	}

}
