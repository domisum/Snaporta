package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Colors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicSnaportaTest
{
	
	// TEST: INIT
	@Test
	void testBlankOfWidthAndHeightDimensions()
	{
		int width = 20;
		int height = 10;
		
		Snaporta snaporta = BasicSnaporta.blankOfWidthAndHeight(width, height);
		
		Assertions.assertEquals(width, snaporta.getWidth());
		Assertions.assertEquals(height, snaporta.getHeight());
	}
	
	@Test
	void testZeroSizeInitException()
	{
		Assertions.assertThrows(IllegalArgumentException.class, ()->new BasicSnaporta(new int[0][0]));
		Assertions.assertThrows(IllegalArgumentException.class, ()->new BasicSnaporta(new int[21][0]));
		Assertions.assertThrows(IllegalArgumentException.class, ()->new BasicSnaporta(new int[0][69]));
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicSnaporta.blankOfWidthAndHeight(-2, 9));
		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicSnaporta.blankOfWidthAndHeight(0, 0));
		Assertions.assertThrows(IllegalArgumentException.class, ()->BasicSnaporta.blankOfWidthAndHeight(10, 0));
	}
	
	@Test
	void testNonRectangularArrayInit()
	{
		int[][] nonRectangular = {{0, 1}, {0}};
		
		Assertions.assertThrows(IllegalArgumentException.class, ()->new BasicSnaporta(nonRectangular));
	}
	
	@Test
	void testCopyEquality()
	{
		SnaportaPainter painter = new SnaportaPainter(BasicSnaporta.blankOfWidthAndHeight(16, 9));
		painter.setColorAt(0, 3, Colors.RED);
		painter.setColorAt(4, 2, Colors.GREEN);
		
		Snaporta base = painter.toSnaporta();
		
		Snaporta copy = BasicSnaporta.copyOf(base);
		
		Assertions.assertEquals(base.getWidth(), copy.getWidth());
		Assertions.assertEquals(base.getHeight(), copy.getHeight());
		for(int y = 0; y < base.getHeight(); y++)
			for(int x = 0; x < base.getWidth(); x++)
				Assertions.assertEquals(base.getArgbAt(x, y), copy.getArgbAt(x, y));
	}
	
}
