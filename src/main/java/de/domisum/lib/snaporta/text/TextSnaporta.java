package de.domisum.lib.snaporta.text;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.SnaportaPadding;
import de.domisum.lib.snaporta.color.SnaportaColor;
import de.domisum.lib.snaporta.formatConversion.SnaportaBufferedImageConverter;
import de.domisum.lib.snaporta.text.dimensions.TextDimensions;
import de.domisum.lib.snaporta.text.dimensions.TextDimensionsCalculator;
import de.domisum.lib.snaporta.text.positioner.horizontal.HorizontalTextPositioner;
import de.domisum.lib.snaporta.text.positioner.vertical.VerticalTextPositioner;
import de.domisum.lib.snaporta.text.sizer.FontSizer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

	private final SnaportaFont font;
	private final SnaportaColor color;

	private final SnaportaPadding padding;
	private final FontSizer fontSizer;
	private final HorizontalTextPositioner horizontalTextPositioner;
	private final VerticalTextPositioner verticalTextPositioner;

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
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = createTextRenderingGraphics(bufferedImage);

		setGraphicsFont(graphics);
		graphics.setColor(color.toAwt());
		drawStringToGraphics(graphics);

		graphics.dispose();
		SnaportaBufferedImageConverter imageConverter = new SnaportaBufferedImageConverter();
		return imageConverter.convertFrom(bufferedImage);
	}

	private Graphics2D createTextRenderingGraphics(BufferedImage bufferedImage)
	{
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		return graphics;
	}

	private void setGraphicsFont(Graphics2D graphics)
	{
		double fontSize = fontSizer.size(font, width, height, padding, text);
		Font font = this.font.getFont().deriveFont((float) fontSize);
		graphics.setFont(font);
	}

	private void drawStringToGraphics(Graphics2D graphics)
	{
		float fontSizePt = graphics.getFont().getSize2D();
		TextDimensions textDimensions = new TextDimensionsCalculator(font, fontSizePt).calculateDimensions(text);

		double horizontalPosition = horizontalTextPositioner.position(width, padding, textDimensions);
		double verticalPosition = verticalTextPositioner.position(height, padding, textDimensions);

		int graphicsVerticalPosition = (int) Math.round(verticalPosition+textDimensions.getHeight());
		int graphicsHorizontalPosition = (int) Math.round(horizontalPosition);
		graphics.drawString(text, graphicsHorizontalPosition, graphicsVerticalPosition);
	}

}
