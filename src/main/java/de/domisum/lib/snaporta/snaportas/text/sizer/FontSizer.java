package de.domisum.lib.snaporta.snaportas.text.sizer;

import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.snaportas.text.Font;

public interface FontSizer
{

	double size(Font font, int width, int height, Padding padding, String text);

}
