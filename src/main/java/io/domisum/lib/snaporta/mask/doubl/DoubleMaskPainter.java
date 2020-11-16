package io.domisum.lib.snaporta.mask.doubl;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.util.Sized;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;

public final class DoubleMaskPainter
	implements Sized
{
	
	private final double[][] values;
	
	
	// INIT
	@API
	public static DoubleMaskPainter sized(int width, int height)
	{
		double[][] values = new double[height][width];
		return new DoubleMaskPainter(values);
	}
	
	private DoubleMaskPainter(double[][] values)
	{
		// validate minimum dimension
		Validate.isTrue(values.length > 0, "mask has to have a minimum height of 1");
		Validate.noNullElements(values);
		Validate.isTrue(values[0].length > 0, "mask has to have a minimum width of 1");
		
		// deep copy to avoid modification through array reference
		int width = values[0].length;
		
		double[][] valuesCopy = new double[values.length][];
		for(int y = 0; y < values.length; y++)
		{
			double[] row = values[y];
			if(row.length != width)
				throw new IllegalArgumentException(PHR.r("rows 0 and {} have different lengths: {} vs {}", y, width, row.length));
			
			valuesCopy[y] = Arrays.copyOf(row, row.length);
		}
		
		this.values = valuesCopy;
	}
	
	
	// CONVERSION
	@API
	public DoubleMask toMask()
	{
		return new BasicDoubleMask(values);
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
	public double getValueAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return values[y][x];
	}
	
	
	// PAINTER
	@API
	public void setValueAt(int x, int y, double value)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		values[y][x] = value;
	}
	
}
