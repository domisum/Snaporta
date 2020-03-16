package io.domisum.lib.snaporta.snaportas.text.positioner.horizontal;

import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

public interface HorizontalTextPositioner
{

	double position(int width, Padding padding, TextDimensions textDimensions);

}
