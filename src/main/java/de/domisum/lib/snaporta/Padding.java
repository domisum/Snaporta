package de.domisum.lib.snaporta;

import de.domisum.lib.auxilium.util.java.annotations.API;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Padding
{

	// PADDING
	@Getter private final int left;
	@Getter private final int right;
	@Getter private final int top;
	@Getter private final int bottom;


	// INIT
	@API public static Padding none()
	{
		return new Padding(0, 0, 0, 0);
	}

	@API public static Padding horizontal(int horizontalPadding)
	{
		return new Padding(horizontalPadding, horizontalPadding, 0, 0);
	}

	@API public static Padding vertical(int verticalPadding)
	{
		return new Padding(0, 0, verticalPadding, verticalPadding);
	}

	@API public static Padding horizontalAndVertical(int horizontalPadding, int verticalPadding)
	{
		return new Padding(horizontalPadding, horizontalPadding, verticalPadding, verticalPadding);
	}

	@API public static Padding toAllSides(int padding)
	{
		return horizontalAndVertical(padding, padding);
	}


	// GETTERS
	public int getHorizontal()
	{
		return left+right;
	}

	public int getVertical()
	{
		return top+bottom;
	}


	// DERIVE ADD
	@API public Padding deriveAddLeft(int deltaLeftPadding)
	{
		return new Padding(left+deltaLeftPadding, right, top, bottom);
	}

	@API public Padding deriveAddRight(int deltaRightPadding)
	{
		return new Padding(left, right+deltaRightPadding, top, bottom);
	}

	@API public Padding deriveAddTop(int deltaTopPaddding)
	{
		return new Padding(left, right, top+deltaTopPaddding, bottom);
	}

	@API public Padding deriveAddBottom(int deltaBottomPadding)
	{
		return new Padding(left, right, top, bottom+deltaBottomPadding);
	}

	@API public Padding deriveAddHorizontal(int deltaHorizontalPadding)
	{
		return new Padding(left+deltaHorizontalPadding, right+deltaHorizontalPadding, top, bottom);
	}

	@API public Padding deriveAddVertical(int deltaVerticalPadding)
	{
		return new Padding(left, right, top+deltaVerticalPadding, bottom+deltaVerticalPadding);
	}

	@API public Padding deriveAddToAllSides(int deltaPadding)
	{
		return new Padding(left+deltaPadding, right+deltaPadding, top+deltaPadding, bottom+deltaPadding);
	}


	// DERIVE MIRROR
	@API public Padding deriveMirrorHorizontalPaddings()
	{
		return new Padding(right, left, top, bottom);
	}

	@API public Padding deriveMirrorVerticalPaddings()
	{
		return new Padding(left, right, bottom, top);
	}

}
