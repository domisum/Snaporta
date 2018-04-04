package de.domisum.lib.snaporta.text.sizer;

import de.domisum.lib.snaporta.SnaportaPadding;
import de.domisum.lib.snaporta.text.SnaportaFont;

public interface FontSizer
{

	double size(SnaportaFont snaportaFont, int width, int height, SnaportaPadding snaportaPadding, String text);

}
