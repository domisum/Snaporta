package de.domisum.lib.snaporta.text.positioner.vertical;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.SnaportaPadding;
import de.domisum.lib.snaporta.text.dimensions.TextDimensions;

@API
public class VerticalCenteredTextPositioner implements VerticalTextPositioner
{

	@Override public double position(int height, SnaportaPadding padding, TextDimensions textDimensions)
	{
		return (height-textDimensions.getHeight())/2;
	}

}
