package io.domisum.lib.snaporta.snaportas.filter.color;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class GreyscaleSnaporta
	implements Snaporta
{
	
	// INPUT
	private final Snaporta parent;
	
	
	@Override
	public String toString()
	{
		return PHR.r("{}({})", getClass().getSimpleName(), StringUtil.indent(parent.toString(), "\t"));
	}
	
	
	// SNAPORTA
	@Override
	public int getWidth()
	{
		return parent.getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return parent.getHeight();
	}
	
	@Override
	public int getArgbAt(int x, int y)
	{
		return getColorAt(x, y).toARGBInt();
	}
	
	@Override
	public Color getColorAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		var parentColor = parent.getColorAt(x, y);
		int level = (parentColor.getRed() + parentColor.getGreen() + parentColor.getBlue()) / 3;
		return Color.fromARGB(parentColor.getAlpha(), level, level, level);
	}
	
}
