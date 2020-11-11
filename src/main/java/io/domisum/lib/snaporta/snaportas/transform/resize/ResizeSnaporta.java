package io.domisum.lib.snaporta.snaportas.transform.resize;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.snaportas.transform.resize.interpolator.Interpolator;
import io.domisum.lib.snaporta.snaportas.transform.resize.interpolator.matrix.BiLinearInterpolator;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;

@API
public final class ResizeSnaporta
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
	@API
	public static ResizeSnaporta withFactor(Snaporta baseSnaporta, double factor)
	{
		int resizedWidth = (int) Math.round(baseSnaporta.getWidth()*factor);
		int resizedHeight = (int) Math.round(baseSnaporta.getHeight()*factor);
		
		return new ResizeSnaporta(baseSnaporta, resizedWidth, resizedHeight);
	}
	
	@API
	public ResizeSnaporta(Snaporta baseSnaporta, Integer width, Integer height)
	{
		this(baseSnaporta, width, height, DEFAULT_INTERPOLATOR);
	}
	
	@API
	public ResizeSnaporta(Snaporta baseSnaporta, Integer width, Integer height, Interpolator interpolator)
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
	public int getArgbAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		
		double relativeX = x/(double) width;
		double relativeY = y/(double) height;
		
		double inChildX = baseSnaporta.getWidth()*relativeX;
		double inChildY = baseSnaporta.getHeight()*relativeY;
		
		int argb = interpolator.interpolateARGBAt(baseSnaporta, inChildX, inChildY);
		return argb;
	}
	
}
