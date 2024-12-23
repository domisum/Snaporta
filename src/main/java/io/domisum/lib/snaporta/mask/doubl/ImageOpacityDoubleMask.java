package io.domisum.lib.snaporta.mask.doubl;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Snaporta;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageOpacityDoubleMask
	implements DoubleMask
{
	
	private final Snaporta image;
	
	
	// OBJECT
	@Override
	public String toString()
	{
		return PHR.r("{}(\n{})", getClass().getSimpleName(),
			 StringUtil.indent(image.toString(), "\t"));
	}
	
	
	// DOUBLE MASK
	@Override
	public double getValueAt(int x, int y)
	{
		return image.getColorAt(x, y).getOpacity();
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
