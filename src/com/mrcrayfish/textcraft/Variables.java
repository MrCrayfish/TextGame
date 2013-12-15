package com.mrcrayfish.textcraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mrcrayfish.textcraft.utilities.House;


public class Variables 
{
	private Game game;

	public Map<String, Integer> inventory = new HashMap<String, Integer>();
	public Map<String, Integer> foodRestoreAmountMap = new HashMap<String, Integer>();
	public ArrayList<String> validLocations = new ArrayList<String>();
	public House house;

	//Global
	public int timeHour = 6;
	public int timeMinute = 0;
	public int day = 0;
	public int maxHealth = 20;
	public int currentHealth = 20;
	public int maxEnergy = 20;
	public int currentEnergy = 20;
	public int treeCount = 3;
	public int treeGrowTime = 0;

	public boolean hasHouse = false;
	public boolean cheatsEnabled = false;
	
	public String location = "field";
	
	public Random rand = new Random();

	public Variables(Game game)
	{
		this.game = game;
		foodRestoreAmountMap.put("apple", 3);
		foodRestoreAmountMap.put("banana", 5);
		foodRestoreAmountMap.put("orange", 4);
		foodRestoreAmountMap.put("strawberry", 2);
		foodRestoreAmountMap.put("soup", 8);
	}

	public void addMaterial(String material, boolean randomAmount, int amount)
	{
		addMaterial(material, randomAmount, amount, true);
	}

	/**
	 * @param material Name of the item/material
	 * @param randomAmount If true, will get random quantity depending on amount param
	 * @param amount The quantity. If randomAmout is true, will use this as the range for random.
	 * @param messageOn If true, 
	 */
	public void addMaterial(String material, boolean randomAmount, int amount, boolean messageOn)
	{
		if(inventory.get(material.toLowerCase()) == null)
		{
			inventory.put(material.toLowerCase(), 0);
		}

		int chance = 0;
		if(randomAmount)
		{
			chance = rand.nextInt(amount);
			if(chance == 0) chance = 1;
		}
		else
		{
			chance = amount;
		}

		if(messageOn) game.printMessage("You obtain " + chance + " " + capitaliseFirstChar(material), 1, CommonColours.COMMON_LIGHT.getColour());

		int count = inventory.get(material.toLowerCase());
		count += chance;
		inventory.put(material.toLowerCase(), count);
	}
	
	public void addLocation(String location)
	{
		validLocations.add(location);
	}

	/**
	 * @param material Name of the item/material
	 * @param amount The quantity
	 */
	public boolean hasRequiredMaterialAndAmount(String material, int amount)
	{
		boolean hasMaterial = false;
		if(inventory.get(material) != null)
		{
			int currentAmount = inventory.get(material);
			if(currentAmount >= amount)
			{
				hasMaterial = true;
			}
		}
		return hasMaterial;
	}

	/**
	 * @param material Name of the item/material
	 * @param amount The quantity
	 */
	public void takeMaterial(String material, int amount)
	{
		if(inventory.get(material) != null)
		{
			int count = inventory.get(material);
			count -= amount;
			if(count <= 0)
			{
				inventory.remove(material);
			}
			else
			{
				inventory.put(material, count);
			}
		}
	}

	/**
	 * Prints all items/materials in the inventory
	 */
	public void printMaterialListAndAmount()
	{
		game.printMessage("\nInventory:", CommonColours.COMMON.getColour());
		if(inventory.size() > 0)
		{
			for(String key : inventory.keySet())
			{
				int count = inventory.get(key);
				game.printMessage(capitaliseFirstChar(key) + ": " + count, CommonColours.COMMON_LIGHT.getColour());
			}
		}
		else
		{
			game.printMessage("You have nothing", CommonColours.COMMON_LIGHT.getColour());
		}
	}

	public String capitaliseFirstChar(String string)
	{
		char first = Character.toUpperCase(string.charAt(0));
		String result = first + string.substring(1);
		return result;
	}

	/**
	 * Gets the time and returns as String
	 */
	public String getTime()
	{
		if(timeHour >= 12)
		{
			return "Time: " + (timeHour - 12) + ":" + timeMinute + "0pm";
		}
		else
		{
			return "Time: " + timeHour + ":" + timeMinute + "0am";
		}
	}

	/**
	 * Add half an hour onto the time
	 */
	public void addTimeEvent()
	{
		if(timeHour == 23 && timeMinute == 3)
		{
			timeHour = 0;
			timeMinute = 0;
		}
		else if(timeMinute == 3)
		{
			timeHour += 1;
			timeMinute = 0;
		}
		else if(timeMinute == 0)
		{
			timeMinute = 3;
		}
	}

	public void takeEnergy(int amount)
	{
		this.currentEnergy -= amount;
		if(this.currentEnergy < 0)
		{
			this.currentEnergy = 0;
		}
	}

	/**
	 * Checks if the player has a sword
	 */
	public boolean hasSword()
	{
		boolean hasSword = false;
		for(String key : inventory.keySet())
		{
			if(key.equalsIgnoreCase("sword")) hasSword = true;
		}
		return hasSword;
	}

	public void createHouse()
	{
		if(!hasHouse)
		{
			this.takeMaterial("wood", 64);
			this.hasHouse = true;
			this.house = new House();
			game.printMessage("House constructed!", CommonColours.HEAL.getColour());
		}
		else
		{
			game.printMessage("You already have a house.", CommonColours.ERROR.getColour());
		}
	}

}
