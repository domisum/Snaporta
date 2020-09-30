package io.domisum.lib.snaporta.matrix.evaluator;

import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.color.ColorComponent;
import io.domisum.lib.snaporta.matrix.Matrix;
import io.domisum.lib.snaporta.util.ARGBUtil;

public class IgnoreOutOfBoundsMatrixOnSnaportaEvaluator
	implements MatrixOnSnaportaEvaluator
{
	
	@Override
	public int evaluateToARGB(Snaporta snaporta, Matrix matrix, int x, int y)
	{
		double[] evaluatedComponentSum = new double[ColorComponent.values().length];
		for(int matrixY = -matrix.getVerticalRadius(); matrixY <= matrix.getVerticalRadius(); matrixY++)
			for(int matrixX = -matrix.getHorizontalRadius(); matrixX <= matrix.getHorizontalRadius(); matrixX++)
			{
				int snaportaX = x+matrixX;
				int snaportaY = y+matrixY;
				
				if(!snaporta.isInBounds(snaportaX, snaportaY))
					continue;
				
				int argbInt = snaporta.getARGBAt(snaportaX, snaportaY);
				double matrixEntry = matrix.getEntryAt(matrixX, matrixY);
				
				for(int i = 0; i < ColorComponent.values().length; i++)
				{
					var colorComponent = ColorComponent.values()[i];
					double value = ARGBUtil.getComponent(colorComponent, argbInt);
					double weightedValue = value*matrixEntry;
					evaluatedComponentSum[i] += weightedValue;
				}
			}
		
		int evaluatedAlpha = (int) Math.round(evaluatedComponentSum[0]);
		int evaluatedRed = (int) Math.round(evaluatedComponentSum[1]);
		int evaluatedGreen = (int) Math.round(evaluatedComponentSum[2]);
		int evaluatedBlue = (int) Math.round(evaluatedComponentSum[3]);
		
		evaluatedAlpha = MathUtil.clamp(0, Color.COLOR_COMPONENT_MAX, evaluatedAlpha);
		evaluatedRed = MathUtil.clamp(0, Color.COLOR_COMPONENT_MAX, evaluatedRed);
		evaluatedGreen = MathUtil.clamp(0, Color.COLOR_COMPONENT_MAX, evaluatedGreen);
		evaluatedBlue = MathUtil.clamp(0, Color.COLOR_COMPONENT_MAX, evaluatedBlue);
		
		int color = ARGBUtil.toARGB(evaluatedAlpha, evaluatedRed, evaluatedGreen, evaluatedBlue);
		return color;
	}
	
}
