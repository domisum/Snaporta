package de.domisum.lib.snaporta.snaportas.filter;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Padding;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.snaportas.BasicEditableSnaporta;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import org.apache.commons.lang3.Validate;

@API
public class ShadowSnaporta implements Snaporta
{

	// SETTINGS
	private final Snaporta baseSnaporta;
	private final Padding padding;
	private final Color color;
	private final int radius;

	// TEMP
	private final Snaporta shadow;


	// INIT
	public ShadowSnaporta(Snaporta baseSnaporta, Padding padding, Color color, int radius)
	{
		Validate.notNull(baseSnaporta);
		Validate.notNull(padding);
		Validate.notNull(color);
		Validate.isTrue(radius >= 0, "radius can't be negative, was "+radius);

		this.baseSnaporta = baseSnaporta;
		this.padding = padding;
		this.color = color;
		this.radius = radius;


		shadow = renderShadow();
	}

	private Snaporta renderShadow()
	{
		double[][] shadowIntensityField = new double[getHeight()][getWidth()];

		for(int y = 0; y < getHeight(); y++)
			for(int x = 0; x < getWidth(); x++)
			{
				Color color = baseSnaporta.getColorAt(x, y);

				for(int dY = -radius; dY <= radius; dY++)
					for(int dX = -radius; dX <= radius; dX++)
					{
						int cX = x+dX;
						int cY = y+dY;

						if(!isInBounds(cX, cY))
							continue;

						double distance = Math.sqrt((dX*dX)+(dY*dY));
						double shadowDistanceIntensity = 1-(distance/(radius*2));
						shadowDistanceIntensity *= shadowDistanceIntensity;
						//shadowDistanceIntensity = MathUtil.smootherStep(shadowDistanceIntensity);

						double shadowStrength = color.getOpacity()*shadowDistanceIntensity;

						if(shadowStrength > shadowIntensityField[cY][cX])
							shadowIntensityField[cY][cX] = shadowStrength;
					}
			}

		BasicEditableSnaporta shadowSnaporta = BasicEditableSnaporta.blankOfWidthAndHeight(getWidth(), getHeight());
		for(int y = 0; y < getHeight(); y++)
			for(int x = 0; x < getWidth(); x++)
			{
				double shadowIntensity = shadowIntensityField[y][x];
				Color color = this.color.deriveMultiplyOpacity(shadowIntensity);
				shadowSnaporta.setColorAt(x, y, color);
			}

		return shadowSnaporta;
	}


	// SNAPORTA
	@Override public int getWidth()
	{
		return baseSnaporta.getWidth()+padding.getHorizontalSum();
	}

	@Override public int getHeight()
	{
		return baseSnaporta.getHeight()+padding.getVerticalSum();
	}

	@Override public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);

		return shadow.getARGBAt(x, y);
	}

}
