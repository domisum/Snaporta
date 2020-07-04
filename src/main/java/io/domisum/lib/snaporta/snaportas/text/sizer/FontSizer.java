package io.domisum.lib.snaporta.snaportas.text.sizer;

import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.Font;

public interface FontSizer
{
	
	double size(Font font, int width, int height, Padding padding, String text);
	
}
