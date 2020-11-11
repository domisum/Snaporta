package io.domisum.lib.snaporta.snaportas.mask;

import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.mask.doubl.DoubleMask;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DoubleMaskOpacitySnaporta
	implements Snaporta
{
	
	private final Snaporta base;
	private final DoubleMask opacityMask;
	
	
	// SNAPORTA
	@Override
	public int getArgbAt(int x, int y)
	{
		return getColorAt(x, y).toARGBInt();
	}
	
	@Override
	public Color getColorAt(int x, int y)
	{
		double opacityAt = opacityMask.getValueAt(x, y);
		var color = base.getColorAt(x, y).deriveMultiplyOpacity(opacityAt);
		
		return color;
	}
	
	@Override
	public int getWidth()
	{
		return base.getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return base.getHeight();
	}
	
}
