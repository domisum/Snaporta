package de.domisum.lib.snaporta.snaportas;

import de.domisum.lib.auxilium.util.PHR;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;


public class Matrix
{

	// ATTRIBUTES
	/**
	 * Array of rows of entries.
	 */
	private final double[][] entries;


	// INIT
	public Matrix(double[][] entries)
	{
		int width = entries[0].length;
		int height = entries.length;
		Validate.isTrue((height%2) == 1, "matrix height has to be uneven, was "+height);
		Validate.isTrue((width%2) == 1, "matrix width has to be uneven, was "+width);

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
	private int getHorizontalRadius()
	{
		return (entries[0].length-1)/2;
	}

	public int getVerticalRadius()
	{
		return (entries.length-1)/2;
	}

}
