package de.domisum.lib.snaporta.text.sizer;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.text.SnaportaFont;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class SnaportaConstantFontSizer implements FontSizer
{

	// ATTRIBUTES
	private final double fontSize;


	// SIZER
	@Override public double size(SnaportaFont snaportaFont, int width, int height, String text)
	{
		return fontSize;
	}

}
