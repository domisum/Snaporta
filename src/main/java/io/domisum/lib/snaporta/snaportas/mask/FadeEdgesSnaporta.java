package io.domisum.lib.snaporta.snaportas.mask;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Padding;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.mask.doubl.DoubleMask;
import io.domisum.lib.snaporta.snaportas.GeneratedSnaporta;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FadeEdgesSnaporta
	extends GeneratedSnaporta
{
	
	private final Snaporta base;
	private final Padding fadeDistances;
	
	
	@Override
	public String toString()
	{
		return PHR.r("{}(dist={}\n{})", getClass().getSimpleName(), fadeDistances,
			StringUtil.indent(base.toString(), "\t"));
	}
	
	@Override
	protected Snaporta generate()
	{return new DoubleMaskOpacitySnaporta(base, new FadeEdgesOpacityMask());}
	
	
	// INTERNAL
	private class FadeEdgesOpacityMask
		implements DoubleMask
	{
		
		@Override
		public double getValueAt(int x, int y)
		{
			double opacity = 1;
			
			if(x < fadeDistances.getLeft())
			{
				double f = (x + 0.5) / fadeDistances.getLeft();
				if(f < opacity)
					opacity = f;
			}
			if(getWidth() - 1 - x < fadeDistances.getRight())
			{
				double f = (getWidth() - x - 0.5) / fadeDistances.getRight();
				if(f < opacity)
					opacity = f;
			}
			if(y < fadeDistances.getTop())
			{
				double f = (y + 0.5) / fadeDistances.getTop();
				if(f < opacity)
					opacity = f;
			}
			if(getHeight() - 1 - y < fadeDistances.getBottom())
			{
				double f = (getHeight() - y - 0.5) / fadeDistances.getBottom();
				if(f < opacity)
					opacity = f;
			}
			
			return opacity;
		}
		
		@Override
		public int getWidth()
		{return base.getWidth();}
		
		@Override
		public int getHeight()
		{return base.getHeight();}
		
	}
	
}
