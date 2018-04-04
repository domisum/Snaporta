package de.domisum.lib.snaporta.color;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaColors
{

	public static final SnaportaColor TRANSPARENT = SnaportaColor.fromARGB(0, 0, 0, 0);
	public static final SnaportaColor BLACK = SnaportaColor.fromBrightness(0);
	public static final SnaportaColor WHITE = SnaportaColor.fromBrightness(255);

	public static final SnaportaColor RED = SnaportaColor.fromRGB(255, 0, 0);
	public static final SnaportaColor GREEN = SnaportaColor.fromRGB(0, 255, 0);
	public static final SnaportaColor BLUE = SnaportaColor.fromRGB(0, 0, 255);

}
