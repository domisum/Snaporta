package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import io.domisum.lib.snaporta.color.Colors;
import io.domisum.lib.snaporta.snaportas.text.Font;
import io.domisum.lib.snaporta.snaportas.text.TextSnaporta;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class TextSnaportaTest
{
	
	@Test
	void testCropLessThanEqualDimensions()
	{
		var fontOptional = Font.ofSystemFont("BebasNeue");
		Assumptions.assumeTrue(fontOptional.isPresent());
		var font = fontOptional.get();
		
		var textSnaporta = new TextSnaporta.Builder(532, 140, "Master Yi")
			.alignCenterCenter()
			.font(font)
			.fontAsBigAsPossible()
			.fontColor(Colors.WHITE)
			.build();
		var cropped = textSnaporta.getCroppedToVisible();
		
		ValidationUtil.lessThanOrEqual(cropped.getWidth(), textSnaporta.getWidth(), "width");
		ValidationUtil.lessThanOrEqual(cropped.getHeight(), textSnaporta.getHeight(), "height");
	}
	
}
