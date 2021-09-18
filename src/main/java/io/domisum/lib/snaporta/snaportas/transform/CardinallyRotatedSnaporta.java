package io.domisum.lib.snaporta.snaportas.transform;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.exceptions.IncompleteCodeError;
import io.domisum.lib.snaporta.CardinalRotation;
import io.domisum.lib.snaporta.Snaporta;

@SuppressWarnings("SuspiciousNameCombination")
public class CardinallyRotatedSnaporta
	implements Snaporta
{
	
	private final Snaporta baseSnaporta;
	private final CardinalRotation rotation;
	
	
	// INIT
	@API
	public CardinallyRotatedSnaporta(Snaporta baseSnaporta, CardinalRotation rotation)
	{
		this.baseSnaporta = baseSnaporta;
		this.rotation = rotation;
	}
	
	
	// SNAPORTA
	@Override
	public int getWidth()
	{
		return isWidthAndHeightSwapped() ? baseSnaporta.getHeight() : baseSnaporta.getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return isWidthAndHeightSwapped() ? baseSnaporta.getWidth() : baseSnaporta.getHeight();
	}
	
	private boolean isWidthAndHeightSwapped()
	{
		if(rotation == CardinalRotation.NONE || rotation == CardinalRotation._180)
			return false;
		else if(rotation == CardinalRotation.CLOCKWISE_90 || rotation == CardinalRotation.COUNTERCLOCKWISE_90)
			return true;
		else
			throw new IncompleteCodeError();
	}
	
	@Override
	public int getArgbAt(int x, int y)
	{
		int tX, tY;
		if(rotation == CardinalRotation.NONE)
		{
			tX = x;
			tY = y;
		}
		else if(rotation == CardinalRotation.CLOCKWISE_90)
		{
			tX = y;
			tY = baseSnaporta.getHeight()-x-1;
		}
		else if(rotation == CardinalRotation._180)
		{
			tX = baseSnaporta.getWidth()-x-1;
			tY = baseSnaporta.getHeight()-y-1;
		}
		else if(rotation == CardinalRotation.COUNTERCLOCKWISE_90)
		{
			tX = baseSnaporta.getWidth()-y-1;
			tY = x;
		}
		else
			throw new IncompleteCodeError();
		
		return baseSnaporta.getArgbAt(tX, tY);
	}
	
}
