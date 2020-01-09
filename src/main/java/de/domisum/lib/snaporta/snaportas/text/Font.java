package de.domisum.lib.snaporta.snaportas.text;

import de.domisum.lib.auxilium.util.java.annotations.API;
import lombok.Getter;

import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Optional;

public final class Font
{

	@Getter
	private final java.awt.Font font;


	// INIT
	private Font(java.awt.Font font)
	{
		this.font = font.deriveFont(1f);
	}

	@API
	public static Font fromAwtFont(java.awt.Font font)
	{
		return new Font(font);
	}


	@API
	public static Font fromTTFFile(File ttfFile)
	{
		try
		{
			java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, ttfFile);
			return fromAwtFont(font);
		}
		catch(FontFormatException e)
		{
			throw new UncheckedIOException(new IOException("loaded font contains errors", e));
		}
		catch(IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	@API
	public static Optional<Font> ofSystemFont(String fontName)
	{
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		java.awt.Font[] graphicsEnvironmentFonts = graphicsEnvironment.getAllFonts();

		for(java.awt.Font font : graphicsEnvironmentFonts)
			if(Objects.equals(font.getName(), fontName))
				return Optional.of(fromAwtFont(font));

		return Optional.empty();
	}


	@API
	public static Font defaultFont()
	{
		return fromAwtFont(java.awt.Font.decode("SansSerif"));
	}

}
