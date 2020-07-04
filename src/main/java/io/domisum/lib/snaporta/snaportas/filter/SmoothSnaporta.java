package io.domisum.lib.snaporta.snaportas.filter;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.matrix.Matrix;
import io.domisum.lib.snaporta.matrix.evaluator.IgnoreOutOfBoundsMatrixOnSnaportaEvaluator;
import io.domisum.lib.snaporta.snaportas.transform.resize.interpolator.matrix.MatrixInterpolator;
import io.domisum.lib.snaporta.util.SnaportaValidate;

@API
public class SmoothSnaporta
		implements Snaporta
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
		// https://en.wikipedia.org/wiki/Gaussian_blur
		
		int W = (radius*2)+1;
		double sigma = 1;
		double mean = (double) W/2;
		
		double[][] kernel = new double[W][W];
		for(int x = 0; x < W; ++x)
			for(int y = 0; y < W; ++y)
				kernel[x][y] =
						Math.exp(-0.5*(Math.pow((x-mean)/sigma, 2.0)+Math.pow((y-mean)/sigma, 2.0)))/(2*Math.PI*sigma*sigma);
		
		return new Matrix(kernel).deriveNormalized();
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
