package de.domisum.lib.snaporta.mask.bool.composite;

import de.domisum.lib.snaporta.mask.bool.BooleanMask;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class OrBooleanMask implements BooleanMask
{

	// MASKS
	private final Collection<BooleanMask> masks;
	@Getter
	private final int width;
	@Getter
	private final int height;


	// INIT
	public OrBooleanMask(Collection<BooleanMask> masks)
	{
		this.masks = Collections.unmodifiableCollection(masks);
		Validate.notEmpty(masks, "masks can't be empty");


		int maxWidth = 0;
		int maxHeight = 0;
		for(BooleanMask mask : masks)
		{
			maxWidth = Math.max(maxWidth, mask.getWidth());
			maxHeight = Math.max(maxHeight, mask.getHeight());
		}
		width = maxWidth;
		height = maxHeight;
	}

	public OrBooleanMask(BooleanMask... masks)
	{
		this(Arrays.asList(masks));
	}


	// MASK
	@Override
	public boolean getValueAt(int x, int y)
	{
		for(BooleanMask mask : masks)
			if(mask.isInBounds(x, y) && mask.getValueAt(x, y))
				return true;

		return false;
	}

}
