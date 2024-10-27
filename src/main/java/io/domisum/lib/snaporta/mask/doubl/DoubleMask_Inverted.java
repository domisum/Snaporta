package io.domisum.lib.snaporta.mask.doubl;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DoubleMask_Inverted
	implements DoubleMask
{
	
	private final DoubleMask backing;
	
	
	// OBJECT
	@Override
	public String toString()
	{
		return PHR.r("{}(\n{})", getClass().getSimpleName(),
			StringUtil.indent(backing.toString(), "\t"));
	}
	
	
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
		return 1 - backingValue;
	}
	
}
