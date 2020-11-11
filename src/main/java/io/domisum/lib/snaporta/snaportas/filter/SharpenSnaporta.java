package io.domisum.lib.snaporta.snaportas.filter;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.matrix.Matrix;
import io.domisum.lib.snaporta.matrix.evaluator.IgnoreOutOfBoundsMatrixOnSnaportaEvaluator;
import io.domisum.lib.snaporta.snaportas.transform.resize.interpolator.matrix.MatrixInterpolator;
import io.domisum.lib.snaporta.util.SnaportaValidate;

@API
public class SharpenSnaporta
	implements Snaporta
{
	
	// INPUT
	private final Snaporta baseSnaporta;
	
	// UTIL
	private final MatrixInterpolator matrixInterpolator;
	
	
	// INIT
	public SharpenSnaporta(Snaporta baseSnaporta, double sharpness)
	{
		this.baseSnaporta = baseSnaporta;
		
		var evaluator = new IgnoreOutOfBoundsMatrixOnSnaportaEvaluator();
		matrixInterpolator = new MatrixInterpolator(evaluator, createSharpenMatrix(sharpness));
	}
	
	private Matrix createSharpenMatrix(double s)
	{
		double[] line1 = {-1*s, -1*s, -1*s};
		double[] line2 = {-1*s, (8*s)+1, -1*s};
		double[] line3 = {-1*s, -1*s, -1*s};
		
		double[][] kernel = {line1, line2, line3};
		var matrix = new Matrix(kernel);
		
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
