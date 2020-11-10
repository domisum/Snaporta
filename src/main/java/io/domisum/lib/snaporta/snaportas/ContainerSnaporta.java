package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import io.domisum.lib.snaporta.Snaporta;

@API
public class ContainerSnaporta
	implements Snaporta
{
	
	private final Snaporta contained;
	
	
	// INIT
	@API
	public ContainerSnaporta(Snaporta contained)
	{
		ValidationUtil.notNull(contained, "contained");
		this.contained = contained;
	}
	
	
	// SNAPORTA
	@Override
	public int getWidth()
	{
		return contained.getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return contained.getHeight();
	}
	
	@Override
	public int getARGBAt(int x, int y)
	{
		return contained.getARGBAt(x, y);
	}
	
}
