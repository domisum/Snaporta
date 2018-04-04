package de.domisum.lib.snaporta.text.sizer;

import de.domisum.lib.snaporta.SnaportaPadding;
import de.domisum.lib.snaporta.text.SnaportaFont;
import de.domisum.lib.snaporta.text.dimensions.TextDimensions;
import de.domisum.lib.snaporta.text.dimensions.TextDimensionsCalculator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SnaportaAsBigAsPossibleFontSizer implements FontSizer
{

	// SIZING
	@Override public double size(SnaportaFont snaportaFont, int width, int height, SnaportaPadding padding, String text)
	{
		int availableWidth = width-padding.getHorizontal();
		int availableHeight = height-padding.getVertical();

		double baselineFontSize = 100; // use big font size to reduce pixel rounding errors
		TextDimensionsCalculator textDimensionsCalculator = new TextDimensionsCalculator(snaportaFont, baselineFontSize);

		TextDimensions textDimensions = textDimensionsCalculator.calculateDimensions(text);
		double fontSizeDerivedFromWidth = baselineFontSize/(textDimensions.getWidth()/availableWidth);
		double fontSizeDerivedFromHeight = baselineFontSize/(textDimensions.getHeight()/availableHeight);

		return Math.min(fontSizeDerivedFromWidth, fontSizeDerivedFromHeight);
	}

}
