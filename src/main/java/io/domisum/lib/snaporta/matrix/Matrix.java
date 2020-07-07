package io.domisum.lib.snaporta.matrix;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@API
public class Matrix
{
	
	// ATTRIBUTES
	/**
	 * Array of rows of entries.
	 */
	private final double[][] entries;
	
	
	// INIT
	@API
	public Matrix(double[][] entries)
	{
		int width = entries[0].length;
		int height = entries.length;
		Validate.isTrue((width%2) == 1, "Matrix width has to be uneven, was "+width);
		Validate.isTrue((height%2) == 1, "Matrix height has to be uneven, was "+height);
		
		// copy to prevent outside modification
		this.entries = new double[height][];
		for(int i = 0; i < entries.length; i++)
		{
			double[] row = entries[i];
			int rowLength = row.length;
			Validate.isTrue(rowLength == width, PHR.r("Row at index {} has width of {}, has to be {}", i, rowLength, width));
			
			this.entries[i] = Arrays.copyOf(row, rowLength);
		}
	}
	
	
	// OBJECT
	@Override
	public String toString()
	{
		int valueLength = 5;
		
		var rowsString = new ArrayList<String>();
		for(double[] row : entries)
			rowsString.add(generateToStringRow(row, valueLength)+"\n");
		
		String separatorLineComponent = "-".repeat(valueLength);
		String separatorLine = separatorLineComponent+("+"+separatorLineComponent).repeat(getWidth()-1)+"\n";
		
		String asString = StringUtil.listToString(rowsString, separatorLine);
		return asString;
	}
	
	private String generateToStringRow(double[] row, int padValueToLength)
	{
		var roundedRowValuesStream = Arrays.stream(row).mapToObj(d->
		{
			String rounded = MathUtil.round(d, padValueToLength-2)+"";
			String pad = "0".repeat(padValueToLength-rounded.length());
			return rounded+pad;
		});
		var roundedRowValues = roundedRowValuesStream.collect(Collectors.toList());
		
		String asString = StringUtil.listToString(roundedRowValues, "|");
		return asString;
	}
	
	
	// GETTERS
	@API
	private int getWidth()
	{
		return entries[0].length;
	}
	
	private int getLength()
	{
		return entries.length;
	}
	
	@API
	public int getHorizontalRadius()
	{
		return (getWidth()-1)/2;
	}
	
	@API
	public int getVerticalRadius()
	{
		return (getLength()-1)/2;
	}
	
	@API
	public double getEntryAt(int x, int y)
	{
		int inEntriesX = x+getHorizontalRadius();
		int inEntriesY = y+getVerticalRadius();
		
		SnaportaValidate.validateInInterval(-getHorizontalRadius(), getHorizontalRadius(), "x", x);
		SnaportaValidate.validateInInterval(-getVerticalRadius(), getVerticalRadius(), "y", y);
		
		double entry = entries[inEntriesY][inEntriesX];
		return entry;
	}
	
	
	// DERIVE
	@API
	public Matrix deriveMultiply(double factor)
	{
		double[][] multiplied = new double[getWidth()][getWidth()];
		for(int x = 0; x < getWidth(); ++x)
			for(int y = 0; y < getWidth(); ++y)
				multiplied[x][y] = entries[x][y]*factor;
		
		return new Matrix(multiplied);
	}
	
	@API
	public Matrix deriveNormalized()
	{
		double kernelValueSum = 0.0;
		for(int x = 0; x < getWidth(); ++x)
			for(int y = 0; y < getWidth(); ++y)
				kernelValueSum += entries[x][y];
		
		return deriveMultiply(1/kernelValueSum);
	}
	
}
