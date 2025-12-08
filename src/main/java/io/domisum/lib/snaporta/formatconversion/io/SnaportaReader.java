package io.domisum.lib.snaporta.formatconversion.io;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.exceptions.IncompleteCodeError;
import io.domisum.lib.auxiliumlib.util.FileUtil;
import io.domisum.lib.snaporta.CardinalRotation;
import io.domisum.lib.snaporta.Orientation;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.formatconversion.SnaportaBufferedImageConverter;
import io.domisum.lib.snaporta.snaportas.transform.CardinallyRotatedSnaporta;
import io.domisum.lib.snaporta.snaportas.transform.MirroredSnaporta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

@API
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaReader
{
	
	// READING
	@API
	public static Snaporta readFromFile(File file)
	{
		try
		{
			return readFromFileUncaught(file);
		}
		catch(IOException e)
		{
			throw new UncheckedIOException("Failed to read snaporta from image " + file, e);
		}
	}
	
	@API
	public static Snaporta readFromFileUncaught(File file)
		throws IOException
	{
		var bufferedImage = FileUtil.readImageUncaught(file);
		var snaporta = SnaportaBufferedImageConverter.convert(bufferedImage);
		
		var exifRotation = readExifOrientation(file);
		if(exifRotation != null)
			snaporta = applyExifOrientation(snaporta, exifRotation);
		
		return snaporta;
	}
	
	public static Snaporta readRaw(byte[] raw)
	{
		try
		{
			var bufferedImage = ImageIO.read(new ByteArrayInputStream(raw));
			if(bufferedImage == null)
				throw new IOException("Failed to identify image type of bytes");
			return SnaportaBufferedImageConverter.convert(bufferedImage);
		}
		catch(IOException e)
		{
			throw new UncheckedIOException("Failed to read snaporta from byte[]", e);
		}
	}
	
	
	// EXIF
	private static Integer readExifOrientation(File file)
		throws IOException
	{
		try
		{
			var metadata = ImageMetadataReader.readMetadata(file);
			var metadataDir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			if(metadataDir == null)
				return null;
			return metadataDir.getInt(ExifIFD0Directory.TAG_ORIENTATION);
		}
		catch(MetadataException ignored)
		{
			return null;
		}
		catch(ImageProcessingException e)
		{
			throw new IOException(e);
		}
	}
	
	private static Snaporta applyExifOrientation(Snaporta input, Integer orientation)
	{
		// from https://chunter.tistory.com/143
		// (row #0 is) / (column #0 is)
		if(orientation == 1 || orientation == 2) // top / left
			return input;
		else if(orientation == 3 || orientation == 4) // bottom / right
			return new CardinallyRotatedSnaporta(input, CardinalRotation._180);
		else if(orientation == 5) // left / top
			return new CardinallyRotatedSnaporta(new MirroredSnaporta(input, Orientation.HORIZONTAL), CardinalRotation.COUNTERCLOCKWISE_90);
		else if(orientation == 6) // right / top
			return new CardinallyRotatedSnaporta(input, CardinalRotation.CLOCKWISE_90);
		else if(orientation == 7) // right / bottom
			return new CardinallyRotatedSnaporta(new MirroredSnaporta(input, Orientation.HORIZONTAL), CardinalRotation.CLOCKWISE_90);
		else if(orientation == 8) // left / bottom
			return new CardinallyRotatedSnaporta(input, CardinalRotation.COUNTERCLOCKWISE_90);
		else
			throw new IncompleteCodeError("Unexpected exif orientation: " + orientation);
	}
	
}
