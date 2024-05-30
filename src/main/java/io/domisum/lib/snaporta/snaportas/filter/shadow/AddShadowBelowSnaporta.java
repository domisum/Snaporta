package io.domisum.lib.snaporta.snaportas.filter.shadow;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.snaportas.LayeredSnaporta;

@API
public class AddShadowBelowSnaporta
	implements Snaporta
{
	
	// RENDERED
	private final Snaporta baseSnaporta;
	private final Snaporta rendered;
	
	
	// HOUSEKEEPING
	@API
	public AddShadowBelowSnaporta(Snaporta baseSnaporta, Color shadowColor, int shadowOffsetX, int shadowOffsetY)
	{
		this.baseSnaporta = baseSnaporta;
		var shadow = new ShadowSnaporta(baseSnaporta, shadowColor, shadowOffsetX, shadowOffsetY);
		rendered = new LayeredSnaporta(shadow, baseSnaporta);
	}
	
	@Override
	public String toString()
	{
		return PHR.r("{}(\n{})", getClass().getSimpleName(), StringUtil.indent(baseSnaporta.toString(), "\t"));
	}
	
	
	// SNAPORTA
	@Override
	public synchronized int getArgbAt(int x, int y)
	{
		return rendered.getArgbAt(x, y);
	}
	
	@Override
	public synchronized int getWidth()
	{
		return rendered.getWidth();
	}
	
	@Override
	public synchronized int getHeight()
	{
		return rendered.getHeight();
	}
	
}
