package io.domisum.lib.snaporta.color;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.util.ArgbUtil;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@API
@EqualsAndHashCode
public final class Color
{
	
	// CONSTANTS
	@API
	public static final int COLOR_COMPONENT_MAX = 255;
	
	@API
	public static final int ALPHA_TRANSPARENT = 0;
	@API
	public static final int ALPHA_OPAQUE = COLOR_COMPONENT_MAX;
	@API
	public static final double OPACITY_TRANSPARENT = 0;
	@API
	public static final double OPACITY_OPAQUE = 1;
	
	// ATTRIBUTES
	@Getter
	private final int alpha;
	@Getter
	private final int red;
	@Getter
	private final int green;
	@Getter
	private final int blue;
	
	
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
	
	@API
	public static Color fromARGBInt(int argb)
	{
		return fromARGB(
			ArgbUtil.getAlphaComponent(argb),
			ArgbUtil.getRedComponent(argb),
			ArgbUtil.getGreenComponent(argb),
			ArgbUtil.getBlueComponent(argb));
	}
	
	@API
	public static Color fromARGB(int alpha, int red, int green, int blue)
	{
		return new Color(alpha, red, green, blue);
	}
	
	@API
	public static Color fromRGB(int red, int green, int blue)
	{
		return new Color(ALPHA_OPAQUE, red, green, blue);
	}
	
	@API
	public static Color fromOHSB(double opacity, double hue, double saturation, double brighntess)
	{
		var hsbColor = java.awt.Color.getHSBColor((float) hue, (float) saturation, (float) brighntess);
		var hsb = fromAwt(hsbColor);
		return hsb.deriveWithOpacity(opacity);
	}
	
	@API
	public static Color fromBrightnessAbs(int brightness)
	{
		return fromRGB(brightness, brightness, brightness);
	}
	
	@API
	public static Color fromBrightnessRel(double brightness)
	{
		SnaportaValidate.validateInDoubleInterval(0, 1, "brightness", brightness);
		return fromBrightnessAbs((int) Math.round(brightness*COLOR_COMPONENT_MAX));
	}
	
	@API
	public static Color fromAwt(java.awt.Color color)
	{
		return new Color(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
	}
	
	@API
	public static Color fullyTransparent()
	{
		return new Color(ALPHA_TRANSPARENT, 0, 0, 0);
	}
	
	
	@API
	public static Color fromRGBHex(String hex)
	{
		final String validCharacters = "0123456789ABCDEF";
		final int hexBase = 16;
		
		String hexProcessed = hex.toUpperCase();
		if(hexProcessed.startsWith("#"))
			hexProcessed = hexProcessed.substring(1);
		
		if(!StringUtils.containsOnly(hexProcessed, validCharacters))
			throw new IllegalArgumentException("Input contains invalid characters: '"+hex+"'");
		
		String redHexString;
		String greenHexString;
		String blueHexString;
		
		if(hexProcessed.length() == 6)
		{
			redHexString = hexProcessed.substring(0, 2);
			greenHexString = hexProcessed.substring(2, 4);
			blueHexString = hexProcessed.substring(4, 6);
		}
		else if(hexProcessed.length() == 3)
		{
			redHexString = hexProcessed.substring(0, 1).repeat(2);
			greenHexString = hexProcessed.substring(1, 2).repeat(2);
			blueHexString = hexProcessed.substring(2, 3).repeat(2);
		}
		else
			throw new IllegalArgumentException("Hex characters of input have to be either length 3 or length 6 but input was: '"+hex+"'");
		
		int red = Integer.parseInt(redHexString, hexBase);
		int green = Integer.parseInt(greenHexString, hexBase);
		int blue = Integer.parseInt(blueHexString, hexBase);
		
		return fromRGB(red, green, blue);
	}
	
	
	// OBJECT
	@Override
	public String toString()
	{
		return PHR.r("{} ({})", displayAsComponentInts(), displayAsRgbHexadecimal());
	}
	
	@API
	public String displayAsComponentInts()
	{
		String opacityPercent = Math.round((alpha/(double) COLOR_COMPONENT_MAX)*100)+"%";
		return PHR.r("{}r-{}g-{}b-{}", red, green, blue, opacityPercent);
	}
	
	@API
	public String displayAsRgbHexadecimal()
	{
		String redHex = getComponentAsHex(red);
		String greenHex = getComponentAsHex(green);
		String blueHex = getComponentAsHex(blue);
		
		return "#"+redHex+greenHex+blueHex;
	}
	
	
	// GETTERS
	@API
	public double getOpacity()
	{
		return alpha/(double) COLOR_COMPONENT_MAX;
	}
	
	@API
	public double getHue()
	{
		float[] hsb = java.awt.Color.RGBtoHSB(red, green, blue, null);
		return hsb[0];
	}
	
	@API
	public double getSaturation()
	{
		float[] hsb = java.awt.Color.RGBtoHSB(red, green, blue, null);
		return hsb[1];
	}
	
	@API
	public double getBrightness()
	{
		float[] hsb = java.awt.Color.RGBtoHSB(red, green, blue, null);
		return hsb[2];
	}
	
	@API
	public boolean isFullyTransparent()
	{
		return alpha == ALPHA_TRANSPARENT;
	}
	
	
	// CONVERSION
	public java.awt.Color toAwt()
	{
		return new java.awt.Color(red, green, blue, alpha);
	}
	
	public int toARGBInt()
	{
		return ArgbUtil.toArgb(alpha, red, green, blue);
	}
	
	
	// DERIVE
	@API
	public Color deriveWithAlpha(int deriveAlpha)
	{
		return fromARGB(deriveAlpha, getRed(), getGreen(), getBlue());
	}
	
	@API
	public Color deriveWithRed(int deriveRed)
	{
		return fromARGB(getAlpha(), deriveRed, getGreen(), getBlue());
	}
	
	@API
	public Color deriveWithGreen(int deriveGreen)
	{
		return fromARGB(getAlpha(), getRed(), deriveGreen, getBlue());
	}
	
	@API
	public Color deriveWithBlue(int deriveBlue)
	{
		return fromARGB(getAlpha(), getRed(), getGreen(), deriveBlue);
	}
	
	
	@API
	public Color deriveWithOpacity(double opacity)
	{
		SnaportaValidate.validateInDoubleInterval(0, 1, "opacity", opacity);
		int newAlpha = (int) Math.round(opacity*COLOR_COMPONENT_MAX);
		return deriveWithAlpha(newAlpha);
	}
	
	@API
	public Color deriveMultiplyOpacity(double opacityFactor)
	{
		SnaportaValidate.validateInDoubleInterval(0, 1, "opacityFactor", opacityFactor);
		return deriveWithOpacity(getOpacity()*opacityFactor);
	}
	
	
	// UTIL
	private String getComponentAsHex(int component)
	{
		var hex = new StringBuilder(Integer.toHexString(component));
		while(hex.length() < 2)
			hex.insert(0, "0");
		
		return hex.toString().toUpperCase();
	}
	
}
