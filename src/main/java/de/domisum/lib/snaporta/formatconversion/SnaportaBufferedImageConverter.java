package de.domisum.lib.snaporta.formatconversion;

import de.domisum.lib.snaporta.EditableSnaporta;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.snaportas.BasicEditableSnaporta;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

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

		int[] pixelsLinear = new int[snaporta.getWidth()*snaporta.getHeight()];
		for(int y = 0; y < snaporta.getHeight(); y++)
			for(int x = 0; x < snaporta.getWidth(); x++)
				pixelsLinear[(y*snaporta.getWidth())+x] = snaporta.getARGBAt(x, y);

		WritableRaster raster = (WritableRaster) bufferedImage.getData();
		raster.setDataElements(0, 0, snaporta.getWidth(), snaporta.getHeight(), pixelsLinear);
		bufferedImage.setData(raster);

		return bufferedImage;
	}

}
