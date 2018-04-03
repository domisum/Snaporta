package de.domisum.lib.snaporta.text;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.formatConversion.SnaportaBufferedImageConverter;
import de.domisum.lib.snaporta.text.sizer.SnaportaFontSizer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

@API
@RequiredArgsConstructor
public class TextSnaporta implements Snaporta // TODO change to use builder
{

	// SETTINGS
	@Getter private final int width;
	@Getter private final int height;

	private final SnaportaFont snaportaFont;
	private final SnaportaFontSizer fontSizer;
	private final String text;

	// TEMP
	private Snaporta renderedText;


	// SNAPORTA
	@Override public int getARGBAt(int x, int y)
	{
		if(renderedText == null)
			renderedText = render();

		return renderedText.getARGBAt(x, y);
	}

	private Snaporta render()
	{
		double fontSize = fontSizer.size(snaportaFont, width, height, text);
		Font font = snaportaFont.getFont().deriveFont((float) fontSize);

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		graphics.setColor(Color.RED);
		graphics.fillRect(0, 0, width, height);

		graphics.setFont(font);
		graphics.setColor(Color.BLACK);
		graphics.drawString(text, 0, height);
		graphics.dispose();


		SnaportaBufferedImageConverter imageConverter = new SnaportaBufferedImageConverter();
		return imageConverter.convertFrom(bufferedImage);
	}

}
