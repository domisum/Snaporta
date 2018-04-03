package de.domisum.lib.snaporta.text;

import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.formatConversion.SnaportaBufferedImageConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class TextSnaporta implements Snaporta // TODO change to use builder
{

	// SETTINGS
	@Getter private final int width;
	@Getter private final int height;

	private final String text;
	private final SnaportaFont snaportaFont;
	private final double fontSizePt;

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
		Font font = snaportaFont.getFont().deriveFont(70f);

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = bufferedImage.createGraphics();
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
