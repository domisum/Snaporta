package de.domisum.lib.snaporta.snaportas.resize.interpolator.matrix;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.matrix.Matrix;
import de.domisum.lib.snaporta.matrix.evaluator.MatrixOnSnaportaEvaluator;

public class LinearInterpolator extends MatrixInterpolator
{

	// CONSTANTS
	private static final int DEFAULT_MATRIX_RADIUS = 1;


	// INIT
	@API public static LinearInterpolator ofDefaultRadius(MatrixOnSnaportaEvaluator matrixOnSnaportaEvaluator)
	{
		return new LinearInterpolator(matrixOnSnaportaEvaluator, DEFAULT_MATRIX_RADIUS);
	}

	public LinearInterpolator(MatrixOnSnaportaEvaluator matrixOnSnaportaEvaluator, int matrixRadius)
	{
		super(matrixOnSnaportaEvaluator, generateInterpolationMatrix(matrixRadius));
	}

	private static Matrix generateInterpolationMatrix(int matrixRadius)
	{
		int matrixSideLength = (matrixRadius*2)+1;
		double[][] matrixEntries = new double[matrixSideLength][matrixSideLength];

		for(int matrixX = -matrixRadius; matrixX <= matrixRadius; matrixX++)
			for(int matrixY = -matrixRadius; matrixY <= matrixRadius; matrixY++)
			{
				double distance = Math.sqrt((matrixX*matrixX)+(matrixY*matrixY));
				int referenceDistance = matrixRadius+1;
				double relativeProximity = Math.max(0, (referenceDistance-distance)/referenceDistance);

				int inEntriesX = matrixX+matrixRadius;
				int inEntriesY = matrixY+matrixRadius;

				matrixEntries[inEntriesY][inEntriesX] = relativeProximity;
			}

		return new Matrix(matrixEntries);
	}

}
