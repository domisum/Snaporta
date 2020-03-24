package io.domisum.lib.snaporta.matrix.evaluator;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.matrix.Matrix;

public interface MatrixOnSnaportaEvaluator
{

	int evaluateToARGB(Snaporta snaporta, Matrix matrix, int x, int y);

	@API
	default Color evaluate(Snaporta snaporta, Matrix matrix, int x, int y)
	{
		return Color.fromARGBInt(evaluateToARGB(snaporta, matrix, x, y));
	}

}
