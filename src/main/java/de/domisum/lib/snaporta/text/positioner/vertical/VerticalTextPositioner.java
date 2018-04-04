package de.domisum.lib.snaporta.text.positioner.vertical;

import de.domisum.lib.snaporta.SnaportaPadding;
import de.domisum.lib.snaporta.text.dimensions.TextDimensions;

public interface VerticalTextPositioner
{

	double position(int height, SnaportaPadding snaportaPadding, TextDimensions textDimensions);

}
