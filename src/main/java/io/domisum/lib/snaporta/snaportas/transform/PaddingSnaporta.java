package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

@API
public class PaddingSnaporta implements Snaporta
{

	private final Snaporta baseSnaporta;
	private final Padding padding;


	// INIT
	public PaddingSnaporta(Snaporta baseSnaporta, Padding padding)
	{
		this.baseSnaporta = baseSnaporta;
		this.padding = padding;

		Validate.notNull(baseSnaporta, "baseSnaporta can't be null");
		Validate.notNull(padding, "padding can't be null");
	}


	// SNAPORTA
	@Override
	public int getWidth()
	{
		return baseSnaporta.getWidth()+padding.getHorizontalSum();
	}

	@Override
	public int getHeight()
	{
		return baseSnaporta.getHeight()+padding.getVerticalSum();
	}

	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		int inBaseX = x-padding.getLeft();
		int inBaseY = y-padding.getTop();

		if(!baseSnaporta.isInBounds(inBaseX, inBaseY))
			return Colors.TRANSPARENT.toARGBInt();

		return baseSnaporta.getARGBAt(inBaseX, inBaseY);
	}

}
