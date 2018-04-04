package de.domisum.lib.snaporta;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.util.ARGBUtil;

public interface EditableSnaporta extends Snaporta
{

	@API void setARGBAt(int x, int y, int argb);


	@API default void setColorAt(int x, int y, Color color)
	{
		setARGBAt(x, y, color.toARGBInt());
	}


	@API default void setAlphaAt(int x, int y, int alpha)
	{
		int oldArgb = getARGBAt(x, y);
		int newArgb = ARGBUtil.toARGB(
				alpha,
				ARGBUtil.getRedComponent(oldArgb),
				ARGBUtil.getGreenComponent(oldArgb),
				ARGBUtil.getBlueComponent(oldArgb)
		);

		setARGBAt(x, y, newArgb);
	}

	@API default void setRedAt(int x, int y, int red)
	{
		int oldArgb = getARGBAt(x, y);
		int newArgb = ARGBUtil.toARGB(
				ARGBUtil.getAlphaComponent(oldArgb),
				red,
				ARGBUtil.getGreenComponent(oldArgb),
				ARGBUtil.getBlueComponent(oldArgb)
		);

		setARGBAt(x, y, newArgb);
	}

	@API default void setGreenAt(int x, int y, int green)
	{
		int oldArgb = getARGBAt(x, y);
		int newArgb = ARGBUtil.toARGB(
				ARGBUtil.getAlphaComponent(oldArgb),
				ARGBUtil.getRedComponent(oldArgb),
				green,
				ARGBUtil.getBlueComponent(oldArgb)
		);

		setARGBAt(x, y, newArgb);
	}

	@API default void setBlueAt(int x, int y, int blue)
	{
		int oldArgb = getARGBAt(x, y);
		int newArgb = ARGBUtil.toARGB(
				ARGBUtil.getAlphaComponent(oldArgb),
				ARGBUtil.getRedComponent(oldArgb),
				ARGBUtil.getGreenComponent(oldArgb),
				blue
		);

		setARGBAt(x, y, newArgb);
	}

}
