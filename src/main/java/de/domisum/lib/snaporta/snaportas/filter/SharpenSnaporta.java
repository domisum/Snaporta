package de.domisum.lib.snaporta.snaportas.filter;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.matrix.Matrix;
import de.domisum.lib.snaporta.matrix.evaluator.IgnoreOutOfBoundsMatrixOnSnaportaEvaluator;
import de.domisum.lib.snaporta.snaportas.transform.resize.interpolator.matrix.MatrixInterpolator;
import de.domisum.lib.snaporta.util.SnaportaValidate;

@API
public class SharpenSnaporta implements Snaporta
{

	// INPUT
	private final Snaporta baseSnaporta;

	// UTIL
	private final MatrixInterpolator matrixInterpolator;


	// INIT
	public SharpenSnaporta(Snaporta baseSnaporta, double sharpness)
	{
		this.baseSnaporta = baseSnaporta;
		matrixInterpolator = new MatrixInterpolator(new IgnoreOutOfBoundsMatrixOnSnaportaEvaluator(),
				createSharpenMatrix(sharpness)
		);
	}

	private Matrix createSharpenMatrix(double s)
	{
		double[] line1 = {-1*s, -1*s, -1*s};
		double[] line2 = {-1*s, (8*s)+1, -1*s};
		double[] line3 = {-1*s, -1*s, -1*s};

		double[][] kernel = {line1, line2, line3};
		return new Matrix(kernel);
	}


	// SNAPORTA
	@Override
	public int getWidth()
	{
		return baseSnaporta.getWidth();
	}

	@Override
	public int getHeight()
	{
		return baseSnaporta.getHeight();
	}

	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		return matrixInterpolator.interpolateARGBAt(baseSnaporta, x, y);
	}

}
