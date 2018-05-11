package de.domisum.lib.snaporta.snaportas.mask;

import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.mask.doubl.DoubleMask;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DoubleMaskOpacitySnaporta implements Snaporta
{

	private final Snaporta base;
	private final DoubleMask opacityMask;


	// SNAPORTA
	@Override public int getARGBAt(int x, int y)
	{
		return getColorAt(x, y).toARGBInt();
	}

	@Override public Color getColorAt(int x, int y)
	{
		double opacityAt = opacityMask.getValueAt(x, y);
		return base.getColorAt(x, y).deriveMultiplyOpacity(opacityAt);
	}

	@Override public int getWidth()
	{
		return base.getWidth();
	}

	@Override public int getHeight()
	{
		return base.getHeight();
	}

}
