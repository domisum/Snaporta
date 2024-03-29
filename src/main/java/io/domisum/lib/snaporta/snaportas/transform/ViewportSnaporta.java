package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.datacontainers.bound.IntBounds2D;
import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;

@API
public final class ViewportSnaporta
	implements Snaporta
{
	
	@Getter
	private final int width;
	@Getter
	private final int height;
	private final Snaporta base;
	private final int offsetX;
	private final int offsetY;
	
	
	// INIT
	@API
	public static ViewportSnaporta sizeAndOffset(Snaporta base, int width, int height, int offsetX, int offsetY)
	{
		return new ViewportSnaporta(base, width, height, offsetX, offsetY);
	}
	
	@API
	public static ViewportSnaporta offset(Snaporta base, int offsetX, int offsetY)
	{
		return sizeAndOffset(base, base.getWidth()+offsetX, base.getHeight()+offsetY, offsetX, offsetY);
	}
	
	@API
	public static ViewportSnaporta center(Snaporta base, int width, int height)
	{
		int offsetX = (width-base.getWidth())/2;
		int offsetY = (height-base.getHeight())/2;
		return sizeAndOffset(base, width, height, offsetX, offsetY);
	}
	
	@API
	public static ViewportSnaporta pad(Snaporta base, Padding padding)
	{
		int width = base.getWidth()+padding.getHorizontalSum();
		int height = base.getHeight()+padding.getVerticalSum();
		return sizeAndOffset(base, width, height, padding.getLeft(), padding.getTop());
	}
	
	@API
	public static ViewportSnaporta crop(Snaporta base, int cropLeft, int cropRight, int cropTop, int cropBottom)
	{
		int croppedWidth = base.getWidth()-cropLeft-cropRight;
		int croppedHeight = base.getHeight()-cropTop-cropBottom;
		return sizeAndOffset(base, croppedWidth, croppedHeight, -cropLeft, -cropTop);
	}
	
	@API
	public static ViewportSnaporta cropLeftTopWithSize(Snaporta base, int cropLeft, int cropTop, int croppedWidth, int croppedHeight)
	{
		return sizeAndOffset(base, croppedWidth, croppedHeight, -cropLeft, -cropTop);
	}
	
	@API
	public static ViewportSnaporta bounds(Snaporta base, IntBounds2D bounds)
	{
		return ViewportSnaporta.sizeAndOffset(base,
			bounds.getWidth(), bounds.getHeight(),
			-bounds.getMinX(), -bounds.getMinY());
	}
	
	
	private ViewportSnaporta(Snaporta base, int width, int height, int offsetX, int offsetY)
	{
		ValidationUtil.greaterZero(width, "width");
		ValidationUtil.greaterZero(height, "height");
		ValidationUtil.notNull(base, "base");
		
		this.width = width;
		this.height = height;
		this.base = base;
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
		
		if(!base.isInBounds(inBaseX, inBaseY))
			return Colors.TRANSPARENT.toARGBInt();
		
		return base.getArgbAt(inBaseX, inBaseY);
	}
	
}
