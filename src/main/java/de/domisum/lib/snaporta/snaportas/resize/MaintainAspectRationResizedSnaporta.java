package de.domisum.lib.snaporta.snaportas.resize;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Colors;
import de.domisum.lib.snaporta.snaportas.resize.interpolator.Interpolator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public final class MaintainAspectRationResizedSnaporta implements Snaporta
{

	// ATTRIBUTES
	@Getter private final int width;
	@Getter private final int height;

	// REFERENCES
	private final Snaporta baseSnaporta;
	private final Interpolator interpolator;

	// SETTINGS
	private final Padding padding;

	// RESIZED
	private ResizedSnaporta resizedSnaporta;
	private int resizedSnaportaX = -1;
	private int resizedSnaportaY = -1;


	// SNAPORTA
	@Override public int getARGBAt(int x, int y)
	{
		if(resizedSnaporta == null)
			buildResizedSnaporta();

		int inChildX = x-resizedSnaportaX;
		int inChildY = y-resizedSnaportaY;

		if(!resizedSnaporta.isInBounds(inChildX, inChildY))
			return Colors.TRANSPARENT.toARGBInt();

		return resizedSnaporta.getARGBAt(inChildX, inChildY);
	}


	private void buildResizedSnaporta()
	{
		double aspectRatio = baseSnaporta.getWidth()/(double) baseSnaporta.getHeight();

		int availableWidth = width-padding.getHorizontalSum();
		int availableHeight = height-padding.getVerticalSum();


		double resizedWidth;
		double resizedHeight;

		double widthDerivedHeight = availableWidth/aspectRatio;
		if(widthDerivedHeight > availableHeight)
		{
			resizedWidth = availableHeight*aspectRatio;
			resizedHeight = availableHeight;
		}
		else
		{
			resizedWidth = availableWidth;
			resizedHeight = availableWidth/aspectRatio;
		}

		resizedSnaporta = new ResizedSnaporta((int) Math.round(resizedWidth),
				(int) Math.round(resizedHeight),
				baseSnaporta,
				interpolator
		);

		int resizedSnaportaFromPaddingOffsetX = (width-padding.getHorizontalSum()-resizedSnaporta.getWidth())/2;
		int resizedSnaportaFromPaddingOffsetY = (height-padding.getVerticalSum()-resizedSnaporta.getHeight())/2;
		resizedSnaportaX = resizedSnaportaFromPaddingOffsetX+padding.getLeft();
		this.resizedSnaportaY = resizedSnaportaFromPaddingOffsetY+padding.getTop();
	}

}
