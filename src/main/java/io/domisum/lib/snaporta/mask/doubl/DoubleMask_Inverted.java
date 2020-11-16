package io.domisum.lib.snaporta.mask.doubl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DoubleMask_Inverted
	implements DoubleMask
{
	
	private final DoubleMask backing;
	
	
	// DOUBLE MASK
	@Override
	public int getWidth()
	{
		return backing.getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return backing.getHeight();
	}
	
	@Override
	public double getValueAt(int x, int y)
	{
		double backingValue = backing.getValueAt(x, y);
		return 1-backingValue;
	}
	
}
