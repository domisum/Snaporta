package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.mask.doubl.DoubleMask;
import io.domisum.lib.snaporta.mask.doubl.DoubleMaskPainter;
import io.domisum.lib.snaporta.snaportas.color.SolidColorSnaporta;
import io.domisum.lib.snaporta.snaportas.mask.DoubleMaskOpacitySnaporta;
import io.domisum.lib.snaporta.snaportas.transform.ViewportSnaporta;
import lombok.RequiredArgsConstructor;

public class HaloSnaporta
	extends ContainerSnaporta
{
	
	public HaloSnaporta(Snaporta base, int radius, Color color)
	{
		super(()->generate(base, radius, color));
	}
	
	private static Snaporta generate(Snaporta base, int radius, Color color)
	{
		var padded = ViewportSnaporta.pad(base, Padding.toEverySide(radius));
		
		var haloIntensityMask = getHaloIntensityMask(padded, radius);
		var haloColored = new SolidColorSnaporta(padded.getWidth(), padded.getHeight(), color);
		var halo = new DoubleMaskOpacitySnaporta(haloColored, haloIntensityMask);
		
		return new LayeredSnaporta(halo, padded);
	}
	
	private static DoubleMask getHaloIntensityMask(Snaporta base, int radius)
	{
		var intensityMaskPainter = DoubleMaskPainter.sized(base.getWidth(), base.getHeight());
		
		for(int pY = radius; pY < base.getHeight()-radius; pY++)
			for(int pX = radius; pX < base.getWidth()-radius; pX++)
				if(shouldSourcePixelBeVisited(base, pX, pY))
					visitSourcePixel(base, radius, intensityMaskPainter, pX, pY);
		
		return intensityMaskPainter.toMask();
	}
	
	private static boolean shouldSourcePixelBeVisited(Snaporta base, int pX, int pY)
	{
		if(base.getColorAt(pX, pY).isFullyTransparent())
			return false;
		
		if(isSurroundedByFullOpacity(base, pX, pY))
			return false;
		
		return true;
	}
	
	private static boolean isSurroundedByFullOpacity(Snaporta base, int pX, int pY)
	{
		for(var neighborPixel : NeighborPixel.values())
			if(base.getAlphaAt(pX+neighborPixel.dX, pY+neighborPixel.dY) != Color.ALPHA_OPAQUE)
				return false;
		
		return true;
	}
	
	private static void visitSourcePixel(Snaporta base, int radius, DoubleMaskPainter intensityMaskPainter, int pX, int pY)
	{
		double sourceOpacity = base.getColorAt(pX, pY).getOpacityRelative();
		
		for(int dY = -radius; dY <= radius; dY++)
			for(int dX = -radius; dX <= radius; dX++)
			{
				int x = pX+dX;
				int y = pY+dY;
				double targetOpacity = base.getColorAt(x, y).getOpacityRelative();
				if(targetOpacity == 1)
					continue;
				
				int distanceSquared = dX*dX+dY*dY;
				if(distanceSquared > radius*radius)
					continue;
				double distance = Math.sqrt(distanceSquared);
				
				double distanceIntensityFactor = (radius-distance+1)/radius;
				double intensity = sourceOpacity*distanceIntensityFactor*(1-targetOpacity);
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
