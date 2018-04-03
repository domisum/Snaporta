package de.domisum.lib.snaporta.color;

import de.domisum.lib.auxilium.util.java.annotations.API;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.Color;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaColor
{

	// CONSTANTS
	@API public static final int ALPHA_TRANSPARENT = 0;
	@API public static final int ALPHA_OPAQUE = 0;

	// ATTRIBUTES
	@Getter private final int red;
	@Getter private final int green;
	@Getter private final int blue;
	@Getter private final int alpha;


	// INIT
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
