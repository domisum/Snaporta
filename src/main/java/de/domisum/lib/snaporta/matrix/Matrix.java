package de.domisum.lib.snaporta.matrix;

import de.domisum.lib.auxilium.util.PHR;
import de.domisum.lib.auxilium.util.StringUtil;
import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.auxilium.util.math.MathUtil;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@API
public class Matrix
{

	// ATTRIBUTES
	/**
	 * Array of rows of entries.
	 */
	private final double[][] entries;


	// INIT
	@API public Matrix(double[][] entries)
	{
		int width = entries[0].length;
		int height = entries.length;
		Validate.isTrue((height%2) == 1, "matrix height has to be uneven, was "+height);
		Validate.isTrue((width%2) == 1, "matrix width has to be uneven, was "+width);

		// copy to prevent outside modification
		this.entries = new double[height][];
		for(int i = 0; i < entries.length; i++)
		{
			double[] row = entries[i];
			int rowLength = row.length;
			Validate.isTrue(rowLength == width, PHR.r("row at index {} has width of {}, has to be {}", i, rowLength, width));

			this.entries[i] = Arrays.copyOf(row, rowLength);
		}
	}


	// OBJECT
	@Override public String toString()
	{
		int valueLength = 5;

		List<String> rowsString = new ArrayList<>();
		for(double[] row : entries)
			rowsString.add(generateToStringRow(row, valueLength)+"\n");

		String separatorLineComponent = StringUtil.repeat("-", valueLength);
		String separatorLine = separatorLineComponent+StringUtil.repeat("+"+separatorLineComponent, getWidth()-1)+"\n";

		return StringUtil.listToString(rowsString, separatorLine);
	}

	private String generateToStringRow(double[] row, int padValueToLength)
	{
		Stream<String> roundedRowValuesStream = Arrays.stream(row).mapToObj(d->
		{
			String rounded = MathUtil.round(d, padValueToLength-2)+"";
			String pad = StringUtil.repeat("0", padValueToLength-rounded.length());
			return rounded+pad;
		});
		List<String> roundedRowValues = roundedRowValuesStream.collect(Collectors.toList());

		return StringUtil.listToString(roundedRowValues, "|");
	}


	// GETTERS
	@API private int getWidth()
	{
		return entries[0].length;
	}

	private int getLength()
	{
		return entries.length;
	}

	@API public int getHorizontalRadius()
	{
		return (getWidth()-1)/2;
	}

	@API public int getVerticalRadius()
	{
		return (getLength()-1)/2;
	}

	@API public double getEntryAt(int x, int y)
	{
		int inEntriesX = x+getHorizontalRadius();
		int inEntriesY = y+getVerticalRadius();

		SnaportaValidate.validateInInterval(-getHorizontalRadius(), getHorizontalRadius(), "x", x);
		SnaportaValidate.validateInInterval(-getVerticalRadius(), getVerticalRadius(), "y", y);
		return entries[inEntriesY][inEntriesX];
	}

}
