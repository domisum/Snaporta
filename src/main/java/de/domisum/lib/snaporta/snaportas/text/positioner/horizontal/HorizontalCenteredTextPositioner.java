package de.domisum.lib.snaporta.snaportas.text.positioner.horizontal;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

@API
public class HorizontalCenteredTextPositioner implements HorizontalTextPositioner
{

	@Override
	public double position(int width, Padding padding, TextDimensions textDimensions)
	{
		double availableWidth = width-textDimensions.getWidth()-padding.getHorizontalSum();
		return (availableWidth/2)+padding.getLeft();
	}

}
