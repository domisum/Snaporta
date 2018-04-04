package de.domisum.lib.snaporta.snaportas;

import de.domisum.lib.auxilium.util.PHR;
import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;

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

			entries[i] = Arrays.copyOf(row, rowLength);
		}
	}


	// GETTERS
	@API public int getHorizontalRadius()
	{
		return (entries[0].length-1)/2;
	}

	@API public int getVerticalRadius()
	{
		return (entries.length-1)/2;
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
