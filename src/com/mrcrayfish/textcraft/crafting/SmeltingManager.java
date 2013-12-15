package com.mrcrayfish.textcraft.crafting;

import java.util.HashMap;

import com.mrcrayfish.textcraft.Game;


public class SmeltingManager extends HashMap<String, String>
{
	private static final long serialVersionUID = 1L;
	private Game game;
	
	public SmeltingManager(Game game)
	{
		this.game = game;
		this.addSmelting("iron", "iron ingot");
	}

	public void addSmelting(String item, String resultItem)
	{
		this.put(item, resultItem);
	}

	public String getResult(String item)
	{
		return get(item);
	}
	
	public boolean canSmelt(String item)
	{
		if(get(item) != null) return true;
		return false;
	}
}
