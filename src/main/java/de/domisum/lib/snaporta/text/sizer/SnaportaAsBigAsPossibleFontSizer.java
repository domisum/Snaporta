package de.domisum.lib.snaporta.text.sizer;

import de.domisum.lib.snaporta.text.SnaportaFont;
import de.domisum.lib.snaporta.text.dimensions.TextDimensions;
import de.domisum.lib.snaporta.text.dimensions.TextDimensionsCalculator;

public class SnaportaAsBigAsPossibleFontSizer implements FontSizer
{

	@Override public double size(SnaportaFont snaportaFont, int width, int height, String text)
	{
		double baselineFontSize = 100; // use big font size to reduce pixel rounding errors
		TextDimensionsCalculator textDimensionsCalculator = new TextDimensionsCalculator(snaportaFont, baselineFontSize);

		TextDimensions textDimensions = textDimensionsCalculator.calculateDimensions(text);
		double fontSizeDerivedFromWidth = baselineFontSize/(textDimensions.getWidth()/width);
		double fontSizeDerivedFromHeight = baselineFontSize/(textDimensions.getHeight()/height);

		return Math.min(fontSizeDerivedFromWidth, fontSizeDerivedFromHeight);
	}

}
