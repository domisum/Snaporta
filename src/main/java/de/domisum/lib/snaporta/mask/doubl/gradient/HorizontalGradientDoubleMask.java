package de.domisum.lib.snaporta.mask.doubl.gradient;

import de.domisum.lib.auxilium.util.math.MathUtil;
import de.domisum.lib.snaporta.mask.doubl.DoubleMask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HorizontalGradientDoubleMask implements DoubleMask
{

	// CONSTANTS
	private static final double HIGH_VALUE = 1.0;
	private static final double LOW_VALUE = 0.0;

	// SETTINGS
	@Getter private final int width;
	@Getter private final int height;

	private final int lowValueX;
	private final int highValueX;


	// MASK
	private boolean isRightHigh()
	{
		return highValueX > lowValueX;
	}

	@Override public double getValueAt(int x, int y)
	{
		int left = Math.min(lowValueX, highValueX);
		int right = Math.max(lowValueX, highValueX);
		double leftNumber = isRightHigh() ? LOW_VALUE : HIGH_VALUE;
		double rightNumber = isRightHigh() ? HIGH_VALUE : LOW_VALUE;

		if(x >= right)
			return rightNumber;
		if(x <= left)
			return leftNumber;

		double leftDistance = (x-left)/(double) (right-left);
		double leftProximity = 1-leftDistance;
		return MathUtil.mix(leftNumber, leftProximity, rightNumber);
	}

}
