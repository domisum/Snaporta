package de.domisum.lib.snaporta.io;

import de.domisum.lib.auxilium.util.FileUtil;
import de.domisum.lib.snaporta.EditableSnaporta;
import de.domisum.lib.snaporta.formatConversion.SnaportaBufferedImageConverter;

import java.awt.image.BufferedImage;
import java.io.File;

public class SnaportaFromFileReader
{

	// DEPENDENCIES
	private final SnaportaBufferedImageConverter snaportaBufferedImageConverter = new SnaportaBufferedImageConverter();


	// READING
	public EditableSnaporta readFromFile(File file)
	{
		BufferedImage bufferedImage = FileUtil.readImage(file);
		return snaportaBufferedImageConverter.convertFrom(bufferedImage);
	}

}
