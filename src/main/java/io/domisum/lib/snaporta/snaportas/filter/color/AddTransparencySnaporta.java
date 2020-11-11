package io.domisum.lib.snaporta.snaportas.filter.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.ArgbUtil;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class AddTransparencySnaporta
	implements Snaporta
{
	
	private final Snaporta parent;
	private final double opacityRel;
	
	
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
		
		if(opacityRel == 0)
			return Colors.TRANSPARENT.toARGBInt();
		
		int parentARGB = parent.getArgbAt(x, y);
		if(opacityRel == 1)
			return parentARGB;
		
		double parentOpacity = ArgbUtil.getOpacity(parentARGB);
		double newOpacity = parentOpacity*opacityRel;
		int newAlpha = ArgbUtil.getAlphaFromOpacity(newOpacity);
		
		int argb = ArgbUtil.toArgb(
			newAlpha,
			ArgbUtil.getRedComponent(parentARGB),
			ArgbUtil.getGreenComponent(parentARGB),
			ArgbUtil.getBlueComponent(parentARGB));
		
		return argb;
	}
	
}
