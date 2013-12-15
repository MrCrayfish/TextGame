package com.mrcrayfish.textcraft;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class History extends KeyAdapter
{
	private String[] history = new String[10];
	private Game game;
	public int count = 0;

	public History(Game game)
	{
		this.game = game;
	}

	public void addToHistory(String command)
	{
		System.out.println(command);
		boolean canAddToHistory = true;
		if(history[0] != null)
		{
			if(history[0].equals(command))
			{
				canAddToHistory = false;
			}
		}
		if(canAddToHistory)
		{
			for(int i = 8; i > 0; i--)
			{
				if(history[i - 1] != null)
				{
					history[i] = new String(history[i - 1]);
				}
			}
			history[0] = command;
		}
	}

	public void keyPressed(KeyEvent evt) 
	{
		if(evt.getSource() == game.input)
		{
			System.out.println(evt.getKeyCode());
			if(evt.getKeyCode() == KeyEvent.VK_UP)
			{
				count++;
				if(count > getMaxCount())
				{
					count = getMaxCount();
				}
				game.input.setText(history[count]);
			}
			if(evt.getKeyCode() == KeyEvent.VK_DOWN)
			{
				count--;
				if(count < 0)
				{
					count = -1;
					game.input.setText("");
				}
				else
				{
					game.input.setText(history[count]);
				}
			}
		}
	}

	public int getMaxCount()
	{
		int maxCount = 0;
		for(int i = 0; i < history.length; i++)
		{
			if(history[i] != null)
			{
				maxCount = i;
			}
		}
		return maxCount;
	}
}
