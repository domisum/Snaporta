package io.domisum.lib.snaporta.snaportas.transform.interpolator.matrix;

import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.matrix.Matrix;
import io.domisum.lib.snaporta.matrix.evaluator.MatrixOnSnaportaEvaluator;
import io.domisum.lib.snaporta.snaportas.transform.interpolator.Interpolator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatrixInterpolator
	implements Interpolator
{
	
	// REFERENCES
	private final MatrixOnSnaportaEvaluator matrixOnSnaportaEvaluator;
	
	// SETTINGS
	private final Matrix interpolationMatrix;
	
	
	@Override
	public int interpolateARGBAt(Snaporta snaporta, double x, double y)
	{
		int closestPixelX = (int) Math.round(x);
		int closestPixelY = (int) Math.round(y);
		
		return matrixOnSnaportaEvaluator.evaluateToARGB(snaporta, interpolationMatrix, closestPixelX, closestPixelY);
	}
	
}
