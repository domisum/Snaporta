package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Orientation;
import io.domisum.lib.snaporta.Snaporta;

public class MirroredSnaporta
	implements Snaporta
{
	
	private final Snaporta baseSnaporta;
	private final Orientation orientation;
	
	
	// INIT
	@API
	public static MirroredSnaporta horizontal(Snaporta baseSnaporta)
	{
		return new MirroredSnaporta(baseSnaporta, Orientation.HORIZONTAL);
	}
	
	@API
	public static MirroredSnaporta vertical(Snaporta baseSnaporta)
	{
		return new MirroredSnaporta(baseSnaporta, Orientation.VERTICAL);
	}
	
	@API
	public MirroredSnaporta(Snaporta baseSnaporta, Orientation orientation)
	{
		this.baseSnaporta = baseSnaporta;
		this.orientation = orientation;
	}
	
	
	// MASK
	@Override
	public int getWidth()
	{
		return baseSnaporta.getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return baseSnaporta.getHeight();
	}
	
	@Override
	public int getArgbAt(int x, int y)
	{
		if(orientation == Orientation.HORIZONTAL)
			x = getWidth()-x-1;
		if(orientation == Orientation.VERTICAL)
			y = getHeight()-y-1;
		
		return baseSnaporta.getArgbAt(x, y);
	}
	
}
