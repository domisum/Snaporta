package de.domisum.lib.snaporta.snaportas.text;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.color.Colors;
import de.domisum.lib.snaporta.formatconversion.SnaportaBufferedImageConverter;
import de.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensions;
import de.domisum.lib.snaporta.snaportas.text.dimensions.TextDimensionsCalculator;
import de.domisum.lib.snaporta.snaportas.text.positioner.horizontal.HorizontalAlignLeftTextPositioner;
import de.domisum.lib.snaporta.snaportas.text.positioner.horizontal.HorizontalAlignRightTextPositioner;
import de.domisum.lib.snaporta.snaportas.text.positioner.horizontal.HorizontalCenteredTextPositioner;
import de.domisum.lib.snaporta.snaportas.text.positioner.horizontal.HorizontalTextPositioner;
import de.domisum.lib.snaporta.snaportas.text.positioner.vertical.VerticalCenteredTextPositioner;
import de.domisum.lib.snaporta.snaportas.text.positioner.vertical.VerticalTextPositioner;
import de.domisum.lib.snaporta.snaportas.text.sizer.AsBigAsPossibleFontSizer;
import de.domisum.lib.snaporta.snaportas.text.sizer.ConstantFontSizer;
import de.domisum.lib.snaporta.snaportas.text.sizer.FontSizer;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

@API
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextSnaporta implements Snaporta
{

	// CONSTANTS
	private static final int DEFAULT_FONT_SIZE = 24;

	// SNAPORTA
	@Getter
	private final int width;
	@Getter
	private final int height;

	// FONT
	private Font font = Font.defaultFont();
	private Color color = Colors.BLACK;

	// POSITIONING
	private Padding padding = Padding.none();
	private FontSizer fontSizer = new ConstantFontSizer(DEFAULT_FONT_SIZE);
	private HorizontalTextPositioner horizontalTextPositioner;
	private VerticalTextPositioner verticalTextPositioner;

	// CONTENT
	private final String text;

	// DEBUG
	private boolean drawPaddingOutline = false;


	// TEMP
	private Snaporta renderedText;


	// SNAPORTA
	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return renderedText.getARGBAt(x, y);
	}


	// RENDERING
	private Snaporta render()
	{
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = createTextRenderingGraphics(bufferedImage);

		if(drawPaddingOutline)
			drawPaddingOutline(graphics);

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


	private void drawPaddingOutline(Graphics2D graphics)
	{
		java.awt.Color colorBefore = graphics.getColor();

		Color outlineColor = color.deriveOpposite();
		graphics.setColor(outlineColor.toAwt());

		graphics.drawRect(0, 0, width-1, height-1);
		graphics.drawRect(padding.getLeft(), padding.getTop(), width-padding.getHorizontalSum(), height-padding.getVerticalSum());

		graphics.setColor(colorBefore);
	}

	private void setGraphicsFont(Graphics2D graphics)
	{
		double fontSize = fontSizer.size(font, width, height, padding, text);
		java.awt.Font font = this.font.getFont().deriveFont((float) fontSize);
		graphics.setFont(font);
	}

	private void drawStringToGraphics(Graphics2D graphics)
	{
		float fontSizePt = graphics.getFont().getSize2D();
		TextDimensions textDimensions = new TextDimensionsCalculator(font, fontSizePt).calculateDimensions(text);

		double horizontalPosition = horizontalTextPositioner.position(width, padding, textDimensions);
		double verticalPosition = verticalTextPositioner.position(height, padding, textDimensions);

		int graphicsHorizontalPosition = (int) Math.round(horizontalPosition);
		int graphicsVerticalPosition = (int) Math.round(verticalPosition+textDimensions.getHeight());
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
		@API
		public TextSnaportaBuilder font(Font font)
		{
			textSnaporta.font = font;
			return this;
		}

		@API
		public TextSnaportaBuilder color(Color color)
		{
			textSnaporta.color = color;
			return this;
		}

		@API
		public TextSnaportaBuilder padding(Padding padding)
		{
			textSnaporta.padding = padding;
			return this;
		}

		@API
		public TextSnaportaBuilder fontSizer(FontSizer fontSizer)
		{
			textSnaporta.fontSizer = fontSizer;
			return this;
		}

		@API
		public TextSnaportaBuilder horizontalTextPositioner(HorizontalTextPositioner horizontalTextPositioner)
		{
			textSnaporta.horizontalTextPositioner = horizontalTextPositioner;
			return this;
		}

		@API
		public TextSnaportaBuilder verticalTextPositioner(VerticalTextPositioner verticalTextPositioner)
		{
			textSnaporta.verticalTextPositioner = verticalTextPositioner;
			return this;
		}


		@API
		public TextSnaportaBuilder drawPaddingOutline()
		{
			textSnaporta.drawPaddingOutline = true;
			return this;
		}


		// SETTINGS SHORTCUTS
		@API
		public TextSnaportaBuilder fontAsBigAsPossible()
		{
			return fontSizer(new AsBigAsPossibleFontSizer());
		}


		@API
		public TextSnaportaBuilder centerHorizontally()
		{
			return horizontalTextPositioner(new HorizontalCenteredTextPositioner());
		}

		@API
		public TextSnaportaBuilder centerVertically()
		{
			return verticalTextPositioner(new VerticalCenteredTextPositioner());
		}

		@API
		public TextSnaportaBuilder alignLeft()
		{
			textSnaporta.horizontalTextPositioner = new HorizontalAlignLeftTextPositioner();
			return this;
		}

		@API
		public TextSnaportaBuilder alignRight()
		{
			textSnaporta.horizontalTextPositioner = new HorizontalAlignRightTextPositioner();
			return this;
		}


		// BUILD
		@API
		public TextSnaporta build()
		{
			Validate.notNull(textSnaporta.horizontalTextPositioner, "horizontalTextPositioner not set");
			Validate.notNull(textSnaporta.verticalTextPositioner, "verticalTextPositioner not set");

			textSnaporta.renderedText = textSnaporta.render();
			return textSnaporta;
		}

	}

}
