package de.domisum.lib.snaporta.snaportas.filter;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.matrix.Matrix;
import de.domisum.lib.snaporta.matrix.evaluator.IgnoreOutOfBoundsMatrixOnSnaportaEvaluator;
import de.domisum.lib.snaporta.snaportas.transform.resize.interpolator.matrix.MatrixInterpolator;
import de.domisum.lib.snaporta.util.SnaportaValidate;

@API
public class SmoothSnaporta implements Snaporta
{

	// SETTINGS
	private final Snaporta baseSnaporta;
	private final int radius;

	// UTIL
	private final MatrixInterpolator matrixInterpolator;


	// INIT
	public SmoothSnaporta(Snaporta baseSnaporta, int radius)
	{
		this.baseSnaporta = baseSnaporta;
		this.radius = radius;

		matrixInterpolator = new MatrixInterpolator(new IgnoreOutOfBoundsMatrixOnSnaportaEvaluator(), createGaussianBlurMatrix());
	}

	private Matrix createGaussianBlurMatrix()
	{
		int W = (radius*2)+1;
		double sigma = 1;
		double mean = W/2;

		double[][] kernel = new double[W][W];
		double kernelValueSum = 0.0;
		for(int x = 0; x < W; ++x)
			for(int y = 0; y < W; ++y)
			{
				double valueAt =
						Math.exp(-0.5*(Math.pow((x-mean)/sigma, 2.0)+Math.pow((y-mean)/sigma, 2.0)))/(2*Math.PI*sigma*sigma);
				kernel[x][y] = valueAt;

				kernelValueSum += valueAt;
			}

		// normalize
		for(int x = 0; x < W; ++x)
			for(int y = 0; y < W; ++y)
				kernel[x][y] /= kernelValueSum;

		return new Matrix(kernel);
	}


	// SNAPORTA
	@Override public int getWidth()
	{
		return baseSnaporta.getWidth();
	}

	@Override public int getHeight()
	{
		return baseSnaporta.getHeight();
	}

	@Override public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		return matrixInterpolator.interpolateARGBAt(baseSnaporta, x, y);
	}

}
