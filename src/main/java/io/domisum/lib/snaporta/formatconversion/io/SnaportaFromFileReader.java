package io.domisum.lib.snaporta.formatconversion.io;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.file.FileUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.formatconversion.SnaportaBufferedImageConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@API
public class SnaportaFromFileReader
{
	
	// DEPENDENCIES
	private final SnaportaBufferedImageConverter snaportaBufferedImageConverter = new SnaportaBufferedImageConverter();
	
	
	// SHORTCUT
	@API
	public static Snaporta read(File file)
	{
		var fromFileReader = new SnaportaFromFileReader();
		var snaporta = fromFileReader.readFromFile(file);
		
		return snaporta;
	}
	
	@API
	public static Snaporta readUncaught(File file)
		throws IOException
	{
		var fromFileReader = new SnaportaFromFileReader();
		var snaporta = fromFileReader.readFromFileUncaught(file);
		
		return snaporta;
	}
	
	
	// READING
	@API
	public Snaporta readFromFile(File file)
	{
		var bufferedImage = readBufferedImage(file);
		var snaporta = snaportaBufferedImageConverter.convertFrom(bufferedImage);
		
		return snaporta;
	}
	
	@API
	public Snaporta readFromFileUncaught(File file)
		throws IOException
	{
		var bufferedImage = readBufferedImageUncaught(file);
		var snaporta = snaportaBufferedImageConverter.convertFrom(bufferedImage);
		return snaporta;
	}
	
	
	private BufferedImage readBufferedImage(File file)
	{
		return FileUtil.readImage(file);
	}
	
	private BufferedImage readBufferedImageUncaught(File file)
		throws IOException
	{
		return FileUtil.readImageUncaught(file);
	}
	
}
