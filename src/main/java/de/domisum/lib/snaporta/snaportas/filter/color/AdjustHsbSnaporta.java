package de.domisum.lib.snaporta.snaportas.filter.color;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.auxilium.util.math.MathUtil;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class AdjustHsbSnaporta implements Snaporta
{

	// INPUT
	private final Snaporta parent;

	// SETTING
	private final double deltaHue;
	private final double deltaSaturation;
	private final double deltaBrightness;


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


		double hue = parentColor.getHue()+deltaHue;
		hue = MathUtil.clamp(0, 1, hue);

		double saturation = parentColor.getSaturation()+deltaSaturation;
		saturation = MathUtil.clamp(0, 1, saturation);

		double brightness = parentColor.getBrightness()+deltaBrightness;
		brightness = MathUtil.clamp(0, 1, brightness);

		return Color.fromOHSB(parentColor.getOpacity(), hue, saturation, brightness).toARGBInt();
	}

}
