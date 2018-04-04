package de.domisum.lib.snaporta.color;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Colors
{

	public static final Color TRANSPARENT = Color.fromARGB(0, 0, 0, 0);
	public static final Color BLACK = Color.fromBrightness(0);
	public static final Color WHITE = Color.fromBrightness(255);

	public static final Color RED = Color.fromRGB(255, 0, 0);
	public static final Color GREEN = Color.fromRGB(0, 255, 0);
	public static final Color BLUE = Color.fromRGB(0, 0, 255);

}
