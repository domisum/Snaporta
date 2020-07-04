package io.domisum.lib.snaporta.mask.bool;

import io.domisum.lib.snaporta.util.Sized;

public interface BooleanMask
		extends Sized
{
	
	boolean getValueAt(int x, int y);
	
}
