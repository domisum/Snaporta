package de.domisum.lib.snaporta.formatconversion.io;

import de.domisum.lib.auxilium.util.FileUtil;
import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.formatconversion.SnaportaBufferedImageConverter;

import java.awt.image.BufferedImage;
import java.io.File;

@API
public class SnaportaToFileWriter
{

	// DEPENDENCIES
	private final SnaportaBufferedImageConverter snaportaBufferedImageConverter = new SnaportaBufferedImageConverter();


	// SHORTCUT
	@API public static void write(File file, Snaporta snaporta)
	{
		SnaportaToFileWriter writer = new SnaportaToFileWriter();
		writer.writeToFile(file, snaporta);
	}


	// WRITING
	@API public void writeToFile(File file, Snaporta snaporta)
	{
		BufferedImage bufferedImage = snaportaBufferedImageConverter.convertTo(snaporta);
		FileUtil.writeImage(file, bufferedImage);
	}

}
