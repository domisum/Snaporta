package de.domisum.lib.snaporta.text.positioner.horizontal;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.text.dimensions.TextDimensions;

@API
public class HorizontalCenteredTextPositioner implements HorizontalTextPositioner
{

	@Override public double position(int width, TextDimensions textDimensions)
	{
		return (width-textDimensions.getWidth())/2;
	}

}
