package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.Sized;
import io.domisum.lib.snaporta.util.SnaportaValidate;

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
				argbPixelsCopy[y][x] = base.getARGBAt(x, y);
		
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
		
		int argb = argbPixels[y][x];
		return argb;
	}
	
	@API
	public Color getColorAt(int x, int y)
	{
		int argb = getARGBAt(x, y);
		var color = Color.fromARGBInt(argb);
		
		return color;
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
	
}
