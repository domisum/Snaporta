package io.domisum.lib.snaporta.snaportas.filter;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

@API
public class ShadowSnaporta implements Snaporta
{

	// SETTINGS
	private final Snaporta baseSnaporta;
	private final Color color;
	private final int offsetX;
	private final int offsetY;


	// INIT
	public ShadowSnaporta(Snaporta baseSnaporta, Color color, int offsetX, int offsetY)
	{
		Validate.notNull(baseSnaporta);
		Validate.notNull(color);

		this.baseSnaporta = baseSnaporta;
		this.color = color;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}


	// SNAPORTA
	@Override
	public int getWidth()
	{
		return baseSnaporta.getWidth();
	}

	@Override
	public int getHeight()
	{
		return baseSnaporta.getHeight();
	}

	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		int inBaseX = x-offsetX;
		int inBaseY = y-offsetY;
		if(!baseSnaporta.isInBounds(inBaseX, inBaseY))
			return Colors.TRANSPARENT.toARGBInt();

		Color colorAt = baseSnaporta.getColorAt(inBaseX, inBaseY);
		double opacity = colorAt.getOpacityRelative();
		if(opacity == 0)
			return Colors.TRANSPARENT.toARGBInt();

		return color.deriveWithOpacity(opacity).toARGBInt();
	}

}
