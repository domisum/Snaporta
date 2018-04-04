package de.domisum.lib.snaporta.text.positioner.horizontal;

import de.domisum.lib.snaporta.SnaportaPadding;
import de.domisum.lib.snaporta.text.dimensions.TextDimensions;

public interface HorizontalTextPositioner
{

	double position(int width, SnaportaPadding padding, TextDimensions textDimensions);

}
