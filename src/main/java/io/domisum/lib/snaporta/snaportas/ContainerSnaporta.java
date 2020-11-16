package io.domisum.lib.snaporta.snaportas;

import com.google.common.base.Suppliers;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.ValidationUtil;
import io.domisum.lib.snaporta.Snaporta;

import java.util.function.Supplier;

@API
public class ContainerSnaporta
	implements Snaporta
{
	
	private final Supplier<Snaporta> containedSupplier;
	
	
	// INIT
	@API
	public ContainerSnaporta(Supplier<Snaporta> containedSupplier)
	{
		ValidationUtil.notNull(containedSupplier, "containedSupplier");
		
		// mismatched supplier types
		this.containedSupplier = Suppliers.memoize(()->containedSupplier.get());
	}
	
	
	// SNAPORTA
	@Override
	public int getWidth()
	{
		return containedSupplier.get().getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return containedSupplier.get().getHeight();
	}
	
	@Override
	public int getArgbAt(int x, int y)
	{
		return containedSupplier.get().getArgbAt(x, y);
	}
	
}
