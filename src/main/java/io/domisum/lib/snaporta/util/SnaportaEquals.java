package io.domisum.lib.snaporta.util;

import io.domisum.lib.snaporta.Snaporta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaEquals
{
	
	public static boolean equals(Snaporta a, Snaporta b)
	{
		if((a == null) || (b == null))
			return a == b;
		if(a.getWidth() != b.getWidth())
			return false;
		if(a.getHeight() != b.getHeight())
			return false;
		
		for(int y = 0; y < a.getHeight(); y++)
			for(int x = 0; x < a.getWidth(); x++)
				if(a.getArgbAt(x, y) != b.getArgbAt(x, y))
					return false;
		
		return true;
	}
	
}