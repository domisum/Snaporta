package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;

@API
public class CenteredSnaporta extends OffsetSnaporta
{

	// INIT
	public CenteredSnaporta(int width, int height, Snaporta snaportaToCenter)
	{
		super(snaportaToCenter, (width-snaportaToCenter.getWidth())/2, (height-snaportaToCenter.getHeight())/2);
	}

}
