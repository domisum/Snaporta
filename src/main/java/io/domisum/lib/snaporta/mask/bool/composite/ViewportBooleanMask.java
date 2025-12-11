package io.domisum.lib.snaporta.mask.bool.composite;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.mask.bool.BooleanMask;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewportBooleanMask
	implements BooleanMask
{
	
	private final BooleanMask baseMask;
	private final int offsetX;
	private final int offsetY;
	private final int deltaWidth;
	private final int deltaHeight;
	
	
	// INIT
	@API
	public static ViewportBooleanMask pad(BooleanMask baseMask, Padding padding)
	{
		return new ViewportBooleanMask(baseMask,
			padding.getLeft(), padding.getTop(),
			padding.getHorizontalSum(), padding.getVerticalSum());
	}
	
	@Override
	public String toString()
	{
		return PHR.r("{}(oX={} oY={} dW={} dH={}\n{})", getClass().getSimpleName(),
			offsetX, offsetY, deltaWidth, deltaHeight,
			StringUtil.indent(baseMask.toString(), "\t"));
	}
	
	
	// MASK
	@Override
	public int getWidth()
	{
		return baseMask.getWidth() + deltaWidth;
	}
	
	@Override
	public int getHeight()
	{
		return baseMask.getHeight() + deltaHeight;
	}
	
	@Override
	public boolean getValueAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		int bX = x - offsetX;
		int bY = y - offsetY;
		
		if(baseMask.isOutOfBounds(bX, bY))
			return false;
		return baseMask.getValueAt(bX, bY);
	}
	
}
