package io.domisum.lib.snaporta;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import lombok.Getter;
import lombok.ToString;

@ToString
public class Padding
{
	
	// PADDING
	@Getter
	private final int left;
	@Getter
	private final int right;
	@Getter
	private final int top;
	@Getter
	private final int bottom;
	
	
	// INIT
	public Padding(int left, int right, int top, int bottom)
	{
		ValidationUtil.greaterZero(left, "left");
		ValidationUtil.greaterZero(right, "right");
		ValidationUtil.greaterZero(top, "top");
		ValidationUtil.greaterZero(bottom, "bottom");
		
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	
	@API
	public static Padding none()
	{
		return new Padding(0, 0, 0, 0);
	}
	
	@API
	public static Padding toHorizontalSides(int horizontalPadding)
	{
		return new Padding(horizontalPadding, horizontalPadding, 0, 0);
	}
	
	@API
	public static Padding toVerticalSides(int verticalPadding)
	{
		return new Padding(0, 0, verticalPadding, verticalPadding);
	}
	
	@API
	public static Padding toHorizontalAndToVerticalSides(int horizontalPadding, int verticalPadding)
	{
		return new Padding(horizontalPadding, horizontalPadding, verticalPadding, verticalPadding);
	}
	
	@API
	public static Padding toEverySide(int padding)
	{
		return new Padding(padding, padding, padding, padding);
	}
	
	@API
	public static Padding toEveryExceptLeft(int padding)
	{
		return new Padding(0, padding, padding, padding);
	}
	
	@API
	public static Padding toEveryExceptRight(int padding)
	{
		return new Padding(padding, 0, padding, padding);
	}
	
	@API
	public static Padding toEveryExceptTop(int padding)
	{
		return new Padding(padding, padding, 0, padding);
	}
	
	@API
	public static Padding toEveryExceptBottom(int padding)
	{
		return new Padding(padding, padding, padding, 0);
	}
	
	
	// GETTERS
	public int getHorizontalSum()
	{
		return left+right;
	}
	
	public int getVerticalSum()
	{
		return top+bottom;
	}
	
	
	// DERIVE ADD
	@API
	public Padding deriveAddLeft(int deltaLeftPadding)
	{
		return new Padding(left+deltaLeftPadding, right, top, bottom);
	}
	
	@API
	public Padding deriveAddRight(int deltaRightPadding)
	{
		return new Padding(left, right+deltaRightPadding, top, bottom);
	}
	
	@API
	public Padding deriveAddTop(int deltaTopPaddding)
	{
		return new Padding(left, right, top+deltaTopPaddding, bottom);
	}
	
	@API
	public Padding deriveAddBottom(int deltaBottomPadding)
	{
		return new Padding(left, right, top, bottom+deltaBottomPadding);
	}
	
	@API
	public Padding deriveAddHorizontal(int deltaHorizontalPadding)
	{
		return new Padding(left+deltaHorizontalPadding, right+deltaHorizontalPadding, top, bottom);
	}
	
	@API
	public Padding deriveAddVertical(int deltaVerticalPadding)
	{
		return new Padding(left, right, top+deltaVerticalPadding, bottom+deltaVerticalPadding);
	}
	
	@API
	public Padding deriveAddToAllSides(int deltaPadding)
	{
		return new Padding(left+deltaPadding, right+deltaPadding, top+deltaPadding, bottom+deltaPadding);
	}
	
	
	// DERIVE MIRROR
	@API
	public Padding deriveMirrorHorizontalPaddings()
	{
		return new Padding(right, left, top, bottom);
	}
	
	@API
	public Padding deriveMirrorVerticalPaddings()
	{
		return new Padding(left, right, bottom, top);
	}
	
}
