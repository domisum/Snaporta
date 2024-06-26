package io.domisum.lib.snaporta.snaportas.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.color.Colors;

@API
public class BlankSnaporta
	extends SolidColorSnaporta
{
	
	@API
	public BlankSnaporta(int width, int height)
	{
		super(width, height, Colors.TRANSPARENT);
	}
	
	@Override
	public BlankState isBlank()
	{
		return BlankState.BLANK;
	}
	
}
