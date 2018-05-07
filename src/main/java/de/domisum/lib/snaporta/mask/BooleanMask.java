package de.domisum.lib.snaporta.mask;

import de.domisum.lib.snaporta.util.Sized;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BooleanMask implements Sized
{

	private final boolean[][] values;


	// INIT
	public static BooleanMask ofFalse(int width, int height)
	{
		boolean[][] values = new boolean[width][height];
		return new BooleanMask(values);
	}


	// GETTERS
	@Override public int getWidth()
	{
		return values[0].length;
	}

	@Override public int getHeight()
	{
		return values.length;
	}

	public boolean getValueAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return values[y][x];
	}

}
