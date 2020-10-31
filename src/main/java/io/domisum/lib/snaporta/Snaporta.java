package io.domisum.lib.snaporta;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.ARGBUtil;
import io.domisum.lib.snaporta.util.Sized;

/**
 * A snaporta is an abstraction of an ARGB image.
 * The coordinate origin is in the top left corner,
 * the x-axis extends to the right and the y-axis down.
 */
public interface Snaporta
	extends Sized
{
	
	@API
	int getARGBAt(int x, int y);
	
	@API
	default Color getColorAt(int x, int y)
	{
		int argb = getARGBAt(x, y);
		return Color.fromARGBInt(argb);
	}
	
	
	@API
	default int getAlphaAt(int x, int y)
	{
		return ARGBUtil.getAlphaComponent(getARGBAt(x, y));
	}
	
	@API
	default int getRedAt(int x, int y)
	{
		return ARGBUtil.getRedComponent(getARGBAt(x, y));
	}
	
	@API
	default int getGreenAt(int x, int y)
	{
		return ARGBUtil.getGreenComponent(getARGBAt(x, y));
	}
	
	@API
	default int getBlueAt(int x, int y)
	{
		return ARGBUtil.getBlueComponent(getARGBAt(x, y));
	}
	
	
	@API
	default boolean containsTransparency()
	{
		for(int y = 0; y < getHeight(); y++)
			for(int x = 0; x < getWidth(); x++)
				if(getAlphaAt(x, y) > 0)
					return true;
		
		return false;
	}
	
}
