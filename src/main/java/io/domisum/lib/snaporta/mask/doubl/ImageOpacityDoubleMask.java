package io.domisum.lib.snaporta.mask.doubl;

import io.domisum.lib.snaporta.Snaporta;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageOpacityDoubleMask implements DoubleMask
{

	private final Snaporta image;


	// DOUBLE MASK
	@Override
	public double getValueAt(int x, int y)
	{
		return image.getColorAt(x, y).getOpacityRelative();
	}

	@Override
	public int getWidth()
	{
		return image.getWidth();
	}

	@Override
	public int getHeight()
	{
		return image.getHeight();
	}

}
