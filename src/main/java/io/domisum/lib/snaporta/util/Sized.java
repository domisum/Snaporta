package io.domisum.lib.snaporta.util;

import io.domisum.lib.auxiliumlib.annotations.API;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface Sized
{
	
	static Sized sized(int width, int height)
	{
		return new SizedContainer(width, height);
	}
	
	
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
	
	
	@RequiredArgsConstructor
	class SizedContainer
		implements Sized
	{
		
		@Getter
		private final int width;
		@Getter
		private final int height;
		
	}
	
}
