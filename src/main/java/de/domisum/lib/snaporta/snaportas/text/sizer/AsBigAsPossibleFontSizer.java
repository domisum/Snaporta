package de.domisum.lib.snaporta.snaportas.text.sizer;

import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.snaportas.text.Font;
import de.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;
import de.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensionsCalculator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AsBigAsPossibleFontSizer implements FontSizer
{

	// SIZING
	@Override public double size(Font font, int width, int height, Padding padding, String text)
	{
		int availableWidth = width-padding.getHorizontal();
		int availableHeight = height-padding.getVertical();

		double baselineFontSize = 100; // use big font size to reduce pixel rounding errors
		TextDimensionsCalculator textDimensionsCalculator = new TextDimensionsCalculator(font, baselineFontSize);

		TextDimensions textDimensions = textDimensionsCalculator.calculateDimensions(text);
		double fontSizeDerivedFromWidth = baselineFontSize/(textDimensions.getWidth()/availableWidth);
		double fontSizeDerivedFromHeight = baselineFontSize/(textDimensions.getHeight()/availableHeight);

		return Math.min(fontSizeDerivedFromWidth, fontSizeDerivedFromHeight);
	}

}