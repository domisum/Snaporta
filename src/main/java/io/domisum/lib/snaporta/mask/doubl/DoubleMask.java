package io.domisum.lib.snaporta.mask.doubl;

import io.domisum.lib.snaporta.util.Sized;

public interface DoubleMask
	extends Sized
{
	
	/*
		Output range: [0.0-1.0]
	 */
	double getValueAt(int x, int y);
	
}
