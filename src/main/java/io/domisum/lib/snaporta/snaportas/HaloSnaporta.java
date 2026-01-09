package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.mask.doubl.DoubleMask;
import io.domisum.lib.snaporta.mask.doubl.DoubleMaskPainter;
import io.domisum.lib.snaporta.snaportas.color.SolidColorSnaporta;
import io.domisum.lib.snaporta.snaportas.mask.DoubleMaskOpacitySnaporta;
import io.domisum.lib.snaporta.snaportas.transform.ViewportSnaporta;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class HaloSnaporta
	extends GeneratedSnaporta
{
	
	private final Snaporta base;
	private final int radius;
	private final Color color;
	
	
	// OBJECT
	@Override
	public String toString()
	{
		return PHR.r("{}(radius={}, color={}\n{})", getClass().getSimpleName(),
			radius, color, StringUtil.indent(base.toString(), "\t"));
	}
	
	
	// INIT
	@Override
	protected Snaporta generate()
	{
		var padded = ViewportSnaporta.pad(base, Padding.toEverySide(radius));
		
		var haloIntensityMask = getHaloIntensityMask(padded);
		var haloColored = new SolidColorSnaporta(padded.getWidth(), padded.getHeight(), color);
		var halo = new DoubleMaskOpacitySnaporta(haloColored, haloIntensityMask);
		
		return new LayeredSnaporta(halo, padded);
	}
	
	private DoubleMask getHaloIntensityMask(Snaporta padded)
	{
		var intensityMaskPainter = DoubleMaskPainter.sized(padded.getWidth(), padded.getHeight());
		
		for(int pY = radius; pY < padded.getHeight() - radius; pY++)
			for(int pX = radius; pX < padded.getWidth() - radius; pX++)
				if(shouldSourcePixelBeVisited(padded, pX, pY))
					visitSourcePixel(padded, intensityMaskPainter, pX, pY);
		
		return intensityMaskPainter.toMask();
	}
	
	private boolean shouldSourcePixelBeVisited(Snaporta snaporta, int pX, int pY)
	{
		if(snaporta.getColorAt(pX, pY).isFullyTransparent())
			return false;
		
		if(isSurroundedByFullOpacity(snaporta, pX, pY))
			return false;
		
		return true;
	}
	
	private boolean isSurroundedByFullOpacity(Snaporta snaporta, int pX, int pY)
	{
		for(var neighborPixel : NeighborPixel.values())
			if(snaporta.getAlphaAt(pX + neighborPixel.dX, pY + neighborPixel.dY) != Color.ALPHA_OPAQUE)
				return false;
		
		return true;
	}
	
	private void visitSourcePixel(Snaporta snaporta, DoubleMaskPainter intensityMaskPainter, int pX, int pY)
	{
		double sourceOpacity = snaporta.getColorAt(pX, pY).getOpacity();
		
		for(int dY = -radius; dY <= radius; dY++)
			for(int dX = -radius; dX <= radius; dX++)
			{
				int x = pX + dX;
				int y = pY + dY;
				double targetOpacity = snaporta.getColorAt(x, y).getOpacity();
				if(targetOpacity == 1)
					continue;
				
				int distanceSquared = dX * dX + dY * dY;
				if(distanceSquared > radius * radius)
					continue;
				double distance = Math.sqrt(distanceSquared);
				
				double distanceIntensityFactor = (radius - distance + 1) / radius;
				double intensity = sourceOpacity * distanceIntensityFactor * (1 - targetOpacity);
				if(intensity > intensityMaskPainter.getValueAt(x, y))
					intensityMaskPainter.setValueAt(x, y, intensity);
			}
	}
	
	
	// UTIL
	@RequiredArgsConstructor
	private enum NeighborPixel
	{
		
		RIGHT(1, 0),
		BOTTOM(0, 1),
		LEFT(-1, 0),
		TOP(0, -1);
		
		private final int dX;
		private final int dY;
		
	}
	
}
