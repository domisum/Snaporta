package io.domisum.lib.snaporta.snaportas.text.dimensions;

import io.domisum.lib.snaporta.snaportas.text.Font;
import lombok.RequiredArgsConstructor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
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
		graphics2D.setFont(font.getFont().deriveFont((float) fontSizePt));

		FontRenderContext fontRenderContext = graphics2D.getFontRenderContext();
		Rectangle pixelBounds = graphics2D.getFont().createGlyphVector(fontRenderContext, text).getPixelBounds(null, 0, 0);

		// have to use font metrics here, pixel bounds are somehow inaccurate don't ask me why
		double stringWidth = graphics2D.getFontMetrics().stringWidth(text);
		double stringHeight = pixelBounds.getHeight();

		graphics2D.dispose();
		return new TextDimensions(stringWidth, stringHeight);
	}

}
