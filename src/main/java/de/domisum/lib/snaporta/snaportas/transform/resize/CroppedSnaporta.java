package de.domisum.lib.snaporta.snaportas.transform.resize;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.util.SnaportaValidate;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

@API
public class CroppedSnaporta implements Snaporta
{

	private final Snaporta backingSnaporta;

	private final int cropX;
	private final int cropY;
	@Getter
	private final int width;
	@Getter
	private final int height;


	// INIT
	@API
	public CroppedSnaporta(Snaporta backingSnaporta, int cropX, int cropY, int width, int height)
	{
		Validate.isTrue(width >= 1, "width has to be at least 1, was "+width);
		Validate.isTrue(height >= 1, "height has to be at least 1, was "+height);
		SnaportaValidate.validateInInterval(0, backingSnaporta.getWidth()-1, "cropX+width", cropX+width);
		SnaportaValidate.validateInInterval(0, backingSnaporta.getHeight()-1, "cropY+height", cropY+height);

		this.backingSnaporta = backingSnaporta;
		this.cropX = cropX;
		this.cropY = cropY;
		this.width = width;
		this.height = height;
	}


	@Override
	public int getARGBAt(int x, int y)
	{
		SnaportaValidate.validateInBounds(this, x, y);
		return backingSnaporta.getARGBAt(x+cropX, y+cropY);
	}

}
