package de.domisum.lib.snaporta.text;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.SnaportaPadding;
import de.domisum.lib.snaporta.color.SnaportaColor;
import de.domisum.lib.snaporta.color.SnaportaColors;
import de.domisum.lib.snaporta.formatConversion.SnaportaBufferedImageConverter;
import de.domisum.lib.snaporta.text.dimensions.TextDimensions;
import de.domisum.lib.snaporta.text.dimensions.TextDimensionsCalculator;
import de.domisum.lib.snaporta.text.positioner.horizontal.HorizontalCenteredTextPositioner;
import de.domisum.lib.snaporta.text.positioner.horizontal.HorizontalTextPositioner;
import de.domisum.lib.snaporta.text.positioner.vertical.VerticalCenteredTextPositioner;
import de.domisum.lib.snaporta.text.positioner.vertical.VerticalTextPositioner;
import de.domisum.lib.snaporta.text.sizer.FontSizer;
import de.domisum.lib.snaporta.text.sizer.SnaportaConstantFontSizer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

@API
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextSnaporta implements Snaporta
{

	// CONSTANTS
	private static final int DEFAULT_FONT_SIZE = 16;

	// SNAPORTA
	@Getter private final int width;
	@Getter private final int height;

	// FONT
	private SnaportaFont font = SnaportaFont.defaultFont();
	private SnaportaColor color = SnaportaColors.BLACK;

	// POSITIONING
	private SnaportaPadding padding = SnaportaPadding.none();
	private FontSizer fontSizer = new SnaportaConstantFontSizer(DEFAULT_FONT_SIZE);
	private HorizontalTextPositioner horizontalTextPositioner;
	private VerticalTextPositioner verticalTextPositioner;

	// CONTENT
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


	// RENDERING
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


	// BUILDER
	@API
	public static final class TextSnaportaBuilder
	{

		private final TextSnaporta textSnaporta;


		// INIT
		public TextSnaportaBuilder(int width, int height, String text)
		{
			textSnaporta = new TextSnaporta(width, height, text);
		}


		// SETTINGS
		@API public TextSnaportaBuilder font(SnaportaFont font)
		{
			textSnaporta.font = font;
			return this;
		}

		@API public TextSnaportaBuilder color(SnaportaColor color)
		{
			textSnaporta.color = color;
			return this;
		}

		@API public TextSnaportaBuilder padding(SnaportaPadding padding)
		{
			textSnaporta.padding = padding;
			return this;
		}

		@API public TextSnaportaBuilder fontSizer(FontSizer fontSizer)
		{
			textSnaporta.fontSizer = fontSizer;
			return this;
		}

		@API public TextSnaportaBuilder horizontalTextPositioner(HorizontalTextPositioner horizontalTextPositioner)
		{
			textSnaporta.horizontalTextPositioner = horizontalTextPositioner;
			return this;
		}

		@API public TextSnaportaBuilder verticalTextPositioner(VerticalTextPositioner verticalTextPositioner)
		{
			textSnaporta.verticalTextPositioner = verticalTextPositioner;
			return this;
		}


		// SETTINGS SHORTCUTS
		@API public TextSnaportaBuilder centerHorizontally()
		{
			return horizontalTextPositioner(new HorizontalCenteredTextPositioner());
		}

		@API public TextSnaportaBuilder centerVertically()
		{
			return verticalTextPositioner(new VerticalCenteredTextPositioner());
		}


		// BUILD
		@API public TextSnaporta build()
		{
			return textSnaporta;
		}

	}

}
