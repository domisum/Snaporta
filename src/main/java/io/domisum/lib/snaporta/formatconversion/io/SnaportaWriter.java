package io.domisum.lib.snaporta.formatconversion.io;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.FileUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.formatconversion.SnaportaBufferedImageConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

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
		var bufferedImage = SnaportaBufferedImageConverter.convert(snaporta);
		try
		{
			var baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			return baos.toByteArray();
		}
		catch(IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}
	
}
