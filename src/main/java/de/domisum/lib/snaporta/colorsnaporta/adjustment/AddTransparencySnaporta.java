package de.domisum.lib.snaporta.colorsnaporta.adjustment;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.SnaportaColors;
import de.domisum.lib.snaporta.util.ARGBUtil;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class AddTransparencySnaporta implements Snaporta
{

	private final Snaporta parent;
	private final double opacity;


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
		if(opacity == 0)
			return SnaportaColors.TRANSPARENT.toARGBInt();

		int parentARGB = parent.getARGBAt(x, y);
		if(opacity == 1)
			return parentARGB;

		double parentOpacity = ARGBUtil.getOpacity(parentARGB);
		double newOpacity = parentOpacity*opacity;
		int newAlpha = ARGBUtil.getAlphaFromOpacity(newOpacity);

		return ARGBUtil.toARGB(
				newAlpha,
				ARGBUtil.getRedComponent(parentARGB),
				ARGBUtil.getGreenComponent(parentARGB),
				ARGBUtil.getBlueComponent(parentARGB)
		);
	}

}
