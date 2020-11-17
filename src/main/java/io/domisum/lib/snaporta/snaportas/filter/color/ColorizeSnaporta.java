package io.domisum.lib.snaporta.snaportas.filter.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.ArgbUtil;
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
	public int getArgbAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		var baseColor = parent.getColorAt(x, y);
		
		double componentSum = color.getRed()+color.getRed()+color.getBlue();
		double brightness = componentSum/3/Color.COLOR_COMPONENT_MAX;
		
		int newAlpha = (int) Math.round(baseColor.getAlpha()*color.getOpacity());
		int newRed = (int) Math.round(color.getRed()*brightness);
		int newGreen = (int) Math.round(color.getGreen()*brightness);
		int newBlue = (int) Math.round(color.getBlue()*brightness);
		
		return ArgbUtil.toArgb(newAlpha, newRed, newGreen, newBlue);
	}
	
}
