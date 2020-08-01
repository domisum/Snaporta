package io.domisum.lib.snaporta.snaportas.filter.shadow;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.snaportas.LayeredSnaporta;

@API
public class AddShadowBelowSnaporta
		implements Snaporta
{
	
	// RENDERED
	private final Snaporta rendered;
	
	
	// INIT
	@API
	public AddShadowBelowSnaporta(Snaporta baseSnaporta, Color shadowColor, int shadowOffsetX, int shadowOffsetY)
	{
		var shadow = new ShadowSnaporta(baseSnaporta, shadowColor, shadowOffsetX, shadowOffsetY);
		rendered = new LayeredSnaporta(shadow, baseSnaporta);
	}
	
	
	// SNAPORTA
	@Override
	public synchronized int getARGBAt(int x, int y)
	{
		return rendered.getARGBAt(x, y);
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
