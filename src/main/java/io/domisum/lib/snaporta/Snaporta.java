package io.domisum.lib.snaporta;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.ArgbUtil;
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
	int getArgbAt(int x, int y);
	
	@API
	default Color getColorAt(int x, int y)
	{
		int argb = getArgbAt(x, y);
		return Color.fromARGBInt(argb);
	}
	
	
	@API
	default int getAlphaAt(int x, int y)
	{
		return ArgbUtil.getAlphaComponent(getArgbAt(x, y));
	}
	
	@API
	default int getRedAt(int x, int y)
	{
		return ArgbUtil.getRedComponent(getArgbAt(x, y));
	}
	
	@API
	default int getGreenAt(int x, int y)
	{
		return ArgbUtil.getGreenComponent(getArgbAt(x, y));
	}
	
	@API
	default int getBlueAt(int x, int y)
	{
		return ArgbUtil.getBlueComponent(getArgbAt(x, y));
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
	
	@API
	default boolean isBlank()
	{
		if(getWidth() == 0 || getHeight() == 0)
			return true;
		
		for(int y = 0; y < getHeight(); y++)
			for(int x = 0; x < getWidth(); x++)
				if(getAlphaAt(x, y) != Color.ALPHA_TRANSPARENT)
					return false;
		
		return true;
	}
	
	@API
	default Snaporta optimize()
	{
		return this; // optional to implement this
	}
	
}
