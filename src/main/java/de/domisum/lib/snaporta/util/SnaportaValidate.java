package de.domisum.lib.snaporta.util;

import de.domisum.lib.auxilium.util.PHR;
import de.domisum.lib.snaporta.Snaporta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaValidate
{

	// BOUNDS
	public static void validateInBounds(Snaporta snaporta, int x, int y)
	{
		validateInInterval(0, snaporta.getWidth()-1, "x", x);
		validateInInterval(0, snaporta.getHeight()-1, "y", y);
	}


	// COLOR
	public static void validateColorComponentInRange(int componentValue, String componentName)
	{
		validateInInterval(0, 255, componentName+" color component", componentValue);
	}


	// GENERAL
	private static void validateInInterval(int minValue, int maxValue, String valueName, int value)
	{
		Validate.inclusiveBetween(
				minValue,
				maxValue,
				value,
				PHR.r(valueName+" has to be in interval [{}-{}], but was {}", minValue, maxValue, value)
		);
	}

}