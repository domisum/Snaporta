package io.domisum.lib.snaporta.snaportas.color;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
@Getter
public class SolidColorSnaporta
	implements Snaporta
{
	
	// ATTRIBUTES
	private final int width;
	private final int height;
	
	private final Color color;
	
	
	@Override
	public String toString()
	{
		return PHR.r("{}(w={} x h={}, c={})", getClass().getSimpleName(),
			width, height, color);
	}
	
	
	// SNAPORTA
	@Override
	public int getArgbAt(int x, int y)
	{
		return getColorAt(x, y).toARGBInt();
	}
	
	@Override
	public Color getColorAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return color;
	}
	
	@Override
	public boolean isBlank()
	{
		return color.isFullyTransparent();
	}
	
}
