package io.domisum.lib.snaporta.mask.doubl.gradient;

import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import io.domisum.lib.snaporta.mask.doubl.DoubleMask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class StraightGradientDoubleMask implements DoubleMask
{

	// CONSTANTS
	private static final double HIGH_VALUE = 1.0;
	private static final double LOW_VALUE = 0.0;

	// SETTINGS
	@Getter
	private final int width;
	@Getter
	private final int height;

	private final int lowValueCoord;
	private final int highValueCoord;


	// MASK
	private boolean isRightHigh()
	{
		return highValueCoord > lowValueCoord;
	}

	protected double getGradientAt(int coord)
	{
		int left = Math.min(lowValueCoord, highValueCoord);
		int right = Math.max(lowValueCoord, highValueCoord);
		double leftNumber = isRightHigh() ? LOW_VALUE : HIGH_VALUE;
		double rightNumber = isRightHigh() ? HIGH_VALUE : LOW_VALUE;

		if(coord >= right)
			return rightNumber;
		if(coord <= left)
			return leftNumber;

		double leftDistance = (coord-left)/(double) (right-left);
		double leftProximity = 1-leftDistance;
		return MathUtil.mix(leftNumber, leftProximity, rightNumber);
	}

}
