package de.domisum.lib.snaporta;

import de.domisum.lib.auxilium.util.java.annotations.API;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class SnaportaPadding
{

	// PADDING
	@Getter private final int left;
	@Getter private final int right;
	@Getter private final int top;
	@Getter private final int bottom;


	// INIT
	@API public static SnaportaPadding none()
	{
		return new SnaportaPadding(0, 0, 0, 0);
	}

	@API public static SnaportaPadding horizontal(int horizontalPadding)
	{
		return new SnaportaPadding(horizontalPadding, horizontalPadding, 0, 0);
	}

	@API public static SnaportaPadding vertical(int verticalPadding)
	{
		return new SnaportaPadding(0, 0, verticalPadding, verticalPadding);
	}

	@API public static SnaportaPadding horizontalAndVertical(int horizontalPadding, int verticalPadding)
	{
		return new SnaportaPadding(horizontalPadding, horizontalPadding, verticalPadding, verticalPadding);
	}

	@API public static SnaportaPadding toAllSides(int padding)
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
	@API public SnaportaPadding deriveAddLeft(int deltaLeftPadding)
	{
		return new SnaportaPadding(left+deltaLeftPadding, right, top, bottom);
	}

	@API public SnaportaPadding deriveAddRight(int deltaRightPadding)
	{
		return new SnaportaPadding(left, right+deltaRightPadding, top, bottom);
	}

	@API public SnaportaPadding deriveAddTop(int deltaTopPaddding)
	{
		return new SnaportaPadding(left, right, top+deltaTopPaddding, bottom);
	}

	@API public SnaportaPadding deriveAddBottom(int deltaBottomPadding)
	{
		return new SnaportaPadding(left, right, top, bottom+deltaBottomPadding);
	}

	@API public SnaportaPadding deriveAddHorizontal(int deltaHorizontalPadding)
	{
		return new SnaportaPadding(left+deltaHorizontalPadding, right+deltaHorizontalPadding, top, bottom);
	}

	@API public SnaportaPadding deriveAddVertical(int deltaVerticalPadding)
	{
		return new SnaportaPadding(left, right, top+deltaVerticalPadding, bottom+deltaVerticalPadding);
	}

	@API public SnaportaPadding deriveAddToAllSides(int deltaPadding)
	{
		return new SnaportaPadding(left+deltaPadding, right+deltaPadding, top+deltaPadding, bottom+deltaPadding);
	}


	// DERIVE MIRROR
	@API public SnaportaPadding deriveMirrorHorizontalPaddings()
	{
		return new SnaportaPadding(right, left, top, bottom);
	}

	@API public SnaportaPadding deriveMirrorVerticalPaddings()
	{
		return new SnaportaPadding(left, right, bottom, top);
	}

}
