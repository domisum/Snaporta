package de.domisum.lib.snaporta.masks;

import de.domisum.lib.snaporta.mask.BooleanMask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanMaskTest
{

	@Test void testInitSize()
	{
		int width = 7;
		int height = 13;


		BooleanMask mask = BooleanMask.onlyFalseOfWidthAndHeight(width, height);


		Assertions.assertEquals(width, mask.getWidth());
		Assertions.assertEquals(height, mask.getHeight());
	}

}
