package de.domisum.lib.snaporta.snaportas.text.positioner.horizontal;

import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

public interface HorizontalTextPositioner
{

	double position(int width, Padding padding, TextDimensions textDimensions);

}
