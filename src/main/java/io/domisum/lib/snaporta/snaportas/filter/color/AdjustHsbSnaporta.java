package io.domisum.lib.snaporta.snaportas.filter.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class AdjustHsbSnaporta
	implements Snaporta
{
	
	// INPUT
	private final Snaporta parent;
	
	// SETTING
	private final double deltaHue;
	private final double deltaSaturation;
	private final double deltaBrightness;
	
	
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
		return getColorAt(x, y).toARGBInt();
	}
	
	@Override
	public Color getColorAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		var parentColor = parent.getColorAt(x, y);
		
		double hue = parentColor.getHue()+deltaHue;
		hue = MathUtil.clamp(0, 1, hue);
		
		double saturation = parentColor.getSaturation()+deltaSaturation;
		saturation = MathUtil.clamp(0, 1, saturation);
		
		double brightness = parentColor.getBrightness()+deltaBrightness;
		brightness = MathUtil.clamp(0, 1, brightness);
		
		var adjustedColor = Color.fromOHSB(parentColor.getOpacityRelative(), hue, saturation, brightness);
		return adjustedColor;
	}
	
}
