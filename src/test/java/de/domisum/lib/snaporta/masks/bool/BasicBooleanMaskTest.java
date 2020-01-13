package de.domisum.lib.snaporta.masks.bool;

import de.domisum.lib.snaporta.mask.bool.BasicBooleanMask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicBooleanMaskTest
{

	@Test
	void testInitSize()
	{
		int width = 7;
		int height = 13;

		BasicBooleanMask mask = BasicBooleanMask.onlyFalseOfWidthAndHeight(width, height);

		Assertions.assertEquals(width, mask.getWidth());
		Assertions.assertEquals(height, mask.getHeight());
	}

}
