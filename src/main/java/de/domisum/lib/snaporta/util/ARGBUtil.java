package de.domisum.lib.snaporta.util;

import de.domisum.lib.snaporta.color.ColorComponent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ARGBUtil
{

	// CONSTANTS
	private static final int TWO_BYTE_MASK = 0xFF;


	// CONVERSION
	public static int toARGB(int alpha, int red, int green, int blue)
	{
		int alphaMasked = alpha&TWO_BYTE_MASK;
		int redMasked = red&TWO_BYTE_MASK;
		int greenMasked = green&TWO_BYTE_MASK;
		int blueMasked = blue&TWO_BYTE_MASK;

		int color = 0;
		color |= alphaMasked<<(8*3);
		color |= redMasked<<(8*2);
		color |= greenMasked<<8;
		color |= blueMasked;

		return color;
	}


	public static int getComponent(ColorComponent colorComponent, int argb)
	{
		switch(colorComponent)
		{
			case ALPHA:
				return getAlphaComponent(argb);
			case RED:
				return getRedComponent(argb);
			case GREEN:
				return getGreenComponent(argb);
			case BLUE:
				return getBlueComponent(argb);
			default:
				throw new IllegalArgumentException("invalid color component: "+colorComponent);
		}
	}


	public static int getAlphaComponent(int argb)
	{
		int shiftedAlpha = argb >> (8*3);
		return shiftedAlpha&TWO_BYTE_MASK;
	}

	public static double getOpacity(int argb)
	{
		return getAlphaComponent(argb)/255d;
	}

	public static int getAlphaFromOpacity(double opacity)
	{
		return (int) Math.round(opacity*255d);
	}


	public static int getRedComponent(int argb)
	{
		int shiftedRed = argb >> (8*2);
		return shiftedRed&TWO_BYTE_MASK;
	}

	public static int getGreenComponent(int argb)
	{
		int shiftedGreen = argb >> 8;
		return shiftedGreen&TWO_BYTE_MASK;
	}

	public static int getBlueComponent(int argb)
	{
		return argb&TWO_BYTE_MASK;
	}

}
