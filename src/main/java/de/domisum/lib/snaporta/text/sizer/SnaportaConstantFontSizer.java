package de.domisum.lib.snaporta.text.sizer;

import de.domisum.lib.snaporta.text.SnaportaFont;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SnaportaConstantFontSizer implements SnaportaFontSizer
{

	// ATTRIBUTES
	private final double fontSize;


	// SIZER
	@Override public double size(SnaportaFont snaportaFont, int width, int height, String text)
	{
		return fontSize;
	}

}
