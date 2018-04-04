package de.domisum.lib.snaporta.color;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.util.ARGBUtil;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;

public final class Color
{

	// CONSTANTS
	@API public static final int COLOR_COMPONENT_MAX = 255;

	@API public static final int ALPHA_TRANSPARENT = 0;
	@API public static final int ALPHA_OPAQUE = COLOR_COMPONENT_MAX;

	// ATTRIBUTES
	@Getter private final int alpha;
	@Getter private final int red;
	@Getter private final int green;
	@Getter private final int blue;


	// INIT
	private Color(int alpha, int red, int green, int blue)
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

	@API public static Color fromARGBInt(int argb)
	{
		return fromARGB(
				ARGBUtil.getAlphaComponent(argb),
				ARGBUtil.getRedComponent(argb),
				ARGBUtil.getBlueComponent(argb),
				ARGBUtil.getBlueComponent(argb)
		);
	}

	@API public static Color fromARGB(int alpha, int red, int green, int blue)
	{
		return new Color(alpha, red, green, blue);
	}

	@API public static Color fromRGB(int red, int green, int blue)
	{
		return new Color(ALPHA_OPAQUE, red, green, blue);
	}

	@API public static Color fromBrightness(int brightness)
	{
		return fromRGB(brightness, brightness, brightness);
	}

	@API public static Color fromAwt(java.awt.Color color)
	{
		return new Color(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
	}

	@API public static Color fullyTransparent()
	{
		return new Color(ALPHA_TRANSPARENT, 0, 0, 0);
	}


	// OBJECT
	@Override public String toString()
	{
		String redHex = getComponentAsHex(red);
		String greenHex = getComponentAsHex(green);
		String blueHex = getComponentAsHex(blue);
		String opacityPercent = Math.round((alpha/255d)*100)+"%";

		return "0x"+redHex+greenHex+blueHex+"_"+opacityPercent;
	}


	// CONVERSION
	public java.awt.Color toAwt()
	{
		return new java.awt.Color(red, green, blue, alpha);
	}

	public int toARGBInt()
	{
		return ARGBUtil.toARGB(alpha, red, green, blue);
	}


	// DERIVE
	@API public Color deriveOpposite()
	{
		int oppositeRed = COLOR_COMPONENT_MAX-red;
		int oppositeGreen = COLOR_COMPONENT_MAX-green;
		int oppositeBlue = COLOR_COMPONENT_MAX-blue;

		return new Color(alpha, oppositeRed, oppositeGreen, oppositeBlue);
	}


	// UTIL
	private String getComponentAsHex(int component)
	{
		StringBuilder hex = new StringBuilder(Integer.toHexString(component));
		while(hex.length() < 2)
			hex.insert(0, "0");

		return hex.toString().toUpperCase();
	}

}