package io.domisum.lib.snaporta.snaportas.filter.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.ARGBUtil;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class ColorizeSnaporta
		implements Snaporta
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
		
		int newAlpha = (int) Math.round(colorAt.getAlpha()*color.getOpacityRelative());
		int newRed = (int) Math.round(color.getRed()*brightness);
		int newGreen = (int) Math.round(color.getGreen()*brightness);
		int newBlue = (int) Math.round(color.getBlue()*brightness);
		
		return ARGBUtil.toARGB(newAlpha, newRed, newGreen, newBlue);
	}
	
}
