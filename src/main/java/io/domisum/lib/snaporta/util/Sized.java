package io.domisum.lib.snaporta.util;

import io.domisum.lib.auxiliumlib.annotations.API;

public interface Sized
{
	
	@API
	int getWidth();
	
	@API
	int getHeight();
	
	@API
	default boolean isInBounds(int x, int y)
	{
		if((x < 0) || (x >= getWidth()))
			return false;
		
		if((y < 0) || (y >= getHeight()))
			return false;
		
		return true;
	}
	
	@API
	default boolean isOutOfBounds(int x, int y)
	{
		return !isInBounds(x, y);
	}
	
	
	@API
	default boolean isSquare()
	{
		return getWidth() == getHeight();
	}
	
}
