package io.domisum.lib.snaporta.snaportas.text.positioner.vertical;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

@API
public class VerticalAlignBottomTextPositioner
		implements VerticalTextPositioner
{
	
	@Override
	public double position(int height, Padding padding, TextDimensions textDimensions)
	{
		return height-(padding.getBottom()+textDimensions.getHeight());
	}
	
}
