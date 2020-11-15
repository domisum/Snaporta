package io.domisum.lib.snaporta.snaportas.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.snaportas.ContainerSnaporta;
import io.domisum.lib.snaporta.snaportas.LayeredSnaporta;
import io.domisum.lib.snaporta.snaportas.transform.ViewportSnaporta;

@API
public class SolidColorBorderSnaporta
	extends ContainerSnaporta
{
	
	@API
	public SolidColorBorderSnaporta(Snaporta containedImage, Padding borderPadding, Color borderColor)
	{
		super(build(containedImage, borderPadding, borderColor));
	}
	
	private static Snaporta build(Snaporta containedImage, Padding borderPadding, Color borderColor)
	{
		var viewport = ViewportSnaporta.pad(containedImage, borderPadding);
		var background = new SolidColorSnaporta(viewport.getWidth(), viewport.getHeight(), borderColor);
		return new LayeredSnaporta(background, viewport);
	}
	
}
