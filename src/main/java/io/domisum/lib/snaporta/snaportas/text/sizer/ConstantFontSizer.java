package io.domisum.lib.snaporta.snaportas.text.sizer;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.Font;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class ConstantFontSizer implements FontSizer
{

	// ATTRIBUTES
	private final double fontSize;


	// SIZER
	@Override
	public double size(Font font, int width, int height, Padding padding, String text)
	{
		return fontSize;
	}

}
