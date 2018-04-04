package de.domisum.lib.snaporta;

import de.domisum.lib.auxilium.util.java.annotations.API;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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


	// DERIVE
	@API public SnaportaPadding addToLeft(int deltaLeft)
	{
		return new SnaportaPadding(left+deltaLeft, right, top, bottom);
	}

	@API public SnaportaPadding addToRight(int deltaRight)
	{
		return new SnaportaPadding(left, right+deltaRight, top, bottom);
	}

	@API public SnaportaPadding addToTop(int deltaTop)
	{
		return new SnaportaPadding(left, right, top+deltaTop, bottom);
	}

	@API public SnaportaPadding addToBottom(int deltaBottom)
	{
		return new SnaportaPadding(left, right, top, bottom+deltaBottom);
	}

}
