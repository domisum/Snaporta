package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;

@API
public class ViewportSnaporta
	implements Snaporta
{
	
	@Getter
	private final int width;
	@Getter
	private final int height;
	private final Snaporta baseSnaporta;
	private final int offsetX;
	private final int offsetY;
	
	
	// INIT
	@API
	public static ViewportSnaporta cropOnAllSides(Snaporta baseSnaporta, int cropLeft, int cropRight, int cropTop, int cropBottom)
	{
		int croppedWidth = baseSnaporta.getWidth()-cropLeft-cropRight;
		int croppedHeight = baseSnaporta.getHeight()-cropTop-cropBottom;
		
		return new ViewportSnaporta(croppedWidth, croppedHeight, baseSnaporta, -cropLeft, -cropTop);
	}
	
	@API
	public static ViewportSnaporta cropLeftTopWithWidthAndHeight(Snaporta baseSnaporta, int cropLeft, int cropTop, int croppedWidth, int croppedHeight)
	{
		return new ViewportSnaporta(croppedWidth, croppedHeight, baseSnaporta, -cropLeft, -cropTop);
	}
	
	@API
	public ViewportSnaporta(int width, int height, Snaporta baseSnaporta, int offsetX, int offsetY)
	{
		ValidationUtil.greaterZero(width, "width");
		ValidationUtil.greaterZero(height, "height");
		ValidationUtil.notNull(baseSnaporta, "baseSnaporta");
		
		this.width = width;
		this.height = height;
		this.baseSnaporta = baseSnaporta;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	
	// SNAPORTA
	@Override
	public int getArgbAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		int inBaseX = x-offsetX;
		int inBaseY = y-offsetY;
		
		if(!baseSnaporta.isInBounds(inBaseX, inBaseY))
			return Colors.TRANSPARENT.toARGBInt();
		
		return baseSnaporta.getArgbAt(inBaseX, inBaseY);
	}
	
}
