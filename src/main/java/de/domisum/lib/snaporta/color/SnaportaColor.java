package de.domisum.lib.snaporta.color;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.util.ARGBUtil;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;

import java.awt.Color;

public final class SnaportaColor
{

	// CONSTANTS
	@API public static final int ALPHA_TRANSPARENT = 0;
	@API public static final int ALPHA_OPAQUE = 255;

	// ATTRIBUTES
	@Getter private final int alpha;
	@Getter private final int red;
	@Getter private final int green;
	@Getter private final int blue;


	// INIT
	private SnaportaColor(int alpha, int red, int green, int blue)
	{
		SnaportaValidate.validateColorComponentInRange(alpha, "alpha");
		SnaportaValidate.validateColorComponentInRange(red, "red");
		SnaportaValidate.validateColorComponentInRange(green, "green");
		SnaportaValidate.validateColorComponentInRange(blue, "blue");

		this.alpha = alpha;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	@API public static SnaportaColor fromARGBInt(int argb)
	{
		return fromARGB(
				ARGBUtil.getAlphaComponent(argb),
				ARGBUtil.getRedComponent(argb),
				ARGBUtil.getBlueComponent(argb),
				ARGBUtil.getBlueComponent(argb)
		);
	}

	@API public static SnaportaColor fromARGB(int alpha, int red, int green, int blue)
	{
		return new SnaportaColor(alpha, red, green, blue);
	}

	@API public static SnaportaColor fromRGB(int red, int green, int blue)
	{
		return new SnaportaColor(ALPHA_OPAQUE, red, green, blue);
	}

	@API public static SnaportaColor fromAwt(Color color)
	{
		return new SnaportaColor(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
	}

	@API public static SnaportaColor fullyTransparent()
	{
		return new SnaportaColor(ALPHA_TRANSPARENT, 0, 0, 0);
	}


	// CONVERSION
	public Color toAwt()
	{
		return new Color(red, green, blue, alpha);
	}

}
