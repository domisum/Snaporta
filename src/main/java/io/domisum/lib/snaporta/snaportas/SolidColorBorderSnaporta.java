package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.SnaportaValidate;

@API
public class SolidColorBorderSnaporta
	implements Snaporta
{
	
	private final Snaporta containedImage;
	private final Padding borderPadding;
	private final Color borderColor;
	
	
	// INIT
	@API
	public SolidColorBorderSnaporta(Snaporta containedImage, Padding borderPadding, Color borderColor)
	{
		this.containedImage = containedImage;
		this.borderPadding = borderPadding;
		this.borderColor = borderColor;
		
		ValidationUtil.notNull(containedImage, "containedImage");
		ValidationUtil.notNull(borderPadding, "borderPadding");
		ValidationUtil.notNull(borderColor, "borderColor");
	}
	
	
	// SNAPORTA
	@Override
	public int getWidth()
	{
		return containedImage.getWidth()+borderPadding.getHorizontalSum();
	}
	
	@Override
	public int getHeight()
	{
		return containedImage.getHeight()+borderPadding.getVerticalSum();
	}
	
	@Override
	public int getArgbAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		int inContainedX = x-borderPadding.getLeft();
		int inContainedY = y-borderPadding.getTop();
		
		if(containedImage.isInBounds(inContainedX, inContainedY))
			return containedImage.getArgbAt(inContainedX, inContainedY);
		else
			return borderColor.toARGBInt();
	}
	
}
