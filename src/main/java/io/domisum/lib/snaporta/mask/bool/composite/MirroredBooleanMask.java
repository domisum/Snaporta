package io.domisum.lib.snaporta.mask.bool.composite;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Orientation;
import io.domisum.lib.snaporta.mask.bool.BooleanMask;

public class MirroredBooleanMask
	implements BooleanMask
{
	
	private final BooleanMask baseMask;
	private final Orientation orientation;
	
	
	// INIT
	@API
	public static MirroredBooleanMask horizontal(BooleanMask baseMask)
	{
		return new MirroredBooleanMask(baseMask, Orientation.HORIZONTAL);
	}
	
	@API
	public static MirroredBooleanMask vertical(BooleanMask baseMask)
	{
		return new MirroredBooleanMask(baseMask, Orientation.VERTICAL);
	}
	
	@API
	public MirroredBooleanMask(BooleanMask baseMask, Orientation orientation)
	{
		this.baseMask = baseMask;
		this.orientation = orientation;
	}
	
	
	// MASK
	@Override
	public int getWidth()
	{
		return baseMask.getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return baseMask.getHeight();
	}
	
	@Override
	public boolean getValueAt(int x, int y)
	{
		if(orientation == Orientation.HORIZONTAL)
			x = getWidth()-x-1;
		if(orientation == Orientation.VERTICAL)
			y = getHeight()-y-1;
		
		return baseMask.getValueAt(x, y);
	}
	
}
