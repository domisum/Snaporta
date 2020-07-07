package io.domisum.lib.snaporta.mask.doubl.gradient;

public class VerticalGradientDoubleMask
		extends StraightGradientDoubleMask
{
	
	// INIT
	public VerticalGradientDoubleMask(int width, int height, int lowValueCoord, int highValueCoord)
	{
		super(width, height, lowValueCoord, highValueCoord);
	}
	
	
	// MASK
	@Override
	public double getValueAt(int x, int y)
	{
		return getGradientAt(y);
	}
	
}
