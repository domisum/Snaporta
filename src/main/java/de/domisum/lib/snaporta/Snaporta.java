package de.domisum.lib.snaporta;

import de.domisum.lib.snaporta.util.ARGBUtil;

public interface Snaporta
{

	int getWidth();

	int getHeight();


	default int getARGBAt(int x, int y)
	{
		int alpha = getAlphaAt(x, y);
		int red = getRedAt(x, y);
		int green = getGreenAt(x, y);
		int blue = getBlueAt(x, y);

		return ARGBUtil.toARGB(alpha, red, green, blue);
	}

	default int getAlphaAt(int x, int y)
	{
		return ARGBUtil.getAlphaComponent(getARGBAt(x, y));
	}

	default int getRedAt(int x, int y)
	{
		return ARGBUtil.getRedComponent(getARGBAt(x, y));
	}

	default int getGreenAt(int x, int y)
	{
		return ARGBUtil.getGreenComponent(getARGBAt(x, y));
	}

	default int getBlueAt(int x, int y)
	{
		return ARGBUtil.getBlueComponent(getARGBAt(x, y));
	}

}
