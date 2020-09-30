package io.domisum.lib.snaporta.snaportas.color;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class SolidColorSnaporta
	implements Snaporta
{
	
	// ATTRIBUTES
	@Getter
	private final int width;
	@Getter
	private final int height;
	
	@Getter
	private final Color color;
	
	
	// SNAPORTA
	@Override
	public int getARGBAt(int x, int y)
	{
		return getColorAt(x, y).toARGBInt();
	}
	
	@Override
	public Color getColorAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return color;
	}
	
}
