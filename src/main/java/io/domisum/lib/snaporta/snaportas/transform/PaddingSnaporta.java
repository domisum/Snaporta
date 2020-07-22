package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.SnaportaValidate;

@API
public class PaddingSnaporta
		implements Snaporta
{
	
	private final Snaporta baseSnaporta;
	private final Padding padding;
	
	
	// INIT
	public PaddingSnaporta(Snaporta baseSnaporta, Padding padding)
	{
		this.baseSnaporta = baseSnaporta;
		this.padding = padding;
		
		ValidationUtil.notNull(baseSnaporta, "baseSnaporta");
		ValidationUtil.notNull(padding, "padding");
	}
	
	
	// SNAPORTA
	@Override
	public int getWidth()
	{
		return baseSnaporta.getWidth()+padding.getHorizontalSum();
	}
	
	@Override
	public int getHeight()
	{
		return baseSnaporta.getHeight()+padding.getVerticalSum();
	}
	
	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		int inBaseX = x-padding.getLeft();
		int inBaseY = y-padding.getTop();
		
		if(!baseSnaporta.isInBounds(inBaseX, inBaseY))
			return Colors.TRANSPARENT.toARGBInt();
		
		int argb = baseSnaporta.getARGBAt(inBaseX, inBaseY);
		return argb;
	}
	
}
