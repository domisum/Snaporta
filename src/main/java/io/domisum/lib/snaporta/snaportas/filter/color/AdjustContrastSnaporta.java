package io.domisum.lib.snaporta.snaportas.filter.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.ArgbUtil;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class AdjustContrastSnaporta
	implements Snaporta
{
	
	// INPUT
	private final Snaporta parent;
	
	// SETTING
	private final double deltaContrast;
	
	
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
		
		var parentColor = parent.getColorAt(x, y);
		
		int newRed = adjustContrastChannel(parentColor.getRed());
		int newGreen = adjustContrastChannel(parentColor.getGreen());
		int newBlue = adjustContrastChannel(parentColor.getBlue());
		
		int argb = ArgbUtil.toArgb(parentColor.getAlpha(), newRed, newGreen, newBlue);
		return argb;
	}
	
	private int adjustContrastChannel(int value)
	{
		double channelRelative = value/(double) Color.COLOR_COMPONENT_MAX;
		double newChannelRelative = channelRelative*(1+deltaContrast);
		double newChannelRelativeClamped = MathUtil.clamp(0, 1, newChannelRelative);
		
		int newChannel = (int) Math.round(newChannelRelativeClamped*Color.COLOR_COMPONENT_MAX);
		return newChannel;
	}
	
}
