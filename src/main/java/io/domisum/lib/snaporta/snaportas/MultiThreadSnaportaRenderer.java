package io.domisum.lib.snaporta.snaportas;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.snaporta.Snaporta;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@API
public class MultiThreadSnaportaRenderer
{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	// SETTINGS
	private final int numberOfThreads;
	
	// RENDERER
	private final ExecutorService executorService;
	
	
	// INIT
	@API
	public MultiThreadSnaportaRenderer()
	{
		this(Runtime.getRuntime().availableProcessors());
		logger.info("Creating {} with {} threads (number of available logical cores)",
				getClass().getSimpleName(), numberOfThreads);
	}
	
	@API
	public MultiThreadSnaportaRenderer(int numberOfThreads)
	{
		Validate.isTrue(numberOfThreads > 0, "Number of threads has to be greater than 0");
		
		this.numberOfThreads = numberOfThreads;
		executorService = Executors.newFixedThreadPool(numberOfThreads, r->
		{
			var thread = Executors.defaultThreadFactory().newThread(r);
			thread.setDaemon(true);
			return thread;
		});
	}
	
	
	// RENDER
	@API
	public Snaporta render(Snaporta snaporta)
	{
		int[][] argbPixels = new int[snaporta.getHeight()][snaporta.getWidth()];
		
		var futures = new HashSet<Future<?>>();
		int numberOfRows = snaporta.getHeight();
		int firstUncoveredRow = 0;
		for(int i = 0; i < numberOfThreads; i++)
		{
			int rowMinIncl = firstUncoveredRow;
			boolean lastThread = (i+1) == numberOfThreads;
			int rowMaxExcl = lastThread ? numberOfRows : ((i+1)*(numberOfRows/numberOfThreads));
			
			var future = executorService.submit(()->render(snaporta, argbPixels, rowMinIncl, rowMaxExcl));
			futures.add(future);
			
			firstUncoveredRow = rowMaxExcl;
		}
		
		try
		{
			for(var future : futures)
				future.get();
		}
		catch(InterruptedException|ExecutionException e)
		{
			logger.error("An error occured while rendering snaporta", e);
		}
		
		var basicSnaporta = new BasicSnaporta(argbPixels);
		return basicSnaporta;
	}
	
	private void render(Snaporta snaporta, int[][] argbPixels, int rowMinIncl, int rowMaxExcl)
	{
		for(int y = rowMinIncl; y < rowMaxExcl; y++)
			for(int x = 0; x < snaporta.getWidth(); x++)
				argbPixels[y][x] = snaporta.getARGBAt(x, y);
	}
	
}
