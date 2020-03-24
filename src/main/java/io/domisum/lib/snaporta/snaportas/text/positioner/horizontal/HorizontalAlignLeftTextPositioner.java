package io.domisum.lib.snaporta.snaportas.text.positioner.horizontal;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

@API
public class HorizontalAlignLeftTextPositioner implements HorizontalTextPositioner
{

	@Override
	public double position(int width, Padding padding, TextDimensions textDimensions)
	{
		return padding.getLeft();
	}

}
