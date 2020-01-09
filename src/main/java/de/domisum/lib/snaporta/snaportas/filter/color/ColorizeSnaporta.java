package de.domisum.lib.snaporta.snaportas.filter.color;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.util.ARGBUtil;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class ColorizeSnaporta implements Snaporta
{

	private final Snaporta parent;
	private final Color color;


	// SNAPORTA
	@Override
	public int getWidth()
	{
		return parent.getWidth();
	}

	@Override
	public int getHeight()
	{
		return parent.getHeight();
	}

	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		Color colorAt = parent.getColorAt(x, y);
		double brightness = colorAt.getRGBBrightnessRelative();

		int newAlpha = (int) Math.round(colorAt.getAlpha()*color.getOpacity());
		int newRed = (int) Math.round(color.getRed()*brightness);
		int newGreen = (int) Math.round(color.getGreen()*brightness);
		int newBlue = (int) Math.round(color.getBlue()*brightness);

		return ARGBUtil.toARGB(newAlpha, newRed, newGreen, newBlue);
	}

}
