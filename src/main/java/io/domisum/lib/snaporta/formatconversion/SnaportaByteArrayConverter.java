package io.domisum.lib.snaporta.formatconversion;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

@API
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaByteArrayConverter
{

	public static byte[] convert(Snaporta snaporta)
	{
		var bufferedImage = SnaportaBufferedImageConverter.convert(snaporta);

		try
		{
			var byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
			return byteArrayOutputStream.toByteArray();
		}
		catch(IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

}
