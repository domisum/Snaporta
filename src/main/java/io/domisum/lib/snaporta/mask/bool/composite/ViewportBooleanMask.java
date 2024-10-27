package io.domisum.lib.snaporta.mask.bool.composite;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.mask.bool.BooleanMask;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

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
	public static ViewportBooleanMask offsetAndSizeDelta(BooleanMask baseMask, int offsetX, int offsetY, int deltaWidth, int deltaHeight)
	{
		return new ViewportBooleanMask(baseMask, offsetX, offsetY, deltaWidth, deltaHeight);
	}
	
	public static ViewportBooleanMask extendLeftTopRightBottom(BooleanMask baseMask, int extendLeft, int extendTop, int extendRight, int extendBottom)
	{
		return new ViewportBooleanMask(baseMask, extendLeft, extendTop, extendLeft + extendRight, extendTop + extendBottom);
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
