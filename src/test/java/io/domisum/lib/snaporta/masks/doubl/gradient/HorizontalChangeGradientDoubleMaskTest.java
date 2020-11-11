package io.domisum.lib.snaporta.masks.doubl.gradient;

import io.domisum.lib.snaporta.mask.doubl.DoubleMask;
import io.domisum.lib.snaporta.mask.doubl.gradient.HorizontalChangeGradientDoubleMask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HorizontalChangeGradientDoubleMaskTest
{
	
	@Test
	void testLeftLow()
	{
		DoubleMask doubleMask = new HorizontalChangeGradientDoubleMask(200, 100, 50, 150);
		
		Assertions.assertEquals(0.0, doubleMask.getValueAt(5, 10));
		Assertions.assertEquals(0.0, doubleMask.getValueAt(50, 10));
		
		Assertions.assertEquals(0.25, doubleMask.getValueAt(75, 10));
		Assertions.assertEquals(0.5, doubleMask.getValueAt(100, 10));
		Assertions.assertEquals(0.75, doubleMask.getValueAt(125, 10));
		
		Assertions.assertEquals(1.0, doubleMask.getValueAt(150, 10));
		Assertions.assertEquals(1.0, doubleMask.getValueAt(190, 10));
	}
	
	@Test
	void testRightLow()
	{
		DoubleMask doubleMask = new HorizontalChangeGradientDoubleMask(1000, 20, 600, 400);
		
		Assertions.assertEquals(1.0, doubleMask.getValueAt(5, 0));
		Assertions.assertEquals(1.0, doubleMask.getValueAt(400, 0));
		
		Assertions.assertEquals(0.5, doubleMask.getValueAt(500, 0));
		
		Assertions.assertEquals(0.0, doubleMask.getValueAt(600, 0));
		Assertions.assertEquals(0.0, doubleMask.getValueAt(650, 0));
	}
	
}
