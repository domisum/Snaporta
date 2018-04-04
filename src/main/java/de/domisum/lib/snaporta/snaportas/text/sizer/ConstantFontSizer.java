package de.domisum.lib.snaporta.snaportas.text.sizer;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.snaportas.text.Font;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class ConstantFontSizer implements FontSizer
{

	// ATTRIBUTES
	private final double fontSize;


	// SIZER
	@Override public double size(Font font, int width, int height, Padding padding, String text)
	{
		return fontSize;
	}

}
