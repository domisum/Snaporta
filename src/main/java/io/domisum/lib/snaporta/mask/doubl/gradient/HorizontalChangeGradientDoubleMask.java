package io.domisum.lib.snaporta.mask.doubl.gradient;

import io.domisum.lib.auxiliumlib.annotations.API;

@API
public class HorizontalChangeGradientDoubleMask
	extends AxisAlignedGradientDoubleMask
{
	
	// INIT
	public HorizontalChangeGradientDoubleMask(int width, int height, int lowValueCoord, int highValueCoord)
	{
		super(width, height, lowValueCoord, highValueCoord);
	}
	
	
	// MASK
	@Override
	public double getValueAt(int x, int y)
	{
		return getGradientAt(x);
	}
	
}
