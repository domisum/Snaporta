package de.domisum.lib.snaporta.snaportas.text.sizer;

import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.snaportas.text.SnaportaFont;

public interface FontSizer
{

	double size(SnaportaFont snaportaFont, int width, int height, Padding padding, String text);

}
