package de.domisum.lib.snaporta;

import de.domisum.lib.snaporta.util.ARGBUtil;

public interface Snaporta
{

	int getWidth();

	int getHeight();


	int getARGBAt(int x, int y);


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
