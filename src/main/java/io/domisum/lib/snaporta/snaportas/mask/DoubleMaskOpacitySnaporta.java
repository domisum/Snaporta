package io.domisum.lib.snaporta.snaportas.mask;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.util.StringUtil;
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
	
	
	// OBJECT
	@Override
	public String toString()
	{
		return PHR.r("{}(\n{}\n{})", getClass().getSimpleName(),
			StringUtil.indent(opacityMask, "\t"),
			StringUtil.indent(base.toString(), "\t"));
	}
	
	
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
		return base.getColorAt(x, y).deriveMultiplyOpacity(opacityAt);
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
