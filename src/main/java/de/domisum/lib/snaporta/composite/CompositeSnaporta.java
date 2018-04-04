package de.domisum.lib.snaporta.composite;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.lib.snaporta.Snaporta;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.util.SortedSet;
import java.util.TreeSet;

@RequiredArgsConstructor
@API
public class CompositeSnaporta implements Snaporta
{

	// ATTRIBUTES
	@Getter private final int width;
	@Getter private final int height;

	// COMPONENTS
	private final SortedSet<CompositeSnaportaComponent> componentsByZ = new TreeSet<>();


	// COMPONENTS
	@API public void addComponent(CompositeSnaportaComponent component)
	{
		boolean uniqueZ = componentsByZ.stream().noneMatch(c->c.getZ() == component.getZ());
		Validate.isTrue(uniqueZ, "component z values have to be unique, already have component with z="+component.getZ());

		componentsByZ.add(component);
	}


	// SNAPORTA
	@Override public int getARGBAt(int x, int y)
	{
		throw new UnsupportedOperationException("not yet implemented");
	}

}
