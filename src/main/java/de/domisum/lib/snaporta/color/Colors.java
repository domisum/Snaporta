package de.domisum.lib.snaporta.color;

import de.domisum.lib.auxilium.util.java.annotations.API;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Colors
{

	@API public static final Color TRANSPARENT = Color.fromARGB(0, 0, 0, 0);
	@API public static final Color BLACK = Color.fromBrightnessAbs(0);
	@API public static final Color GRAY = Color.fromBrightnessAbs(255/2);
	@API public static final Color WHITE = Color.fromBrightnessAbs(255);

	@API public static final Color RED = Color.fromRGB(255, 0, 0);
	@API public static final Color GREEN = Color.fromRGB(0, 255, 0);
	@API public static final Color BLUE = Color.fromRGB(0, 0, 255);

}
