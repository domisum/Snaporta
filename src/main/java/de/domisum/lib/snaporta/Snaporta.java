package de.domisum.lib.snaporta;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.util.ARGBUtil;
import de.domisum.lib.snaporta.util.Sized;

public interface Snaporta extends Sized
{

	@API int getARGBAt(int x, int y);

	@API default Color getColorAt(int x, int y)
	{
		int argb = getARGBAt(x, y);
		return Color.fromARGBInt(argb);
	}


	@API default int getAlphaAt(int x, int y)
	{
		return ARGBUtil.getAlphaComponent(getARGBAt(x, y));
	}

	@API default int getRedAt(int x, int y)
	{
		return ARGBUtil.getRedComponent(getARGBAt(x, y));
	}

	@API default int getGreenAt(int x, int y)
	{
		return ARGBUtil.getGreenComponent(getARGBAt(x, y));
	}

	@API default int getBlueAt(int x, int y)
	{
		return ARGBUtil.getBlueComponent(getARGBAt(x, y));
	}

}
