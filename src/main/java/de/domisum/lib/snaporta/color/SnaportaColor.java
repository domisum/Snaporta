package de.domisum.lib.snaporta.color;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;

import java.awt.Color;

public final class SnaportaColor
{

	// CONSTANTS
	@API public static final int ALPHA_TRANSPARENT = 0;
	@API public static final int ALPHA_OPAQUE = 255;

	// ATTRIBUTES
	@Getter private final int red;
	@Getter private final int green;
	@Getter private final int blue;
	@Getter private final int alpha;


	// INIT
	private SnaportaColor(int red, int green, int blue, int alpha)
	{
		SnaportaValidate.validateColorComponentInRange(red, "red");
		SnaportaValidate.validateColorComponentInRange(green, "green");
		SnaportaValidate.validateColorComponentInRange(blue, "blue");
		SnaportaValidate.validateColorComponentInRange(alpha, "alpha");

		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	@API public static SnaportaColor fromRGBA(int red, int green, int blue, int alpha)
	{
		return new SnaportaColor(red, green, blue, alpha);
	}

	@API public static SnaportaColor fromRGB(int red, int green, int blue)
	{
		return new SnaportaColor(red, green, blue, ALPHA_OPAQUE);
	}

	@API public static SnaportaColor fromAwt(Color color)
	{
		return new SnaportaColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	@API public static SnaportaColor fullyTransparent()
	{
		return new SnaportaColor(0, 0, 0, ALPHA_TRANSPARENT);
	}


	// CONVERSION
	public Color toAwt()
	{
		return new Color(red, green, blue, alpha);
	}

}
