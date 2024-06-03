package io.domisum.lib.snaporta.snaportas.text;

import com.google.common.base.Suppliers;
import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.datacontainers.bound.IntBounds2D;
import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.formatconversion.SnaportaBufferedImageConverter;
import io.domisum.lib.snaporta.snaportas.transform.ViewportSnaporta;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

@API
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextSnaporta
	implements Snaporta
{
	
	// ESSENTIAL
	@Getter
	private final int width;
	@Getter
	private final int height;
	@Getter private final String text;
	
	// FONT
	private final Font font;
	@Nullable
	private final Integer maxFontSize; // null means 'as big as possible'
	private final Color fontColor;
	
	// POSITIONING
	private final Padding padding;
	private final HorizontalAlignment horizontalAlignment;
	private final VerticalAlignment verticalAlignment;
	
	// RENDERED
	private final Supplier<Rendered> lazyInitRendered = Suppliers.memoize(this::render);
	
	
	// INIT
	@API
	public static final class Builder
	{
		
		// ESSENTIAL
		private final int width;
		private final int height;
		private final String text;
		
		// FONT
		private Font font = null;
		private Integer maxFontSize = -1; // null is already used for 'as big as possible', so use -1 to indicate 'not yet set'
		private Color fontColor = null;
		
		// POSITIONING
		private Padding padding = Padding.none();
		private HorizontalAlignment horizontalAlignment = null;
		private VerticalAlignment verticalAlignment = null;
		
		
		// INIT
		@API
		public Builder(int width, int height, String text)
		{
			ValidationUtil.greaterZero(width, "width");
			ValidationUtil.greaterZero(height, "height");
			ValidationUtil.notBlank(text, "text");
			
			this.width = width;
			this.height = height;
			this.text = text;
		}
		
		
		// SETTERS
		@API
		public Builder font(Font font)
		{
			ValidationUtil.notNull(font, "font");
			this.font = font;
			return this;
		}
		
		@API
		public Builder systemDefaultFont()
		{
			return font(Font.defaultFont());
		}
		
		@API
		public Builder fontSize(int maxFontSize)
		{
			ValidationUtil.greaterZero(maxFontSize, "maxFontSize");
			this.maxFontSize = maxFontSize;
			
			return this;
		}
		
		@API
		public Builder fontAsBigAsPossible()
		{
			maxFontSize = null;
			return this;
		}
		
		@API
		public Builder fontColor(Color fontColor)
		{
			ValidationUtil.notNull(fontColor, "fontColor");
			this.fontColor = fontColor;
			return this;
		}
		
		@API
		public Builder padding(Padding padding)
		{
			ValidationUtil.notNull(padding, "padding");
			Validate.isTrue(width - padding.getHorizontalSum() > 0,
				"padding " + padding + " leaves no horizontal space for the text (snaporta width: " + width + ")");
			Validate.isTrue(height - padding.getVerticalSum() > 0,
				"padding " + padding + " leaves no vertical space for the text (snaporta height: " + height + ")");
			
			this.padding = padding;
			return this;
		}
		
		@API
		public Builder horizontalAlignment(HorizontalAlignment horizontalAlignment)
		{
			ValidationUtil.notNull(horizontalAlignment, "horizontalAlignment");
			this.horizontalAlignment = horizontalAlignment;
			return this;
		}
		
		@API
		public Builder verticalAlignment(VerticalAlignment verticalAlignment)
		{
			ValidationUtil.notNull(verticalAlignment, "verticalAlignment");
			this.verticalAlignment = verticalAlignment;
			return this;
		}
		
		@API
		public Builder alignTopLeft()
		{
			verticalAlignment = VerticalAlignment.TOP;
			horizontalAlignment = HorizontalAlignment.LEFT;
			return this;
		}
		
		@API
		public Builder alignTopCenter()
		{
			verticalAlignment = VerticalAlignment.TOP;
			horizontalAlignment = HorizontalAlignment.CENTER;
			return this;
		}
		
		@API
		public Builder alignTopRight()
		{
			verticalAlignment = VerticalAlignment.TOP;
			horizontalAlignment = HorizontalAlignment.RIGHT;
			return this;
		}
		
		@API
		public Builder alignCenterRight()
		{
			verticalAlignment = VerticalAlignment.CENTER;
			horizontalAlignment = HorizontalAlignment.RIGHT;
			return this;
		}
		
		@API
		public Builder alignBottomRight()
		{
			verticalAlignment = VerticalAlignment.BOTTOM;
			horizontalAlignment = HorizontalAlignment.RIGHT;
			return this;
		}
		
		@API
		public Builder alignBottomCenter()
		{
			verticalAlignment = VerticalAlignment.BOTTOM;
			horizontalAlignment = HorizontalAlignment.CENTER;
			return this;
		}
		
		@API
		public Builder alignBottomLeft()
		{
			verticalAlignment = VerticalAlignment.BOTTOM;
			horizontalAlignment = HorizontalAlignment.LEFT;
			return this;
		}
		
		@API
		public Builder alignCenterLeft()
		{
			verticalAlignment = VerticalAlignment.CENTER;
			horizontalAlignment = HorizontalAlignment.LEFT;
			return this;
		}
		
		@API
		public Builder alignCenterCenter()
		{
			verticalAlignment = VerticalAlignment.CENTER;
			horizontalAlignment = HorizontalAlignment.CENTER;
			return this;
		}
		
		
		// BUILD
		@API
		public TextSnaporta build()
		{
			// essential attributes are validated in constructor, don't need to do that here
			
			Validate.notNull(font, "You have to set a font before building");
			if(maxFontSize != null)
				Validate.isTrue(maxFontSize != -1, "You have to set the font size before building");
			Validate.notNull(fontColor, "You have to set the font color before building");
			Validate.notNull(horizontalAlignment, "You have to set the horizontal alignment before building");
			Validate.notNull(verticalAlignment, "You have to set the vertical alignment before building");
			
			return new TextSnaporta(width, height, text,
				font, maxFontSize, fontColor,
				padding, horizontalAlignment, verticalAlignment);
		}
		
	}
	
	
	@Override
	public String toString()
	{
		return PHR.r("{}(w={} x h={} \"{}\")", getClass().getSimpleName(),
			width, height, text);
	}
	
	
	// SNAPORTA
	@Override
	public int getArgbAt(int x, int y)
	{
		return lazyInitRendered.get().getImage().getArgbAt(x, y);
	}
	
	
	// GETTERS
	@API
	public Rendered getRendered()
	{
		return lazyInitRendered.get();
	}
	
	@API
	public Snaporta getCroppedToVisible(Padding padding)
	{
		var visibleBounds = getRendered().getVisibleBounds();
		
		int cropLeft = visibleBounds.getMinX() - padding.getLeft();
		int cropRight = getWidth() - visibleBounds.getMaxX() - padding.getRight();
		int cropTop = visibleBounds.getMinY() - padding.getTop();
		int cropBottom = height - visibleBounds.getMaxY() - padding.getBottom();
		
		return ViewportSnaporta.crop(this, cropLeft, cropRight, cropTop, cropBottom);
	}
	
	@API
	public Snaporta getCroppedToVisible()
	{
		return getCroppedToVisible(Padding.none());
	}
	
	
	// RENDER
	private Rendered render()
	{
		return new Rendering().render();
	}
	
	private class Rendering
	{
		
		private final BufferedImage bufferedImage;
		private final Graphics2D graphics;
		
		private Rectangle2D visualBounds;
		
		
		public Rendering()
		{
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			
			graphics = bufferedImage.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics.setColor(fontColor.toAwt());
		}
		
		public Rendered render()
		{
			int fontSize = determineFontSize();
			var sizedFont = font.getAwtFont().deriveFont((float) fontSize);
			graphics.setFont(sizedFont);
			
			var glyphVector = graphics.getFont().createGlyphVector(graphics.getFontRenderContext(), text);
			visualBounds = glyphVector.getVisualBounds();
			
			// render start point is at (0,0) of the visual bounds (x -> right, y -> down); the x axis is equal to the baseline
			double renderStartPointX = getRenderStartPointX(visualBounds, getTextMinX());
			double renderStartPointY = getRenderStartPointY(visualBounds, getTextMinY());
			graphics.drawGlyphVector(glyphVector, (float) renderStartPointX, (float) renderStartPointY);
			
			var snaporta = SnaportaBufferedImageConverter.convert(bufferedImage);
			graphics.dispose();
			
			var visibleBounds = buildVisibleBounds();
			return new Rendered(snaporta, visibleBounds);
		}
		
		private int determineFontSize()
		{
			final double testFontSize = 1000f;
			
			var testFont = font.getAwtFont().deriveFont((float) testFontSize);
			var testGlyphVector = testFont.createGlyphVector(graphics.getFontRenderContext(), text);
			
			var testGlyphVectorVisualBounds = testGlyphVector.getVisualBounds();
			double testTextWidth = testGlyphVectorVisualBounds.getWidth();
			double testTextHeight = testGlyphVectorVisualBounds.getHeight();
			
			double testWidthFactor = testTextWidth / getInsidePaddingWidth();
			double testHeightFactor = testTextHeight / getInsidePaddingHeight();
			
			double biggerFactor = Math.max(testWidthFactor, testHeightFactor);
			double fontSizeDecimal = testFontSize / biggerFactor;
			
			if(maxFontSize != null && maxFontSize < fontSizeDecimal)
				return maxFontSize;
			return (int) Math.floor(fontSizeDecimal);
		}
		
		private IntBounds2D buildVisibleBounds()
		{
			int visibleBoundsMinX = (int) Math.floor(getTextMinX());
			int visibleBoundsMinY = (int) Math.floor(getTextMinY());
			int visibleWidth = (int) Math.ceil(visualBounds.getWidth());
			int visibleHeight = (int) Math.ceil(visualBounds.getHeight());
			
			return IntBounds2D.fromPosAndSize(visibleBoundsMinX, visibleBoundsMinY, visibleWidth, visibleHeight);
		}
		
		
		// MEASUREMENTS
		private int getInsidePaddingHeight()
		{
			return height - padding.getVerticalSum();
		}
		
		private double getInsidePaddingWidth()
		{
			return width - padding.getHorizontalSum();
		}
		
		private double getTextMinX()
		{
			double insidePaddingUnoccupiedWidth = getInsidePaddingWidth() - visualBounds.getWidth();
			double unoccoupiedWidthOnLeft = getUnoccoupiedWidthOnLeft(insidePaddingUnoccupiedWidth);
			return padding.getLeft() + unoccoupiedWidthOnLeft;
		}
		
		private double getTextMinY()
		{
			double insidePaddingUnoccupiedHeight = getInsidePaddingHeight() - visualBounds.getHeight();
			double unoccoupiedHeightOnTop = getUnoccoupiedHeightOnTop(insidePaddingUnoccupiedHeight);
			return padding.getTop() + unoccoupiedHeightOnTop;
		}
		
		private double getUnoccoupiedWidthOnLeft(double unoccupiedWidth)
		{
			double proportion = horizontalAlignment.getProportionOfUnoccupiedWidthOnLeft();
			return proportion * unoccupiedWidth;
		}
		
		private double getUnoccoupiedHeightOnTop(double unoccupiedHeight)
		{
			double proportion = verticalAlignment.getProportionOfUnoccupiedHeightOnTop();
			return proportion * unoccupiedHeight;
		}
		
		private double getRenderStartPointX(Rectangle2D visualBounds, double textMinX)
		{
			double widthPastRenderStartPointOnLeft = -visualBounds.getMinX(); // overflow to left has negative x coordinate -> minus
			return textMinX + widthPastRenderStartPointOnLeft;
		}
		
		private double getRenderStartPointY(Rectangle2D visualBounds, double textMinY)
		{
			double topVisualBoundsEdgeToBaseline = -visualBounds.getMinY(); // top edge of visual bounds has negative y coordinate -> minus
			return textMinY + topVisualBoundsEdgeToBaseline;
		}
		
	}
	
	@API
	@RequiredArgsConstructor
	public static class Rendered
	{
		
		@Getter
		private final Snaporta image;
		@Getter
		private final IntBounds2D visibleBounds;
		
	}
	
	
	// OPTIONS
	@RequiredArgsConstructor
	public enum HorizontalAlignment
	{
		
		LEFT(0),
		CENTER(0.5),
		RIGHT(1);
		
		@Getter
		private final double proportionOfUnoccupiedWidthOnLeft;
		
	}
	
	@RequiredArgsConstructor
	public enum VerticalAlignment
	{
		
		TOP(0),
		CENTER(0.5),
		BOTTOM(1);
		
		@Getter
		private final double proportionOfUnoccupiedHeightOnTop;
		
	}
	
}
