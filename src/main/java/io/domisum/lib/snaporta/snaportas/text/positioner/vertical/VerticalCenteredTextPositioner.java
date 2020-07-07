package io.domisum.lib.snaporta.snaportas.text.positioner.vertical;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

@API
public class VerticalCenteredTextPositioner
		implements VerticalTextPositioner
{
	
	@Override
	public double position(int height, Padding padding, TextDimensions textDimensions)
	{
		double availableHeight = height-textDimensions.getHeight()-padding.getVerticalSum();
		double pos = (availableHeight/2)+padding.getTop();
		
		return pos;
	}
	
}
