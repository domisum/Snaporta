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

	// SETTINGS
	private final Snaporta baseSnaporta;

	// UTIL
	private final MatrixInterpolator matrixInterpolator;


	// INIT
	public SharpenSnaporta(Snaporta baseSnaporta, double sharpness)
	{
		this.baseSnaporta = baseSnaporta;

		System.out.println(createSharpenMatrix(sharpness));
		matrixInterpolator = new MatrixInterpolator(new IgnoreOutOfBoundsMatrixOnSnaportaEvaluator(),
				createSharpenMatrix(sharpness)
		);
	}

	private Matrix createSharpenMatrix(double s)
	{
		double[][] kernel = {{-1*s, -1*s, -1*s}, {-1*s, (8*s)+1, -1*s}, {-1*s, -1*s, -1*s}};
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
