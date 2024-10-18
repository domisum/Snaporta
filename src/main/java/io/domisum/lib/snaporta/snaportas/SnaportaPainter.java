package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.Sized;
import io.domisum.lib.snaporta.util.SnaportaValidate;

import java.util.Arrays;

public class SnaportaPainter
	implements Sized
{
	
	/**
	 * Pixels represented as a 2D array of ARGB values. 2D array: array of horizontal rows of pixels.
	 */
	private final int[][] argbPixels;
	
	
	// INIT
	@API
	public SnaportaPainter(int width, int height)
	{
		this(BasicSnaporta.blankOfWidthAndHeight(width, height));
	}
	
	@API
	public SnaportaPainter(Snaporta base)
	{
		int[][] argbPixelsCopy = new int[base.getHeight()][base.getWidth()];
		for(int y = 0; y < base.getHeight(); y++)
			for(int x = 0; x < base.getWidth(); x++)
				argbPixelsCopy[y][x] = base.getArgbAt(x, y);
		
		argbPixels = argbPixelsCopy;
	}
	
	
	// OBJECT
	@Override
	public String toString()
	{
		return PHR.r("SnaportaPainter(width={},height={})", getWidth(), getHeight());
	}
	
	
	// CONVERSION
	@API
	public Snaporta toSnaporta()
	{
		return new BasicSnaporta(argbPixels);
	}
	
	
	// SNAPORTA
	@API
	@Override
	public int getWidth()
	{
		return argbPixels[0].length;
	}
	
	@API
	@Override
	public int getHeight()
	{
		return argbPixels.length;
	}
	
	@API
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return argbPixels[y][x];
	}
	
	@API
	public Color getColorAt(int x, int y)
	{
		int argb = getARGBAt(x, y);
		return Color.fromARGBInt(argb);
	}
	
	
	// PAINTER
	@API
	public void setARGBAt(int x, int y, int argb)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		argbPixels[y][x] = argb;
	}
	
	@API
	public void setColorAt(int x, int y, Color color)
	{
		setARGBAt(x, y, color.toARGBInt());
	}
	
	@API
	public void setOpacityAt(int x, int y, double opacity)
	{
		var color = getColorAt(x, y).deriveWithOpacity(opacity);
		setColorAt(x, y, color);
	}
	
	@API
	public void fill(Color color)
	{
		int colorArgb = color.toARGBInt();
		for(int[] row : argbPixels)
			Arrays.fill(row, colorArgb);
	}
	
	@API
	public void fill(int x0i, int x1e, int y0i, int y1e, Color color)
	{
		int colorArgb = color.toARGBInt();
		for(int y = y0i; y < y1e; y++)
		{
			int[] row = argbPixels[y];
			Arrays.fill(row, x0i, x1e, colorArgb);
		}
	}
	
}
