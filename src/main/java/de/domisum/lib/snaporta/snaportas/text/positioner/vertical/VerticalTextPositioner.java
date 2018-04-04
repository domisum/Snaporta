package de.domisum.lib.snaporta.snaportas.text.positioner.vertical;

import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;

public interface VerticalTextPositioner
{

	double position(int height, Padding padding, TextDimensions textDimensions);

}
