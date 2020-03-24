package io.domisum.lib.snaporta.snaportas.text.positioner.horizontal;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

@API
public class HorizontalAlignRightTextPositioner implements HorizontalTextPositioner
{

	@Override
	public double position(int width, Padding padding, TextDimensions textDimensions)
	{
		return width-(padding.getHorizontalSum()+textDimensions.getWidth());
	}

}
