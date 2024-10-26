package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.datacontainers.bound.IntBounds2D;
import io.domisum.lib.auxiliumlib.datacontainers.math.Coordinate2DInt;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.snaportas.LayeredSnaporta;
import io.domisum.lib.snaporta.snaportas.color.BlankSnaporta;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;

@API
@Getter
public final class ViewportSnaporta
	implements Snaporta
{
	
	private Snaporta base;
	private final int width;
	private final int height;
	
	private final Coordinate2DInt positionOffset;
	private final Coordinate2DInt internalOffset;
	private final Coordinate2DInt windowSize;
	
	
	// INIT
	@API
	public static ViewportSnaporta sizeAndOffset(Snaporta base, int width, int height, int offsetX, int offsetY)
	{
		return new ViewportSnaporta(base, width, height, offsetX, offsetY);
	}
	
	@API
	public static ViewportSnaporta offset(Snaporta base, int offsetX, int offsetY)
	{
		return sizeAndOffset(base, base.getWidth() + offsetX, base.getHeight() + offsetY, offsetX, offsetY);
	}
	
	@API
	public static ViewportSnaporta center(Snaporta base, int width, int height)
	{
		int offsetX = (width - base.getWidth()) / 2;
		int offsetY = (height - base.getHeight()) / 2;
		return sizeAndOffset(base, width, height, offsetX, offsetY);
	}
	
	@API
	public static ViewportSnaporta pad(Snaporta base, Padding padding)
	{
		int width = base.getWidth() + padding.getHorizontalSum();
		int height = base.getHeight() + padding.getVerticalSum();
		return sizeAndOffset(base, width, height, padding.getLeft(), padding.getTop());
	}
	
	@API
	public static ViewportSnaporta crop(Snaporta base, int cropLeft, int cropRight, int cropTop, int cropBottom)
	{
		int croppedWidth = base.getWidth() - cropLeft - cropRight;
		int croppedHeight = base.getHeight() - cropTop - cropBottom;
		return sizeAndOffset(base, croppedWidth, croppedHeight, -cropLeft, -cropTop);
	}
	
	@API
	public static ViewportSnaporta cropLeftTopWithSize(Snaporta base, int cropLeft, int cropTop, int croppedWidth, int croppedHeight)
	{
		return sizeAndOffset(base, croppedWidth, croppedHeight, -cropLeft, -cropTop);
	}
	
	@API
	public static ViewportSnaporta boundsSubsection(Snaporta base, IntBounds2D bounds)
	{
		return sizeAndOffset(base,
			bounds.getWidth(), bounds.getHeight(),
			-bounds.getMinX(), -bounds.getMinY());
	}
	
	
	private ViewportSnaporta(Snaporta base, int width, int height, int offsetX, int offsetY)
	{
		this(base, width, height,
			new Coordinate2DInt(offsetX, offsetY),
			Coordinate2DInt.zero(),
			new Coordinate2DInt(base.getWidth(), base.getHeight()));
	}
	
	public ViewportSnaporta(Snaporta base, int width, int height, Coordinate2DInt positionOffset, Coordinate2DInt internalOffset, Coordinate2DInt windowSize)
	{
		ValidationUtil.greaterZero(width, "width");
		ValidationUtil.greaterZero(height, "height");
		ValidationUtil.notNull(base, "base");
		
		var croppedWindowSizeBound = new Coordinate2DInt(width - positionOffset.getX() - internalOffset.getX(), height - positionOffset.getY() - internalOffset.getY());
		var baseWindowSizeBound = new Coordinate2DInt(base.getWidth(), base.getHeight());
		
		this.base = base;
		this.width = width;
		this.height = height;
		this.positionOffset = positionOffset;
		this.internalOffset = internalOffset;
		this.windowSize = windowSize.deriveMergeMin(baseWindowSizeBound).deriveMergeMin(croppedWindowSizeBound);
	}
	
	@Override
	public String toString()
	{
		return PHR.r("{}(w={} x h={}, po={} io={} ws={}\n{})", getClass().getSimpleName(),
			width, height, positionOffset, internalOffset, windowSize, StringUtil.indent(base.toString(), "\t"));
	}
	
	
	// SNAPORTA
	@Override
	public int getArgbAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		int inBaseX = x - positionOffset.getX() - internalOffset.getX();
		int inBaseY = y - positionOffset.getY() - internalOffset.getY();
		
		if((inBaseX >= 0) && (inBaseX < windowSize.getX()) && (inBaseY >= 0) && (inBaseY < windowSize.getY()))
			return base.getArgbAt(inBaseX, inBaseY);
		else
			return 0; // transparent
	}
	
	@Override
	public BlankState isBlank()
	{
		if(windowSize.getX() <= 0 || windowSize.getY() <= 0)
			return BlankState.BLANK;
		return base.isBlank();
	}
	
	@Override
	public Snaporta optimize()
	{
		base = base.optimize();
		
		if(base instanceof ViewportSnaporta inner)
			return mergeWithInnerViewportSnaporta(inner).optimize();
		
		if(base instanceof LayeredSnaporta laySnap)
		{
			var newLs = new LayeredSnaporta(width, height);
			for(var l : laySnap.getLayersBottomUp())
				newLs.addLayerOnTop(new ViewportSnaporta(l, width, height,
					positionOffset, internalOffset, windowSize).optimize());
			return newLs.optimize();
		}
		
		return this;
	}
	
	private Snaporta mergeWithInnerViewportSnaporta(ViewportSnaporta inner)
	{
		var newPositionOffset = this.positionOffset;
		var newInternalOffset = inner.internalOffset.deriveSubtract(inner.positionOffset).deriveAdd(this.internalOffset);
		var newWindowSize = calculateMergedWindowSize(inner);
		
		if(newInternalOffset.getX() < 0)
		{
			newPositionOffset = newPositionOffset.deriveAdd(-newInternalOffset.getX(), 0);
			newInternalOffset = new Coordinate2DInt(0, newInternalOffset.getY());
		}
		if(newInternalOffset.getY() < 0)
		{
			newPositionOffset = newPositionOffset.deriveAdd(0, -newInternalOffset.getY());
			newInternalOffset = new Coordinate2DInt(newInternalOffset.getX(), 0);
		}
		
		if(newWindowSize.getX() <= 0 || newWindowSize.getY() <= 0)
			return new BlankSnaporta(width, height);
		
		return new ViewportSnaporta(inner.base, width, height,
			newPositionOffset, newInternalOffset, newWindowSize);
	}
	
	private Coordinate2DInt calculateMergedWindowSize(ViewportSnaporta inner)
	{
		var windowThisTl = this.positionOffset;
		var windowThisBr = windowThisTl.deriveAdd(this.windowSize);
		var windowInnerTl = windowThisTl.deriveSubtract(this.internalOffset).deriveAdd(inner.positionOffset);
		var windowInnerBr = windowInnerTl.deriveAdd(inner.windowSize);
		
		int windowLeft = Math.max(windowThisTl.getX(), windowInnerTl.getX());
		int windowRight = Math.min(windowThisBr.getX(), windowInnerBr.getX());
		int windowWidth = windowRight - windowLeft;
		
		int windowTop = Math.max(windowThisTl.getY(), windowInnerTl.getY());
		int windowBottom = Math.min(windowThisBr.getY(), windowInnerBr.getY());
		int windowHeight = windowBottom - windowTop;
		return new Coordinate2DInt(windowWidth, windowHeight);
	}
	
}
