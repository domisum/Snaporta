package io.domisum.lib.snaporta.snaportas.text.sizer;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.snaportas.text.Font;
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
		var textDimensionsCalculator = new TextDimensionsCalculator(font, baselineFontSize);
		
		var textDimensions = textDimensionsCalculator.calculateDimensions(text);
		double fontSizeDerivedFromWidth = baselineFontSize/(textDimensions.getWidth()/availableWidth);
		double fontSizeDerivedFromHeight = baselineFontSize/(textDimensions.getHeight()/availableHeight);
		
		double size = Math.min(fontSizeDerivedFromWidth, fontSizeDerivedFromHeight);
		return size;
	}
	
}
