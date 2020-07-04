package io.domisum.lib.snaporta.snaportas.text.positioner.vertical;

import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

public interface VerticalTextPositioner
{
	
	double position(int height, Padding padding, TextDimensions textDimensions);
	
}
