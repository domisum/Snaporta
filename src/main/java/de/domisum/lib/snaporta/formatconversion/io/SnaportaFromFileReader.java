package de.domisum.lib.snaporta.formatconversion.io;

import de.domisum.lib.auxilium.util.FileUtil;
import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.formatconversion.SnaportaBufferedImageConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@API
public class SnaportaFromFileReader
{

	// DEPENDENCIES
	private final SnaportaBufferedImageConverter snaportaBufferedImageConverter = new SnaportaBufferedImageConverter();


	// SHORTCUT
	@API public static Snaporta read(File file)
	{
		SnaportaFromFileReader fromFileReader = new SnaportaFromFileReader();
		return fromFileReader.readFromFile(file);
	}

	@API public static Snaporta readUncaught(File file) throws IOException
	{
		SnaportaFromFileReader fromFileReader = new SnaportaFromFileReader();
		return fromFileReader.readFromFileUncaught(file);
	}


	// READING
	@API public Snaporta readFromFile(File file)
	{
		BufferedImage bufferedImage = readBufferedImage(file);
		return snaportaBufferedImageConverter.convertFrom(bufferedImage);
	}

	@API public Snaporta readFromFileUncaught(File file) throws IOException
	{
		BufferedImage bufferedImage = readBufferedImageUncaught(file);
		return snaportaBufferedImageConverter.convertFrom(bufferedImage);
	}


	private BufferedImage readBufferedImage(File file)
	{
		return FileUtil.readImage(file);
	}

	private BufferedImage readBufferedImageUncaught(File file) throws IOException
	{
		return FileUtil.readImageUncaught(file);
	}

}
