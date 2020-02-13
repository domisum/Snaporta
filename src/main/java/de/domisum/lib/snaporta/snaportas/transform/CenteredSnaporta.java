package de.domisum.lib.snaporta.snaportas.transform;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;

@API
public class CenteredSnaporta extends OffsetSnaporta
{

	// INIT
	public CenteredSnaporta(int width, int height, Snaporta snaportaToCenter)
	{
		super(snaportaToCenter, (width-snaportaToCenter.getWidth())/2, (height-snaportaToCenter.getHeight())/2);
	}

}
