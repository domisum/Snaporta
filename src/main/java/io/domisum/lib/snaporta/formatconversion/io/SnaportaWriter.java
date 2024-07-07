package io.domisum.lib.snaporta.formatconversion.io;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.FileUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.formatconversion.SnaportaBufferedImageConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Locale;

@API
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaWriter
{
	
	// SHORTCUT
	@API
	public static void writeToFile(File file, Snaporta snaporta)
	{
		var bufferedImage = SnaportaBufferedImageConverter.convert(snaporta);
		FileUtil.writeImage(file, bufferedImage);
	}
	
	@API
	public static byte[] writeToRaw(Snaporta snaporta)
	{
		return writeToRaw(snaporta, "png");
	}
	
	@API
	public static byte[] writeToRaw(Snaporta snaporta, String format)
	{
		return writeToRaw(snaporta, format, null);
	}
	
	@API
	public static byte[] writeToRaw(Snaporta snaporta, String format, Double quality)
	{
		format = format.toLowerCase(Locale.ROOT);
		if(format.equals("jpg"))
			format = "jpeg";
		
		boolean doesFormatSupportAlpha = "png".equals(format);
		int bufferedImageType = doesFormatSupportAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
		var bufferedImage = SnaportaBufferedImageConverter.convert(snaporta, bufferedImageType);
		
		try
		{
			var baos = new ByteArrayOutputStream();
			
			if("jpeg".equals(format) && quality != null)
			{
				var iter = ImageIO.getImageWritersByFormatName("jpeg");
				var writer = (ImageWriter) iter.next();
				
				var iwp = writer.getDefaultWriteParam();
				iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				iwp.setCompressionQuality(quality.floatValue());
				
				writer.setOutput(new MemoryCacheImageOutputStream(baos));
				var iioImage = new IIOImage(bufferedImage, null, null);
				writer.write(null, iioImage, iwp);
				writer.dispose();
			}
			else
				ImageIO.write(bufferedImage, format, baos);
			
			return baos.toByteArray();
		}
		catch(IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}
	
}
