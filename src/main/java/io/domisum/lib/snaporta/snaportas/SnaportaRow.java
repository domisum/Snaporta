package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.exceptions.IncompleteCodeError;
import io.domisum.lib.auxiliumlib.util.StringListUtil;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.util.Sized;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@API
@RequiredArgsConstructor
public class SnaportaRow
	extends GeneratedSnaporta
{
	
	// CONSTANTS
	private static final int DEFAULT_BETWEEN_PADDING = 0;
	
	// SETTINGS
	private final List<Snaporta> snaportas;
	private final RowDirection direction;
	private final RowItemAlignment rowItemAlignment;
	
	private final int betweenPadding;
	
	
	// HOUSEKEEPING
	@API
	public static SnaportaRow horizontal(RowItemAlignment rowItemAlignment, int betweenPadding, Snaporta... snaportas)
	{
		return horizontal(rowItemAlignment, betweenPadding, Arrays.asList(snaportas));
	}
	
	@API
	public static SnaportaRow horizontal(RowItemAlignment rowItemAlignment, int betweenPadding, List<Snaporta> snaportas)
	{
		return new SnaportaRow(snaportas, RowDirection.HORIZONTAL, rowItemAlignment, betweenPadding);
	}
	
	@API
	public static SnaportaRow horizontal(RowItemAlignment rowItemAlignment, List<Snaporta> snaportas)
	{
		return new SnaportaRow(snaportas, RowDirection.HORIZONTAL, rowItemAlignment, DEFAULT_BETWEEN_PADDING);
	}
	
	@API
	public static SnaportaRow vertical(RowItemAlignment rowItemAlignment, int betweenPadding, Snaporta... snaportas)
	{
		return vertical(rowItemAlignment, betweenPadding, Arrays.asList(snaportas));
	}
	
	@API
	public static SnaportaRow vertical(RowItemAlignment rowItemAlignment, int betweenPadding, List<Snaporta> snaportas)
	{
		return new SnaportaRow(snaportas, RowDirection.VERTICAL, rowItemAlignment, betweenPadding);
	}
	
	@API
	public static SnaportaRow vertical(RowItemAlignment rowItemAlignment, List<Snaporta> snaportas)
	{
		return new SnaportaRow(snaportas, RowDirection.VERTICAL, rowItemAlignment, DEFAULT_BETWEEN_PADDING);
	}
	
	@Override
	public String toString()
	{
		var itemsDisplay = new ArrayList<>(snaportas);
		
		String inner = itemsDisplay.isEmpty() ? " <empty>"
			: "\n" + StringUtil.indent(StringListUtil.list(itemsDisplay, "\n"), "\t");
		return PHR.r("{}(d={}, ria={}, pd={}{})", getClass().getSimpleName(),
			direction, rowItemAlignment, betweenPadding, inner);
	}
	
	
	// GENERATE
	@Override
	protected Snaporta generate()
	{
		if(rowItemAlignment.getRowDirection() != null && rowItemAlignment.getRowDirection() != direction)
			throw new IllegalArgumentException(PHR.r("Row direction {} doesn't support row item alignment {}",
				direction, rowItemAlignment));
		
		int paddingSum = (snaportas.size() - 1) * betweenPadding;
		int width = direction == RowDirection.HORIZONTAL ?
			snaportas.stream().mapToInt(Sized::getWidth).sum() + paddingSum :
			snaportas.stream().mapToInt(Sized::getWidth).max().orElseThrow();
		
		int height = direction == RowDirection.HORIZONTAL ?
			snaportas.stream().mapToInt(Sized::getHeight).max().orElseThrow() :
			snaportas.stream().mapToInt(Sized::getHeight).sum() + paddingSum;
		
		var combined = new LayeredSnaporta(width, height);
		int xOrY = 0;
		for(var snaporta : snaportas)
		{
			int x = direction == RowDirection.HORIZONTAL ? xOrY : rowItemAlignment.calculateOffset(snaporta.getWidth(), width);
			int y = direction == RowDirection.HORIZONTAL ? rowItemAlignment.calculateOffset(snaporta.getHeight(), height) : xOrY;
			
			combined.addLayerOnTop(snaporta, x, y);
			
			xOrY += direction == RowDirection.HORIZONTAL ? snaporta.getWidth() : snaporta.getHeight();
			xOrY += betweenPadding;
		}
		
		return combined;
	}
	
	
	// HELPER
	public enum RowDirection
	{
		
		HORIZONTAL,
		VERTICAL
		
	}
	
	@RequiredArgsConstructor
	public enum RowItemAlignment
	{
		
		CENTER(null),
		
		TOP(RowDirection.HORIZONTAL),
		BOTTOM(RowDirection.HORIZONTAL),
		
		LEFT(RowDirection.VERTICAL),
		RIGHT(RowDirection.VERTICAL);
		
		
		@Getter
		private final RowDirection rowDirection;
		
		
		public int calculateOffset(int itemSize, int spaceSize)
		{
			if(this == CENTER)
				return (spaceSize - itemSize) / 2;
			else if(this == TOP || this == LEFT)
				return 0;
			else if(this == BOTTOM || this == RIGHT)
				return spaceSize - itemSize;
			else
				throw new IncompleteCodeError();
		}
		
	}
	
}
