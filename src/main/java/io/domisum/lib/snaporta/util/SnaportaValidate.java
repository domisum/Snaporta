package io.domisum.lib.snaporta.util;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.snaporta.color.Color;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnaportaValidate
{
	
	// BOUNDS
	public static void validateInBounds(Sized sized, int x, int y)
	{
		validateInInterval(0, sized.getWidth()-1, "x", x);
		validateInInterval(0, sized.getHeight()-1, "y", y);
	}
	
	
	// COLOR
	public static void validateColorComponentInRange(int componentValue, String componentName)
	{
		validateInInterval(0, Color.COLOR_COMPONENT_MAX, componentName+" color component", componentValue);
	}
	
	
	// GENERAL
	public static void validateInInterval(int minValue, int maxValue, String valueName, int value)
	{
		if((value < minValue) || (value > maxValue))
			throw new IllegalArgumentException(PHR.r(valueName+" has to be in interval [{}-{}], but was {}",
				minValue, maxValue, value));
	}
	
	public static void validateInDoubleInterval(double minValue, double maxValue, String valueName, double value)
	{
		if((value < minValue) || (value > maxValue))
			throw new IllegalArgumentException(PHR.r(valueName+" has to be in interval [{}-{}], but was {}",
				minValue, maxValue, value));
	}
	
}
