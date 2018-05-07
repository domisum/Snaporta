package de.domisum.lib.snaporta.formatconversion.io;

import de.domisum.lib.auxilium.util.FileUtil;
import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.formatconversion.SnaportaBufferedImageConverter;

import java.awt.image.BufferedImage;
import java.io.File;

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


	// READING
	@API public Snaporta readFromFile(File file)
	{
		BufferedImage bufferedImage = FileUtil.readImage(file);
		return snaportaBufferedImageConverter.convertFrom(bufferedImage);
	}

}
