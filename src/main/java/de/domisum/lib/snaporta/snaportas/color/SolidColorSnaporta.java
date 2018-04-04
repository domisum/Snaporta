package de.domisum.lib.snaporta.snaportas.color;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.Color;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class SolidColorSnaporta implements Snaporta
{

	// ATTRIBUTES
	@Getter private final int width;
	@Getter private final int height;

	@Getter private final Color color;


	// SNAPORTA
	@Override public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return color.toARGBInt();
	}

}
