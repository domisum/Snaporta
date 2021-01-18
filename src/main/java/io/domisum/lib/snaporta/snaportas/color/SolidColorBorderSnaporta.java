package io.domisum.lib.snaporta.snaportas.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.snaportas.GeneratedSnaporta;
import io.domisum.lib.snaporta.snaportas.LayeredSnaporta;
import io.domisum.lib.snaporta.snaportas.transform.ViewportSnaporta;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class SolidColorBorderSnaporta
	extends GeneratedSnaporta
{
	
	private final Snaporta containedImage;
	private final Padding borderPadding;
	private final Color borderColor;
	
	
	// INIT
	@Override
	protected Snaporta generate()
	{
		var viewport = ViewportSnaporta.pad(containedImage, borderPadding);
		var background = new SolidColorSnaporta(viewport.getWidth(), viewport.getHeight(), borderColor);
		return new LayeredSnaporta(background, viewport);
	}
	
}
