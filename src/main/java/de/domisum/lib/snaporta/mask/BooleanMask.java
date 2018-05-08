package de.domisum.lib.snaporta.mask;

import de.domisum.lib.auxilium.util.PHR;
import de.domisum.lib.snaporta.util.Sized;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;

public class BooleanMask implements Sized
{

	private final boolean[][] values;


	// INIT
	public static BooleanMask onlyFalseOfWidthAndHeight(int width, int height)
	{
		boolean[][] values = new boolean[height][width];
		return new BooleanMask(values);
	}

	private BooleanMask(boolean[][] values)
	{
		// validate minimum dimension
		Validate.isTrue(values.length > 0, "mask has to have a minimum height of 1");
		Validate.noNullElements(values);
		Validate.isTrue(values[0].length > 0, "mask has to have a minimum width of 1");

		// deep copy to avoid modification through array reference
		int width = values[0].length;

		boolean[][] valuesCopy = new boolean[values.length][];
		for(int y = 0; y < values.length; y++)
		{
			boolean[] row = values[y];
			if(row.length != width)
				throw new IllegalArgumentException(PHR.r("rows 0 and {} have different lengths: {} vs {}", y, width, row.length));

			valuesCopy[y] = Arrays.copyOf(row, row.length);
		}

		this.values = valuesCopy;
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
