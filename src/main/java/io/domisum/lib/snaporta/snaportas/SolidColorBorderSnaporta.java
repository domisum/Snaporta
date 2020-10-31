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
	private final Padding borderTicknesses;
	private final Color borderColor;
	
	
	// INIT
	@API
	public SolidColorBorderSnaporta(Snaporta containedImage, Padding borderTicknesses, Color borderColor)
	{
		this.containedImage = containedImage;
		this.borderTicknesses = borderTicknesses;
		this.borderColor = borderColor;
		
		ValidationUtil.notNull(containedImage, "containedImage");
		ValidationUtil.notNull(borderTicknesses, "borderTicknesses");
		ValidationUtil.notNull(borderColor, "borderColor");
	}
	
	
	// SNAPORTA
	@Override
	public int getWidth()
	{
		return containedImage.getWidth()+borderTicknesses.getHorizontalSum();
	}
	
	@Override
	public int getHeight()
	{
		return containedImage.getHeight()+borderTicknesses.getVerticalSum();
	}
	
	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		int inContainedX = x-borderTicknesses.getLeft();
		int inContainedY = y-borderTicknesses.getTop();
		
		if(containedImage.isInBounds(inContainedX, inContainedY))
			return containedImage.getARGBAt(inContainedX, inContainedY);
		else
			return borderColor.toARGBInt();
	}
	
}
