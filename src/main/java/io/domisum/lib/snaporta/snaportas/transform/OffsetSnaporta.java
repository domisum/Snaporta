package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

@API
public class OffsetSnaporta
		implements Snaporta
{
	
	private final Snaporta baseSnaporta;
	private final int offsetX;
	private final int offsetY;
	
	
	// INIT
	public OffsetSnaporta(Snaporta baseSnaporta, int offsetX, int offsetY)
	{
		this.baseSnaporta = baseSnaporta;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		
		Validate.notNull(baseSnaporta, "baseSnaporta can't be null");
		Validate.isTrue(getWidth() > 0, "width has to be greater than 0, was "+getWidth());
		Validate.isTrue(getHeight() > 0, "height has to be greater than 0, was "+getHeight());
	}
	
	
	// SNAPORTA
	@Override
	public int getWidth()
	{
		return baseSnaporta.getWidth()+offsetX;
	}
	
	@Override
	public int getHeight()
	{
		return baseSnaporta.getHeight()+offsetY;
	}
	
	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		int inBaseX = x-offsetX;
		int inBaseY = y-offsetY;
		
		if(!baseSnaporta.isInBounds(inBaseX, inBaseY))
			return Colors.TRANSPARENT.toARGBInt();
		
		int argb = baseSnaporta.getARGBAt(inBaseX, inBaseY);
		return argb;
	}
	
}
