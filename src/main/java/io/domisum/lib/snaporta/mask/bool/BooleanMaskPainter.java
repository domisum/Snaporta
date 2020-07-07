package io.domisum.lib.snaporta.mask.bool;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.util.Sized;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;

@SuppressWarnings("BooleanParameter")
public final class BooleanMaskPainter
		implements Sized
{
	
	private final boolean[][] values;
	
	
	// INIT
	@API
	public static BooleanMaskPainter onlyFalseOfWidthAndHeight(int width, int height)
	{
		boolean[][] values = new boolean[height][width];
		return new BooleanMaskPainter(values);
	}
	
	private BooleanMaskPainter(boolean[][] values)
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
	
	
	// CONVERSION
	@API
	public BooleanMask toMask()
	{
		return new BasicBooleanMask(values);
	}
	
	
	// MASK
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
	
	@API
	public boolean getValueAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return values[y][x];
	}
	
	
	// PAINTER
	@API
	public void setValueAt(int x, int y, boolean value)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		values[y][x] = value;
	}
	
}
