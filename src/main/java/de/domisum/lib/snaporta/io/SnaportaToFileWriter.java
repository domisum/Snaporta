package de.domisum.lib.snaporta.io;

import de.domisum.lib.auxilium.util.FileUtil;
import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.formatConversion.SnaportaBufferedImageConverter;

import java.awt.image.BufferedImage;
import java.io.File;

public class SnaportaToFileWriter
{

	// DEPENDENCIES
	private final SnaportaBufferedImageConverter snaportaBufferedImageConverter = new SnaportaBufferedImageConverter();


	// WRITING
	public void writeToFile(File file, Snaporta snaporta)
	{
		BufferedImage bufferedImage = snaportaBufferedImageConverter.convertTo(snaporta);
		FileUtil.writeImage(file, bufferedImage);
	}

}
