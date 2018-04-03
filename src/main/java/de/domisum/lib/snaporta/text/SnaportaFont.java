package de.domisum.lib.snaporta.text;

import de.domisum.lib.auxilium.util.java.annotations.API;
import lombok.Getter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Optional;

public final class SnaportaFont
{

	@Getter private final Font font;


	// INIT
	private SnaportaFont(Font font)
	{
		this.font = font.deriveFont(1f);
	}

	@API public static SnaportaFont fromAwtFont(Font font)
	{
		return new SnaportaFont(font);
	}


	@API public static SnaportaFont fromTTFFile(File ttfFile)
	{
		try
		{
			Font font = Font.createFont(Font.TRUETYPE_FONT, ttfFile);
			return fromAwtFont(font);
		}
		catch(FontFormatException e)
		{
			throw new RuntimeException(e); // TODO better way to handle this exception
		}
		catch(IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	@API public static Optional<SnaportaFont> ofSystemFont(String fontName)
	{
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] graphicsEnvironmentFonts = graphicsEnvironment.getAllFonts();

		for(Font font : graphicsEnvironmentFonts)
			if(Objects.equals(font.getName(), fontName))
				return Optional.of(fromAwtFont(font));

		return Optional.empty();
	}

}
