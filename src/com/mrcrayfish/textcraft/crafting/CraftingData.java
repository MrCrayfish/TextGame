package com.mrcrayfish.textcraft.crafting;

public class CraftingData
{	
	private String itemName;
	private int quantity;
	
	public CraftingData(String itemName, int quantity)
	{
		this.itemName = itemName;
		this.quantity = quantity;
	}
	
	public Object getFirst()
	{
		return itemName;
	}
	public Object getSecond()
	{
		return quantity;
	}
}
