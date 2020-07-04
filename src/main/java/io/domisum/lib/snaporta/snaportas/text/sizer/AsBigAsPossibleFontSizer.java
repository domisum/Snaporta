package io.domisum.lib.snaporta.snaportas.text.sizer;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.Font;
import io.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;
import io.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensionsCalculator;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class AsBigAsPossibleFontSizer
		implements FontSizer
{
	
	// SIZING
	@Override
	public double size(Font font, int width, int height, Padding padding, String text)
	{
		int availableWidth = width-padding.getHorizontalSum();
		int availableHeight = height-padding.getVerticalSum();
		
		double baselineFontSize = 1000; // use big font size to reduce pixel rounding errors
		TextDimensionsCalculator textDimensionsCalculator = new TextDimensionsCalculator(font, baselineFontSize);
		
		TextDimensions textDimensions = textDimensionsCalculator.calculateDimensions(text);
		double fontSizeDerivedFromWidth = baselineFontSize/(textDimensions.getWidth()/availableWidth);
		double fontSizeDerivedFromHeight = baselineFontSize/(textDimensions.getHeight()/availableHeight);
		
		return Math.min(fontSizeDerivedFromWidth, fontSizeDerivedFromHeight);
	}
	
}
