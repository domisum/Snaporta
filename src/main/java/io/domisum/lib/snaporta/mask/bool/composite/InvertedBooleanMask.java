package io.domisum.lib.snaporta.mask.bool.composite;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.mask.bool.BooleanMask;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class InvertedBooleanMask
	implements BooleanMask
{
	
	private final BooleanMask baseMask;
	
	
	@Override
	public int getWidth()
	{return baseMask.getWidth();}
	
	@Override
	public int getHeight()
	{return baseMask.getHeight();}
	
	
	@Override
	public boolean getValueAt(int x, int y)
	{return !baseMask.getValueAt(x, y);}
	
}
