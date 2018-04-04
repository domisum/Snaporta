package de.domisum.lib.snaporta.snaportas.composite;

import de.domisum.lib.snaporta.Snaporta;
import de.domisum.lib.snaporta.color.SnaportaColors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Comparator;

@RequiredArgsConstructor
public class CompositeSnaportaComponent implements Comparable<CompositeSnaportaComponent>
{

	private final Snaporta snaporta;
	private final int x;
	private final int y;

	@Getter private final double z;


	// COMPARE
	@Override public int compareTo(@Nonnull CompositeSnaportaComponent other)
	{
		return Comparator.comparing(CompositeSnaportaComponent::getZ).reversed().compare(this, other);
	}


	// COMPONENT
	public int getARGBAt(int x, int y)
	{
		int inSnaportaX = x-this.x;
		int inSnaportaY = y-this.y;

		if(snaporta.isInBounds(inSnaportaX, inSnaportaY))
			return snaporta.getARGBAt(inSnaportaX, inSnaportaY);
		else
			return SnaportaColors.TRANSPARENT.toARGBInt();
	}

}
