package de.domisum.lib.snaporta.matrix.evaluator;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.matrix.Matrix;

public interface MatrixOnSnaportaEvaluator
{

	int evaluateToARGB(Snaporta snaporta, Matrix matrix, int x, int y);

	@API
	default Color evaluate(Snaporta snaporta, Matrix matrix, int x, int y)
	{
		return Color.fromARGBInt(evaluateToARGB(snaporta, matrix, x, y));
	}

}
