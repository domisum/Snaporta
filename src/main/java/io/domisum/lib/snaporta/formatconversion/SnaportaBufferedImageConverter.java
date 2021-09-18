package io.domisum.lib.snaporta.formatconversion;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.snaportas.SnaportaPainter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaBufferedImageConverter
{
	
	@API
	public static BufferedImage convert(Snaporta snaporta)
	{
		return convert(snaporta, BufferedImage.TYPE_INT_ARGB);
	}
	
	@API
	public static BufferedImage convert(Snaporta snaporta, int bufferedImageType)
	{
		final int rgbMask = 0xFFFFFF;
		
		var bufferedImage = new BufferedImage(snaporta.getWidth(), snaporta.getHeight(), bufferedImageType);
		
		int[] pixelsLinear = new int[snaporta.getWidth()*snaporta.getHeight()];
		for(int y = 0; y < snaporta.getHeight(); y++)
			for(int x = 0; x < snaporta.getWidth(); x++)
			{
				int pixel;
				if(bufferedImageType == BufferedImage.TYPE_INT_ARGB)
					pixel = snaporta.getArgbAt(x, y);
				else if(bufferedImageType == BufferedImage.TYPE_INT_RGB)
					pixel = snaporta.getArgbAt(x, y)&rgbMask;
				else
					throw new UnsupportedOperationException("Unsupported BufferedImageType: "+bufferedImageType);
				
				pixelsLinear[(y*snaporta.getWidth())+x] = pixel;
			}
		
		var raster = (WritableRaster) bufferedImage.getData();
		raster.setDataElements(0, 0, snaporta.getWidth(), snaporta.getHeight(), pixelsLinear);
		bufferedImage.setData(raster);
		
		return bufferedImage;
	}
	
	@API
	public static Snaporta convert(BufferedImage bufferedImage)
	{
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		var painter = new SnaportaPainter(width, height);
		
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				painter.setARGBAt(x, y, bufferedImage.getRGB(x, y));
		
		return painter.toSnaporta();
	}
	
}
