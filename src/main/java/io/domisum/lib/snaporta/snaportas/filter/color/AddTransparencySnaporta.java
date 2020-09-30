package io.domisum.lib.snaporta.snaportas.filter.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.ARGBUtil;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class AddTransparencySnaporta
	implements Snaporta
{
	
	private final Snaporta parent;
	private final double opacity;
	
	
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
		
		if(opacity == 0)
			return Colors.TRANSPARENT.toARGBInt();
		
		int parentARGB = parent.getARGBAt(x, y);
		if(opacity == 1)
			return parentARGB;
		
		double parentOpacity = ARGBUtil.getOpacity(parentARGB);
		double newOpacity = parentOpacity*opacity;
		int newAlpha = ARGBUtil.getAlphaFromOpacity(newOpacity);
		
		int argb = ARGBUtil.toARGB(
			newAlpha,
			ARGBUtil.getRedComponent(parentARGB),
			ARGBUtil.getGreenComponent(parentARGB),
			ARGBUtil.getBlueComponent(parentARGB));
		
		return argb;
	}
	
}
