package io.domisum.lib.snaporta.mask.doubl;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;

public final class BasicDoubleMask
	implements DoubleMask
{
	
	private final double[][] values;
	
	
	// INIT
	@API
	public static BasicDoubleMask ofSize(int width, int height)
	{
		double[][] values = new double[height][width];
		return new BasicDoubleMask(values);
	}
	
	BasicDoubleMask(double[][] values)
	{
		// validate minimum dimension
		Validate.isTrue(values.length > 0, "Mask has to have a minimum height of 1");
		Validate.noNullElements(values);
		Validate.isTrue(values[0].length > 0, "Mask has to have a minimum width of 1");
		
		// deep copy to avoid modification through array reference
		int width = values[0].length;
		
		double[][] valuesCopy = new double[values.length][];
		for(int y = 0; y < values.length; y++)
		{
			double[] row = values[y];
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
	public double getValueAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return values[y][x];
	}
	
}
