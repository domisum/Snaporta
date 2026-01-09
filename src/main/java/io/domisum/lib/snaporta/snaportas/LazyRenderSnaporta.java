package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import io.domisum.lib.snaporta.Snaporta;
import lombok.RequiredArgsConstructor;

@API
@RequiredArgsConstructor
public class LazyRenderSnaporta
	extends GeneratedSnaporta
{
	
	private final Snaporta base;
	private boolean rendered = false;
	
	
	@Override
	public String toString()
	{
		return PHR.r("{}(rendered={}\n{})", getClass().getSimpleName(),
			rendered, StringUtil.indent(base.toString(), "\t"));
	}
	
	@Override
	protected Snaporta generate()
	{
		var basic = BasicSnaporta.copyOf(base);
		rendered = true;
		return basic;
	}
	
}
