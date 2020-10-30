package io.domisum.lib.snaporta.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Colors
{
	
	@API
	public static final Color TRANSPARENT = Color.fromARGB(0, 0, 0, 0);
	@API
	public static final Color HALF_TRANSPARENT_RED =  Color.fromARGB(Color.COLOR_COMPONENT_MAX/2, Color.COLOR_COMPONENT_MAX, 0, 0);
	
	@API
	public static final Color BLACK = Color.fromBrightnessAbs(0);
	@API
	public static final Color GRAY = Color.fromBrightnessAbs(Color.COLOR_COMPONENT_MAX/2);
	@API
	public static final Color WHITE = Color.fromBrightnessAbs(Color.COLOR_COMPONENT_MAX);
	
	@API
	public static final Color RED = Color.fromRGB(Color.COLOR_COMPONENT_MAX, 0, 0);
	@API
	public static final Color GREEN = Color.fromRGB(0, Color.COLOR_COMPONENT_MAX, 0);
	@API
	public static final Color BLUE = Color.fromRGB(0, 0, Color.COLOR_COMPONENT_MAX);
	
}
