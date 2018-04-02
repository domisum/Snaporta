package de.domisum.lib.snaporta.formatConversion;

import de.domisum.lib.snaporta.BasicEditableSnaporta;
import de.domisum.lib.snaporta.EditableSnaporta;
import de.domisum.lib.snaporta.Snaporta;

import java.awt.image.BufferedImage;

public class SnaportaBufferedImageConverter
{

	public EditableSnaporta convertFrom(BufferedImage bufferedImage)
	{
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		BasicEditableSnaporta snaporta = BasicEditableSnaporta.blankOfWidthAndHeight(width, height);

		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				snaporta.setARGBAt(x, y, bufferedImage.getRGB(x, y));

		return snaporta;
	}

	public BufferedImage convertTo(Snaporta snaporta)
	{
		BufferedImage bufferedImage = new BufferedImage(snaporta.getWidth(), snaporta.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for(int y = 0; y < snaporta.getHeight(); y++)
			for(int x = 0; x < snaporta.getWidth(); x++)
				bufferedImage.setRGB(x, y, snaporta.getARGBAt(x, y));

		return bufferedImage;
	}

}
