package io.domisum.lib.snaporta.snaportas;

import com.google.common.base.Suppliers;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;

import java.util.function.Supplier;

@API
public abstract class GeneratedSnaporta
	implements Snaporta
{
	
	private final Supplier<Snaporta> generateOnce;
	private boolean generated = false;
	
	
	// INIT
	@API
	public GeneratedSnaporta() {generateOnce = Suppliers.memoize(this::gen);}
	
	private Snaporta gen()
	{
		var snaporta = generate();
		generated = true;
		return snaporta;
	}
	
	protected abstract Snaporta generate();
	
	
	// SNAPORTA
	@Override
	public int getWidth() {return get().getWidth();}
	
	@Override
	public int getHeight() {return get().getHeight();}
	
	@Override
	public int getArgbAt(int x, int y) {return get().getArgbAt(x, y);}
	
	@Override
	public BlankState isBlank()
	{
		if(!generated)
			return BlankState.UNKNOWN;
		return get().isBlank();
	}
	
	@Override
	public Snaporta optimize() {return get().optimize();}
	
	
	// UTIL
	private Snaporta get() {return generateOnce.get();}
	
}
