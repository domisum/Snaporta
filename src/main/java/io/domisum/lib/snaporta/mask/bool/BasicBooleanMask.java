package io.domisum.lib.snaporta.mask.bool;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;

public final class BasicBooleanMask
		implements BooleanMask
{
	
	private final boolean[][] values;
	
	
	// INIT
	public static BasicBooleanMask onlyFalseOfWidthAndHeight(int width, int height)
	{
		boolean[][] values = new boolean[height][width];
		return new BasicBooleanMask(values);
	}
	
	BasicBooleanMask(boolean[][] values)
	{
		// validate minimum dimension
		Validate.isTrue(values.length > 0, "Mask has to have a minimum height of 1");
		Validate.noNullElements(values);
		Validate.isTrue(values[0].length > 0, "Mask has to have a minimum width of 1");
		
		// deep copy to avoid modification through array reference
		int width = values[0].length;
		
		boolean[][] valuesCopy = new boolean[values.length][];
		for(int y = 0; y < values.length; y++)
		{
			boolean[] row = values[y];
			if(row.length != width)
				throw new IllegalArgumentException(PHR.r("Rows 0 and {} have different lengths: {} vs {}", y, width, row.length));
			
			valuesCopy[y] = Arrays.copyOf(row, row.length);
		}
		
		this.values = valuesCopy;
	}
	
	
	// GETTERS
	@Override
	public int getWidth()
	{
		return values[0].length;
	}
	
	@Override
	public int getHeight()
	{
		return values.length;
	}
	
	@Override
	public boolean getValueAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return values[y][x];
	}
	
}
