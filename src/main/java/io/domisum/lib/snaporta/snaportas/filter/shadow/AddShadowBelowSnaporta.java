package io.domisum.lib.snaporta.snaportas.filter.shadow;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.snaportas.LayeredSnaporta;

@API
public class AddShadowBelowSnaporta
	extends LayeredSnaporta
{
	
	@API
	public AddShadowBelowSnaporta(Snaporta base, Color shadowColor, int shadowOffsetX, int shadowOffsetY)
	{
		super(new ShadowSnaporta(base, shadowColor, shadowOffsetX, shadowOffsetY), base);
	}
	
}
