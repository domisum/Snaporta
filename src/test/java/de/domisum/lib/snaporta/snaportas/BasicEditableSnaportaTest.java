package de.domisum.lib.snaporta.snaportas;

import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Colors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicEditableSnaportaTest
{

	// TEST: INIT
	@Test void testBlankOfWidthAndHeightDimensions()
	{
		int width = 20;
		int height = 10;


		Snaporta snaporta = BasicEditableSnaporta.blankOfWidthAndHeight(width, height);


		Assertions.assertEquals(width, snaporta.getWidth());
		Assertions.assertEquals(height, snaporta.getHeight());
	}

	@Test void testZeroSizeInitException()
	{
		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicEditableSnaporta.fromARGBPixels(new int[0][0]));
		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicEditableSnaporta.fromARGBPixels(new int[21][0]));
		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicEditableSnaporta.fromARGBPixels(new int[0][69]));

		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicEditableSnaporta.blankOfWidthAndHeight(-2, 9));
		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicEditableSnaporta.blankOfWidthAndHeight(0, 0));
		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicEditableSnaporta.blankOfWidthAndHeight(10, 0));
	}

	@Test void testNonRectangularArrayInit()
	{
		int[][] nonRectangular = {{0, 1}, {0}};


		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicEditableSnaporta.fromARGBPixels(nonRectangular));
	}

	@Test void testCopyEquality()
	{
		BasicEditableSnaporta base = BasicEditableSnaporta.blankOfWidthAndHeight(16, 9);
		base.setColorAt(0, 3, Colors.RED);
		base.setColorAt(4, 2, Colors.GREEN);


		Snaporta copy = BasicEditableSnaporta.copyOf(base);


		Assertions.assertEquals(base.getWidth(), copy.getWidth());
		Assertions.assertEquals(base.getHeight(), copy.getHeight());
		for(int y = 0; y < base.getHeight(); y++)
			for(int x = 0; x < base.getWidth(); x++)
				Assertions.assertEquals(base.getARGBAt(x, y), copy.getARGBAt(x, y));
	}

}
