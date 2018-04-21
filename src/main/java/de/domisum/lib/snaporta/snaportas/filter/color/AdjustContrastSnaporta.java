package de.domisum.lib.snaporta.snaportas.filter.color;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.auxilium.util.math.MathUtil;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.util.ARGBUtil;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class AdjustContrastSnaporta implements Snaporta
{

	// INPUT
	private final Snaporta parent;

	// SETTING
	private final double deltaContrast;


	// SNAPORTA
	@Override public int getWidth()
	{
		return parent.getWidth();
	}

	@Override public int getHeight()
	{
		return parent.getHeight();
	}

	@Override public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		Color parentColor = parent.getColorAt(x, y);

		int newRed = adjustContrastChannel(parentColor.getRed());
		int newGreen = adjustContrastChannel(parentColor.getGreen());
		int newBlue = adjustContrastChannel(parentColor.getBlue());

		return ARGBUtil.toARGB(parentColor.getAlpha(), newRed, newGreen, newBlue);
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
