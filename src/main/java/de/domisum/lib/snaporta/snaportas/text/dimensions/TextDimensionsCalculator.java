package de.domisum.lib.snaporta.snaportas.text.dimensions;

import de.domisum.lib.snaporta.snaportas.text.Font;
import lombok.RequiredArgsConstructor;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class TextDimensionsCalculator
{

	private final Font font;
	private final double fontSizePt;


	public TextDimensions calculateDimensions(String text)
	{
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = image.createGraphics();

		java.awt.Font font = this.font.getFont().deriveFont((float) fontSizePt);
		FontMetrics fontMetrics = graphics2D.getFontMetrics(font);

		double stringWidth = fontMetrics.stringWidth(text);
		double stringHeight = fontMetrics.getAscent();
		graphics2D.dispose();

		return new TextDimensions(stringWidth, stringHeight);
	}

}
