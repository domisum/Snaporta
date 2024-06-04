package io.domisum.lib.snaporta.snaportas.filter.shadow;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.snaportas.LayeredSnaporta;
import io.domisum.lib.snaporta.util.ArgbUtil;

@API
public class AddShadowBelowSnaporta
	implements Snaporta
{
	
	// INPUT
	private final Snaporta base;
	private final Color shadowColor;
	private final int shadowOffsetX;
	private final int shadowOffsetY;
	
	// STATE
	private ShadowSnaporta shadow;
	
	
	// HOUSEKEEPING
	public AddShadowBelowSnaporta(Snaporta base, Color shadowColor, int shadowOffsetX, int shadowOffsetY)
	{
		this.base = base;
		this.shadowColor = shadowColor;
		this.shadowOffsetX = shadowOffsetX;
		this.shadowOffsetY = shadowOffsetY;
	}
	
	@Override
	public String toString()
	{
		return PHR.r("{}(c={}, sx={} x sy={}\n{})", getClass().getSimpleName(),
			shadowColor, shadowOffsetX, shadowOffsetY, StringUtil.indent(base, "\t"));
	}
	
	
	// SNAPORTA
	@Override
	public int getWidth()
	{
		return base.getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return base.getHeight();
	}
	
	@Override
	public int getArgbAt(int x, int y)
	{
		if(shadow == null)
			shadow = new ShadowSnaporta(base, shadowColor, shadowOffsetX, shadowOffsetY);
		
		int baseColor = base.getArgbAt(x, y);
		if(ArgbUtil.getAlphaComponent(baseColor) == Color.ALPHA_OPAQUE)
			return baseColor;
		
		int shadowColor = shadow.getArgbAt(x, y);
		return LayeredSnaporta.mixArgb(shadowColor, baseColor);
	}
	
	
	@Override
	public BlankState isBlank()
	{
		return base.isBlank();
	}
	
	@Override
	public Snaporta optimize()
	{
		var newBase = base.optimize();
		if(newBase == base)
			return this;
		
		return new AddShadowBelowSnaporta(newBase, shadowColor, shadowOffsetX, shadowOffsetY);
	}
	
}
