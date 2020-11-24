package io.domisum.lib.snaporta.snaportas.transform.interpolator.matrix;

import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.snaportas.transform.interpolator.Interpolator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;

public class BiLinearInterpolator
	implements Interpolator
{
	
	@Override
	public int interpolateARGBAt(Snaporta snaporta, double x, double y)
	{
		int xFloor = (int) Math.floor(x);
		int yFloor = (int) Math.floor(y);
		
		var valuedPixels = new HashSet<ValuedPixel>();
		getValuedPixel(snaporta, x, y, xFloor, yFloor).ifPresent(valuedPixels::add);
		getValuedPixel(snaporta, x, y, xFloor+1, yFloor).ifPresent(valuedPixels::add);
		getValuedPixel(snaporta, x, y, xFloor+1, yFloor+1).ifPresent(valuedPixels::add);
		getValuedPixel(snaporta, x, y, xFloor, yFloor+1).ifPresent(valuedPixels::add);
		
		double redSum = 0;
		double greenSum = 0;
		double blueSum = 0;
		double alphaSum = 0;
		
		for(var valuedPixel : valuedPixels)
		{
			double value = valuedPixel.getValue();
			redSum += valuedPixel.getColor().getRed()*value;
			greenSum += valuedPixel.getColor().getGreen()*value;
			blueSum += valuedPixel.getColor().getBlue()*value;
			alphaSum += valuedPixel.getColor().getAlpha()*value;
		}
		
		int red = (int) Math.round(redSum);
		int green = (int) Math.round(greenSum);
		int blue = (int) Math.round(blueSum);
		int alpha = (int) Math.round(alphaSum);
		
		red = Math.min(red, Color.COLOR_COMPONENT_MAX);
		green = Math.min(green, Color.COLOR_COMPONENT_MAX);
		blue = Math.min(blue, Color.COLOR_COMPONENT_MAX);
		alpha = Math.min(alpha, Color.COLOR_COMPONENT_MAX);
		
		return Color.fromARGB(alpha, red, green, blue).toARGBInt();
	}
	
	private Optional<ValuedPixel> getValuedPixel(Snaporta snaporta, double x, double y, int pX, int pY)
	{
		if((pX >= snaporta.getWidth()) || (pY >= snaporta.getHeight()))
			return Optional.empty();
		
		var color = snaporta.getColorAt(pX, pY);
		
		double dX = 1-((pX >= x) ? (pX-x) : (x-pX));
		double dY = 1-((pY >= y) ? (pY-y) : (y-pY));
		double value = dX*dY;
		
		var valuedPixel = new ValuedPixel(color, value);
		return Optional.of(valuedPixel);
	}
	
	
	@RequiredArgsConstructor
	private static class ValuedPixel
	{
		
		@Getter
		private final Color color;
		@Getter
		private final double value;
		
	}
	
}
