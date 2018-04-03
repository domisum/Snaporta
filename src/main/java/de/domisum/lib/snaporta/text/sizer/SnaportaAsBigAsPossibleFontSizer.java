package de.domisum.lib.snaporta.text.sizer;

import de.domisum.lib.snaporta.text.SnaportaFont;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SnaportaAsBigAsPossibleFontSizer implements SnaportaFontSizer
{

	@Override public double size(SnaportaFont snaportaFont, int width, int height, String text)
	{
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = image.createGraphics();

		float baselineFontSize = 100f; // big font to reduce pixel rounding errors
		Font font = snaportaFont.getFont().deriveFont(baselineFontSize);
		FontMetrics fontMetrics = graphics2D.getFontMetrics(font);

		double stringWidth = fontMetrics.stringWidth(text);
		double stringHeight = fontMetrics.getAscent();

		double fontSizeDerivedFromWidth = baselineFontSize/(stringWidth/width);
		double fontSizeDerivedFromHeight = baselineFontSize/(stringHeight/height);

		return Math.min(fontSizeDerivedFromWidth, fontSizeDerivedFromHeight);
	}

}
