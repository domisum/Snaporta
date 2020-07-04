package io.domisum.lib.snaporta.snaportas.transform.resize;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.snaportas.transform.resize.interpolator.Interpolator;
import io.domisum.lib.snaporta.snaportas.transform.resize.interpolator.matrix.BiLinearInterpolator;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;

@API
public final class ResizedSnaporta
		implements Snaporta
{
	
	// DEFAULTS
	private static final Interpolator DEFAULT_INTERPOLATOR = new BiLinearInterpolator();
	
	// ATTRIBUTES
	@Getter
	private final int width;
	@Getter
	private final int height;
	
	// REFERENCES
	private final Snaporta baseSnaporta;
	private final Interpolator interpolator;
	
	
	// INIT
	public ResizedSnaporta(Integer width, Integer height, Snaporta baseSnaporta)
	{
		this(width, height, baseSnaporta, DEFAULT_INTERPOLATOR);
	}
	
	public ResizedSnaporta(Integer width, Integer height, Snaporta baseSnaporta, Interpolator interpolator)
	{
		if((width == null) && (height == null))
			throw new IllegalArgumentException("width and height can't be null at the same time");
		
		if(width == null)
		{
			double factor = height/(double) baseSnaporta.getHeight();
			width = (int) Math.round(baseSnaporta.getWidth()*factor);
		}
		
		if(height == null)
		{
			double factor = width/(double) baseSnaporta.getWidth();
			height = (int) Math.round(baseSnaporta.getHeight()*factor);
		}
		
		this.width = width;
		this.height = height;
		this.baseSnaporta = baseSnaporta;
		
		this.interpolator = interpolator;
	}
	
	
	// SNAPORTA
	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		double relativeX = x/(double) width;
		double relativeY = y/(double) height;
		
		double inChildX = baseSnaporta.getWidth()*relativeX;
		double inChildY = baseSnaporta.getHeight()*relativeY;
		return interpolator.interpolateARGBAt(baseSnaporta, inChildX, inChildY);
	}
	
}
