package de.domisum.lib.snaporta.io;

import de.domisum.lib.auxilium.util.FileUtil;
import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.EditableSnaporta;
import de.domisum.lib.snaporta.formatConversion.SnaportaBufferedImageConverter;

import java.awt.image.BufferedImage;
import java.io.File;

@API
public class SnaportaFromFileReader
{

	// DEPENDENCIES
	private final SnaportaBufferedImageConverter snaportaBufferedImageConverter = new SnaportaBufferedImageConverter();


	// READING
	@API public EditableSnaporta readFromFile(File file)
	{
		BufferedImage bufferedImage = FileUtil.readImage(file);
		return snaportaBufferedImageConverter.convertFrom(bufferedImage);
	}

}
