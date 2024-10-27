package io.domisum.lib.snaporta.snaportas.mask;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.mask.doubl.DoubleMaskPainter;
import io.domisum.lib.snaporta.snaportas.GeneratedSnaporta;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FadeEdgesSnaporta
	extends GeneratedSnaporta
{
	
	private final Snaporta base;
	private final int distanceFromEdge;
	
	
	@Override
	public String toString()
	{
		return PHR.r("{}(dist={}\n{})", getClass().getSimpleName(), distanceFromEdge,
			StringUtil.indent(base.toString(), "\t"));
	}
	
	
	@Override
	protected Snaporta generate()
	{
		var painter = DoubleMaskPainter.sized(base.getWidth(), base.getHeight());
		for(int y = 0; y < base.getHeight(); y++)
			for(int x = 0; x < base.getWidth(); x++)
			{
				int distanceToEdge = x + 1;
				distanceToEdge = Math.min(distanceToEdge, y + 1);
				distanceToEdge = Math.min(distanceToEdge, base.getWidth() - x);
				distanceToEdge = Math.min(distanceToEdge, base.getHeight() - y);
				
				double opacity = distanceToEdge > distanceFromEdge ? 1 :
					distanceToEdge / (double) distanceFromEdge;
				opacity *= opacity; // quadratic falloff
				painter.setValueAt(x, y, opacity);
			}
		
		return new DoubleMaskOpacitySnaporta(base, painter.toMask());
	}
	
}
