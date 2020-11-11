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
	private final double sigma;
	
	// UTIL
	private final MatrixInterpolator matrixInterpolator;
	
	
	// INIT
	public SmoothSnaporta(Snaporta baseSnaporta, int sigma)
	{
		this.baseSnaporta = baseSnaporta;
		this.sigma = sigma;
		
		matrixInterpolator = new MatrixInterpolator(new IgnoreOutOfBoundsMatrixOnSnaportaEvaluator(), createGaussianBlurMatrix());
	}
	
	private Matrix createGaussianBlurMatrix()
	{
		// https://en.wikipedia.org/wiki/Gaussian_blur
		
		int radius = (int) Math.ceil(3*sigma);
		int matrixSize = (radius*2)+1;
		
		double[][] kernel = new double[matrixSize][matrixSize];
		for(int kernelX = 0; kernelX < matrixSize; kernelX++)
			for(int kernelY = 0; kernelY < matrixSize; kernelY++)
			{
				double x = kernelX-radius;
				double y = kernelY-radius;
				
				double exponentNumerator = (x*x)+(y*y);
				double exponentDenominator = 2*sigma*sigma;
				double exponent = -exponentNumerator/exponentDenominator;
				
				double numerator = Math.exp(exponent);
				double denominator = 2*Math.PI*sigma*sigma;
				
				double value = numerator/denominator;
				kernel[kernelX][kernelY] = value;
			}
		
		var matrix = new Matrix(kernel).deriveNormalized();
		return matrix;
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
	public int getArgbAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		int argb = matrixInterpolator.interpolateARGBAt(baseSnaporta, x, y);
		return argb;
	}
	
}
