package de.domisum.lib.snaporta.util;

import de.domisum.lib.auxilium.util.java.annotations.API;

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

}
