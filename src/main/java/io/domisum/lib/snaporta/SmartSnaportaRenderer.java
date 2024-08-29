package io.domisum.lib.snaporta;

import io.domisum.lib.auxiliumlib.time.RunStopwatch;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.auxiliumlib.util.math.RandomUtil;
import io.domisum.lib.snaporta.snaportas.BasicSnaporta;

import java.time.Duration;
import java.util.*;

public class SmartSnaportaRenderer
{
	
	// CONSTANTS
	private static final int MAX_HISTORY_LENGTH = 20;
	
	// DEPENDENCIES
	private final List<SnaportaRenderer> renderers;
	
	// STATUS
	private final Map<String, List<Deque<Duration>>> timings = new HashMap<>();
	
	
	// INIT
	@API
	public SmartSnaportaRenderer(SnaportaRenderer... renderers)
	{
		this(Arrays.asList(renderers));
	}
	
	@API
	public SmartSnaportaRenderer(List<SnaportaRenderer> renderers)
	{
		this.renderers = new ArrayList<>();
		this.renderers.add(BasicSnaporta::copyOf);
		this.renderers.addAll(renderers);
	}
	
	
	// INTERFACE
	@API
	public Snaporta render(Snaporta snaporta, String jobTitle)
	{
		int rendererIndex = selectRenderer(jobTitle);
		var renderer = renderers.get(rendererIndex);
		
		var timedResult = RunStopwatch.run(() -> renderer.render(snaporta));
		putTiming(jobTitle, rendererIndex, timedResult.getDuration());
		
		return timedResult.get();
	}
	
	
	// INTERNAL
	private synchronized int selectRenderer(String jobTitle)
	{
		if(RandomUtil.getByChance(0.1))
			return selectRandomRenderer();
		
		return selectBestRenderer(jobTitle);
	}
	
	private int selectBestRenderer(String jobTitle)
	{
		var histories = getHistories(jobTitle);
		
		int bestIndex = -1;
		double bestTime = Double.MAX_VALUE;
		for(int i = 0; i < histories.size(); i++)
		{
			double avgMs = histories.get(i).stream()
				.mapToLong(Duration::toMillis)
				.average()
				.orElse(0);
			if(avgMs < bestTime)
			{
				bestIndex = i;
				bestTime = avgMs;
			}
		}
		return bestIndex;
	}
	
	private int selectRandomRenderer()
	{
		return RandomUtil.nextInt(renderers.size());
	}
	
	
	private synchronized void putTiming(String jobTitle, int rendererIndex, Duration duration)
	{
		var histories = getHistories(jobTitle);
		var history = histories.get(rendererIndex);
		
		history.add(duration);
		while(history.size() > MAX_HISTORY_LENGTH)
			history.removeFirst();
	}
	
	
	private List<Deque<Duration>> getHistories(String jobTitle)
	{
		return timings.computeIfAbsent(jobTitle, s ->
		{
			var list = new ArrayList<Deque<Duration>>();
			for(var ignored : renderers)
				list.add(new LinkedList<>());
			return list;
		});
	}
	
}
