package de.domisum.lib.snaporta.matrix.evaluator;

import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.ColorComponent;
import de.domisum.lib.snaporta.matrix.Matrix;
import de.domisum.lib.snaporta.util.ARGBUtil;

public class IgnoreOutOfBoundsMatrixOnSnaportaEvaluator implements MatrixOnSnaportaEvaluator
{

	@Override public int evaluateToARGB(Snaporta snaporta, Matrix matrix, int x, int y)
	{
		double[] evaluatedComponentSum = new double[ColorComponent.values().length];
		double matrixEntrySum = 0;

		for(int matrixX = -matrix.getHorizontalRadius(); matrixX <= matrix.getHorizontalRadius(); matrixX++)
			for(int matrixY = -matrix.getVerticalRadius(); matrixY <= matrix.getHorizontalRadius(); matrixY++)
			{
				int snaportaX = x+matrixX;
				int snaportaY = y+matrixY;

				if(!snaporta.isInBounds(snaportaX, snaportaY))
					continue;

				int argbInt = snaporta.getARGBAt(snaportaX, snaportaY);
				double matrixEntry = matrix.getEntryAt(matrixX, matrixY);
				matrixEntrySum += matrixEntry;

				for(int i = 0; i < ColorComponent.values().length; i++)
				{
					ColorComponent colorComponent = ColorComponent.values()[i];
					double value = ARGBUtil.getComponent(colorComponent, argbInt);
					double weightedValue = value*matrixEntry;
					evaluatedComponentSum[i] += weightedValue;
				}
			}

		int evaluatedAlpha = (int) Math.round(evaluatedComponentSum[0]/matrixEntrySum);
		int evaluatedRed = (int) Math.round(evaluatedComponentSum[1]/matrixEntrySum);
		int evaluatedGreen = (int) Math.round(evaluatedComponentSum[2]/matrixEntrySum);
		int evaluatedBlue = (int) Math.round(evaluatedComponentSum[3]/matrixEntrySum);

		return ARGBUtil.toARGB(evaluatedAlpha, evaluatedRed, evaluatedGreen, evaluatedBlue);
	}

}
