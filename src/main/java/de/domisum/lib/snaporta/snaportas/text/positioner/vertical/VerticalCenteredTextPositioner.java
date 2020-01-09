package de.domisum.lib.snaporta.snaportas.text.positioner.vertical;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

@API
public class VerticalCenteredTextPositioner implements VerticalTextPositioner
{

	@Override
	public double position(int height, Padding padding, TextDimensions textDimensions)
	{
		double availableHeight = height-textDimensions.getHeight()-padding.getVerticalSum();
		return (availableHeight/2)+padding.getTop();
	}

}
