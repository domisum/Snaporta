package de.domisum.lib.snaporta.snaportas.transform;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Colors;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

@API
public class OffsetSnaporta implements Snaporta
{

	private final Snaporta baseSnaporta;
	private final int offsetX;
	private final int offsetY;


	// INIT
	public OffsetSnaporta(Snaporta baseSnaporta, int offsetX, int offsetY)
	{
		this.baseSnaporta = baseSnaporta;
		this.offsetX = offsetX;
		this.offsetY = offsetY;

		Validate.notNull(baseSnaporta, "baseSnaporta can't be null");
		Validate.isTrue(getWidth() > 0, "width has to be greater than 0, was "+getWidth());
		Validate.isTrue(getHeight() > 0, "height has to be greater than 0, was "+getHeight());
	}


	// SNAPORTA
	@Override public int getWidth()
	{
		return baseSnaporta.getWidth()+offsetX;
	}

	@Override public int getHeight()
	{
		return baseSnaporta.getHeight()+offsetY;
	}

	@Override public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		int inBaseX = x-offsetX;
		int inBaseY = y-offsetY;

		if(!baseSnaporta.isInBounds(inBaseX, inBaseY))
			return Colors.TRANSPARENT.toARGBInt();

		return baseSnaporta.getARGBAt(inBaseX, inBaseY);
	}

}