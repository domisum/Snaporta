package de.domisum.lib.snaporta.util;

import de.domisum.lib.auxilium.util.PHR;
import de.domisum.lib.snaporta.Snaporta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaValidate
{

	public static void validateInBounds(Snaporta snaporta, int x, int y)
	{
		validateDimension(x, snaporta.getWidth(), "x");
		validateDimension(y, snaporta.getHeight(), "y");
	}

	private static void validateDimension(int value, int bound, String valueName)
	{
		int maxValue = bound-1;

		Validate.inclusiveBetween(0,
				maxValue,
				value,
				PHR.r(valueName+" has to be in interval [{}-{}], but was {}", 0, maxValue, value)
		);
	}

}
