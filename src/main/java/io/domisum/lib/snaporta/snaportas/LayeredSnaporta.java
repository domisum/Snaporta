package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.StringListUtil;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import io.domisum.lib.snaporta.Snaporta;
import io.domisum.lib.snaporta.color.Color;
import io.domisum.lib.snaporta.color.ColorComponent;
import io.domisum.lib.snaporta.snaportas.color.BlankSnaporta;
import io.domisum.lib.snaporta.snaportas.transform.ViewportSnaporta;
import io.domisum.lib.snaporta.util.ArgbUtil;
import io.domisum.lib.snaporta.util.Sized;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@API
@RequiredArgsConstructor
public class LayeredSnaporta
	implements Snaporta
{
	
	// IMMUTABLE
	@Getter
	private final int width;
	@Getter
	private final int height;
	
	// MUTABLE
	private final List<Snaporta> layersBottomUp = new ArrayList<>();
	private Snaporta rendered = null;
	
	
	// HOUSEKEEPING
	public LayeredSnaporta(List<Snaporta> layersBottomUp)
	{
		this(getDimensions(layersBottomUp).getWidth(), getDimensions(layersBottomUp).getHeight());
		for(var component : layersBottomUp)
			if(component != null)
				addLayerOnTop(component);
	}
	
	public LayeredSnaporta(Snaporta... layersBottomUp)
	{
		this(Arrays.asList(layersBottomUp));
	}
	
	private static Sized getDimensions(List<Snaporta> layersBottomUp)
	{
		if(layersBottomUp.isEmpty())
			throw new IllegalArgumentException("Can't determine dimensions of layered snaporta "
				+ "when empty list of layers is given");
		
		int maxWidth = -1;
		int maxHeight = -1;
		
		for(var snaporta : layersBottomUp)
			if(snaporta != null)
			{
				maxWidth = Math.max(maxWidth, snaporta.getWidth());
				maxHeight = Math.max(maxHeight, snaporta.getHeight());
			}
		
		return Sized.sized(maxWidth, maxHeight);
	}
	
	@Override
	public String toString()
	{
		var layersDisplay = new ArrayList<>(layersBottomUp);
		Collections.reverse(layersDisplay);
		
		String inner = layersDisplay.isEmpty() ? " <empty>"
			: "\n" + StringUtil.indent(StringListUtil.list(layersDisplay, "\n"), "\t");
		return PHR.r("{}(w={} x h={}{})", getClass().getSimpleName(),
			width, height, inner);
	}
	
	private void ensureStillMutable()
	{
		if(rendered != null)
			throw new IllegalStateException("Can't modify layered snaporta after accessing image data");
	}
	
	private void ensureRendered()
	{
		if(rendered == null)
			rendered = render();
	}
	
	
	// GETTERS
	@API
	public int getNumberOfLayers()
	{
		return layersBottomUp.size();
	}
	
	@API
	public List<Snaporta> getLayersBottomUp()
	{
		return new ArrayList<>(layersBottomUp);
	}
	
	
	// LAYERS
	@API
	public void addLayerOnTop(Snaporta snaporta)
	{
		ensureStillMutable();
		layersBottomUp.add(snaporta);
	}
	
	@API
	public void addLayerOnTop(Snaporta snaporta, int x, int y)
	{
		addLayerOnTop(ViewportSnaporta.offset(snaporta, x, y));
	}
	
	@API
	public void addLayerBelow(Snaporta snaporta)
	{
		ensureStillMutable();
		layersBottomUp.add(0, snaporta);
	}
	
	@API
	public void addLayerBelow(Snaporta snaporta, int x, int y)
	{
		addLayerBelow(ViewportSnaporta.offset(snaporta, x, y));
	}
	
	
	// SNAPORTA
	@Override
	public int getArgbAt(int x, int y)
	{
		ensureRendered();
		return rendered.getArgbAt(x, y);
	}
	
	@Override
	public BlankState isBlank()
	{
		if(layersBottomUp.isEmpty())
			return BlankState.BLANK;
		return BlankState.UNKNOWN;
	}
	
	@Override
	public Snaporta optimize()
	{
		var newLs = new LayeredSnaporta(width, height);
		for(var l : layersBottomUp)
			for(var nl : optimizeLayer(l))
				newLs.addLayerOnTop(nl);
		
		if(newLs.layersBottomUp.isEmpty())
			return new BlankSnaporta(width, height);
		return newLs;
	}
	
	public List<Snaporta> optimizeLayer(Snaporta layer)
	{
		layer = layer.optimize();
		
		if(layer instanceof LayeredSnaporta laySnap)
			return laySnap.layersBottomUp;
		if(layer.isBlank() == BlankState.BLANK)
			return Collections.emptyList();
		
		return List.of(layer);
	}
	
	protected Snaporta render()
	{
		int[][] argbPixels = new int[getHeight()][getWidth()];
		for(var l : layersBottomUp)
			if(l instanceof ViewportSnaporta vs)
				renderViewportLayer(vs, argbPixels);
			else
				renderLayer(l, argbPixels);
		
		return new BasicSnaporta(argbPixels);
	}
	
	private void renderViewportLayer(ViewportSnaporta vs, int[][] argbPixels)
	{
		var topLeft = vs.getPositionOffset();
		var bottomRight = topLeft.deriveAdd(vs.getWindowSize());
		var inBaseOffset = vs.getInternalOffset().deriveSubtract(vs.getPositionOffset());
		
		int yBoundLowerIncl = MathUtil.clamp(0, height, topLeft.getY());
		int yBoundUpperExcl = MathUtil.clamp(0, height, bottomRight.getY());
		int xBoundLowerIncl = MathUtil.clamp(0, width, topLeft.getX());
		int xBoundUpperExcl = MathUtil.clamp(0, width, bottomRight.getX());
		for(int y = yBoundLowerIncl; y < yBoundUpperExcl; y++)
			for(int x = xBoundLowerIncl; x < xBoundUpperExcl; x++)
			{
				int inBaseX = x + inBaseOffset.getX();
				int inBaseY = y + inBaseOffset.getY();
				
				int layerColor = vs.getBase().getArgbAt(inBaseX, inBaseY);
				argbPixels[y][x] = mixArgb(argbPixels[y][x], layerColor);
			}
	}
	
	private void renderLayer(Snaporta l, int[][] argbPixels)
	{
		int yBound = Math.min(height, l.getHeight());
		int xBound = Math.min(width, l.getWidth());
		for(int y = 0; y < yBound; y++)
			for(int x = 0; x < xBound; x++)
				argbPixels[y][x] = mixArgb(argbPixels[y][x], l.getArgbAt(x, y));
	}
	
	
	// COLOR MIXING
	public static int mixArgb(int backgroundArgb, int foregroundArgb)
	{
		if(ArgbUtil.getAlphaComponent(backgroundArgb) == Color.ALPHA_TRANSPARENT)
			return foregroundArgb;
		
		int redCombined = getColorComponentCombined(ColorComponent.RED, backgroundArgb, foregroundArgb);
		int greenCombined = getColorComponentCombined(ColorComponent.GREEN, backgroundArgb, foregroundArgb);
		int blueCombined = getColorComponentCombined(ColorComponent.BLUE, backgroundArgb, foregroundArgb);
		
		double foregroundTransparency = 1 - ArgbUtil.getOpacity(foregroundArgb);
		double backgroundTransparency = 1 - ArgbUtil.getOpacity(backgroundArgb);
		double transparencyCombined = foregroundTransparency * backgroundTransparency;
		int alphaCombined = ArgbUtil.getAlphaFromOpacity(1 - transparencyCombined);
		
		return ArgbUtil.toArgb(alphaCombined, redCombined, greenCombined, blueCombined);
	}
	
	private static int getColorComponentCombined(ColorComponent colorComponent, int background, int foreground)
	{
		int foregroundComponent = ArgbUtil.getComponent(colorComponent, foreground);
		double foregroundOpacity = ArgbUtil.getOpacity(foreground);
		
		int backgroundComponent = ArgbUtil.getComponent(colorComponent, background);
		double backgroundOpacity = ArgbUtil.getOpacity(background);
		
		double mixed = MathUtil.mix(
			foregroundComponent, foregroundOpacity,
			backgroundComponent, (1 - foregroundOpacity) * backgroundOpacity);
		return (int) Math.round(mixed);
	}
	
}
