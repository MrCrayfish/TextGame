package com.mrcrayfish.textcraft;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PrintingManager implements Runnable
{
	private static PrintingManager printingManager = null;
	private List<StringData> stringsToPrint;
	private Game game;

	private PrintingManager(Game game)
	{
		stringsToPrint = new ArrayList<StringData>();
		this.game = game;
	}

	private Thread thread;

	public static PrintingManager getInstance()
	{
		return printingManager;
	}

	public static void initialise(Game game)
	{
		printingManager = new PrintingManager(game);
		printingManager.thread = new Thread(printingManager);
		printingManager.thread.start();
	}

	@Override
	public synchronized void run() 
	{
		while(true)
		{
			for(StringData data : stringsToPrint)
			{
				game.input.setEnabled(false);
				long currentTime = System.currentTimeMillis();
				boolean sent = false;

				while(!sent)
				{
					if(System.currentTimeMillis() > currentTime + (data.getDelay() * 1000))
					{
						game.printMessage(data.getMessage(), data.getColour());
						sent = true;
					}
				}
			}
			
			stringsToPrint.clear();
			
			game.input.setEnabled(true);
			game.input.requestFocus();

			try 
			{
				wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}

	}

	public synchronized void printString(String message, Color colour, int delay)
	{
		stringsToPrint.add(new StringData(message, colour, delay));
		notify();
	}

	public void shutdown() 
	{
		this.thread.interrupt();
	}

}
