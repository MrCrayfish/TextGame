package com.mrcrayfish.textcraft;

import java.util.Random;

import com.mrcrayfish.textcraft.crafting.CraftingManager;
import com.mrcrayfish.textcraft.mob.MobBattle;

public class Actions 
{
	private Game game;

	public Actions(Game game)
	{
		this.game = game;
	}

	public void cutTree()
	{
		if(game.var.treeCount > 0)
		{
			if(game.var.currentEnergy >= 2)
			{
				game.printMessage("You punch at the tree", 0, CommonColours.COMMON.getColour());
				game.var.takeEnergy(2);
				game.var.addMaterial("Wood", true, 3);
				game.var.addTimeEvent();
				game.var.treeCount -= 1;
				goTo("woods");
				this.spawnMobAtChance(10, 5);
			}
			else
			{
				game.printMessage("You do not have enough energy!", CommonColours.ATTACK.getColour());
			}
		}
		else
		{
			game.printMessage("There are not trees to cut. Come back later.", CommonColours.ATTACK.getColour());
		}
	}

	public void goMining()
	{
		game.printMessage("You swing your pickaxe at the stone.", CommonColours.COMMON.getColour());
		Random rand = new Random();
		if(rand.nextInt(50) == 0)
		{
			game.var.addMaterial("Diamond", true, 3);
		}
		else if(rand.nextInt(20) == 0)
		{
			game.var.addMaterial("Iron", true, 3);
		}
		else
		{
			game.var.addMaterial("Stone", true, 2);
		}
		game.var.takeEnergy(2);
		game.var.addTimeEvent();
	}

	public void craft(String item)
	{
		System.out.println("Item To Craft: " + item);
		CraftingManager crafting = new CraftingManager(game);
		crafting.getRecipe(item);
	}

	public void gatherFood() 
	{
		game.input.setEditable(false);
		game.printMessage("You set out to gather some food.", CommonColours.COMMON.getColour());
		Random rand = new Random();
		if(rand.nextInt(5) == 0) game.var.addMaterial("mushroom", true, 3);
		else if(rand.nextInt(4) == 0) game.var.addMaterial("apple", true, 3);
		else if(rand.nextInt(4) == 0) game.var.addMaterial("banana", true, 3);
		else if(rand.nextInt(3) == 0) game.var.addMaterial("orange", true, 3);
		else if(rand.nextInt(2) == 0) game.var.addMaterial("strawberry", true, 5);
		else game.printMessage("Unfortunely you come back with no food.", 1, CommonColours.COMMON_LIGHT.getColour());
		game.var.addTimeEvent();
		this.doActionAfterDelay("spawnMobAtChance", 1);
	}

	public void eat(String foodName)
	{
		String first = foodName.toLowerCase();
		String second = this.capitaliseFirstChar(first);
		if(game.var.hasRequiredMaterialAndAmount(second, 1))
		{
			if(game.var.foodRestoreAmountMap.get(first) != null)
			{
				int amount = game.var.foodRestoreAmountMap.get(first);
				if(game.var.currentHealth < game.var.maxHealth)
				{
					if(game.var.currentHealth + amount > game.var.maxHealth)
					{
						int amount2 = (game.var.maxHealth + amount) - (game.var.currentHealth + amount);
						game.var.currentHealth += amount2;
						game.printMessage("You gain " + amount2 + " health", CommonColours.HEAL.getColour());
					}
					else
					{
						game.var.currentHealth += amount;
						game.printMessage("You gain " + amount + " health", CommonColours.HEAL.getColour());
					}
					game.var.takeMaterial(second, 1);
				}
				else
				{
					game.printMessage("Your health is full already", CommonColours.HEAL.getColour());
				}
			}
			else
			{
				game.printMessage("You can't eat a " + second + " unless you are Bear Grills", CommonColours.COMMON.getColour());
			}
		}
		else
		{
			game.printMessage("You don't have a " + second, CommonColours.COMMON.getColour());
		}
	}
	
	public void hunt()
	{
		spawnMob();
	}

	public void goTo(String location)
	{
		for(int i = 0; i < game.var.validLocations.size(); i++)
		{
			if(location.equalsIgnoreCase((String)game.var.validLocations.get(i)))
			{
				if(location.equalsIgnoreCase("house"))
				{
					if(!game.var.hasHouse)
					{
						game.printMessage("You don't have a House yet.", CommonColours.ERROR.getColour());
						break;
					}
				}
				game.var.location = location;
				System.out.println(game.var.location);
				game.graphics.changeLocationImage(game.var.location);
				break;
			}
			if(i == game.var.validLocations.size() - 1)
			{
				game.printMessage("No such location as '" + location + "'", CommonColours.ERROR.getColour());
			}
		}
	}

	public void place(String object)
	{
		if(game.var.hasHouse)
		{
			if(object.equalsIgnoreCase("storagebox")) game.var.house.addStorageBox();
			if(object.equalsIgnoreCase("furnace")) game.var.house.addFurnace();
		}
	}

	public void deposit(String object)
	{
		System.out.println(object);
		if(object.contains(" "))
		{
			String[] data = object.split("\\s+");
			String item = data[0];
			int amount = Integer.parseInt(data[1]);
			if(game.var.hasRequiredMaterialAndAmount(item, amount))
			{
				game.var.takeMaterial(item, amount);
				game.var.house.getStorage().addItem(item, amount);
				game.printMessage("Deposited " + amount + " " + item + "'s into your storage box.", CommonColours.INFO.getColour());
			}
			else
			{
				game.printMessage("You don't have " + amount + " " + item + "'s", CommonColours.ERROR.getColour());
			}
		}
		else
		{
			if(game.var.hasRequiredMaterialAndAmount(object, 1))
			{
				game.var.takeMaterial(object, 1);
				game.var.house.getStorage().addItem(object, 1);
				game.printMessage("Deposited 1 " + object + " into your chest.", CommonColours.INFO.getColour());
			}
		}
	}

	public void withdraw(String object)
	{
		if(object.contains(" "))
		{
			String[] data = object.split("\\s+");
			String item = data[0];
			int amount = Integer.parseInt(data[1]);
			if(game.var.house.getStorage().hasRequiredMaterialAndAmount(item, amount))
			{
				game.var.house.getStorage().takeMaterial(item, amount);
				game.var.addMaterial(item, false, amount, false);
				game.printMessage("Withdrew " + amount + " " + item + "('s) into your inventory.", CommonColours.INFO.getColour());
			}
			else
			{
				game.printMessage("There is not " + amount + " " + item + "('s) in your storage box.", CommonColours.ERROR.getColour());
			}
		}
		else
		{
			if(game.var.house.getStorage().hasRequiredMaterialAndAmount(object, 1))
			{
				game.var.house.getStorage().takeMaterial(object, 1);
				game.var.addMaterial(object, false, 1, false);
				game.printMessage("Withdrew 1 " + object + " into your inventory.", CommonColours.INFO.getColour());
			}
			else
			{
				game.printMessage("There is not 1 " + object + " in your storage box.", CommonColours.ERROR.getColour());
			}
		}
	}

	public void smelt(String item)
	{
		if(game.var.location.equals("house"))
		{
			if(game.var.hasHouse)
			{
				if(game.var.house.hasFurnace)
				{
					if(game.var.hasRequiredMaterialAndAmount(item, 1))
					{
						if(game.furnace.canSmelt(item))
						{
							game.var.takeMaterial(item, 1);
							game.printMessage("Smelting " + item, CommonColours.INFO.getColour());
							String smeltingResult = game.furnace.get(item);
							game.var.addMaterial(smeltingResult, false, 1);
						}
					}
				}
			}
		}
	}

	public void rest()
	{
		if(game.var.currentEnergy != game.var.maxEnergy)
		{
			if(game.var.currentEnergy + 2 > game.var.maxEnergy)
			{
				int amount2 = (game.var.maxEnergy + 2) - (game.var.currentEnergy + 2);
				game.var.currentEnergy += amount2;
				game.printMessage("You take a rest and gain " + amount2 + " energy", CommonColours.HEAL.getColour());
			}
			else
			{
				game.var.currentEnergy += 2;
				game.printMessage("You take a rest and gain " + 2 + " energy", CommonColours.HEAL.getColour());
			}
			game.var.addTimeEvent();
		}
		else
		{
			game.printMessage("Your energy is full.", CommonColours.HEAL.getColour());
		}
	}

	public void sleep()
	{
		game.var.timeHour = 6;
		game.var.timeMinute = 0;
	}

	public void spawnMobAtChance(int dayChance, int nightChance)
	{
		Random rand = new Random();
		if(isDay())
		{
			if(rand.nextInt(dayChance) == 0)
			{
				spawnMob();
			}
			else
			{
				game.input.setEditable(true);
			}
		}
		else
		{
			if(rand.nextInt(nightChance) == 0)
			{
				spawnMob();
			}
			else
			{
				game.input.setEditable(true);
			}
		}
	}
	
	public void spawnMob()
	{
		game.mobBattle = new MobBattle(game);
	}

	public void takeDamage()
	{
		Random rand = new Random();
		int damage = rand.nextInt(4);
		game.var.currentHealth -= damage;
		game.printMessage("You take " + damage + " damage", CommonColours.ATTACK.getColour());
		System.out.println("Health: " + game.var.currentHealth);
	}

	public boolean isDay()
	{
		if(game.var.timeHour >= 6 && game.var.timeHour < 18)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void build()
	{
		if(game.var.hasRequiredMaterialAndAmount("wood", 64))
		{
			game.var.createHouse();
		}
		else
		{
			game.printMessage("You don't have enough Wood. You need 64.", CommonColours.ERROR.getColour());
		}
	}

	public void inventory()
	{
		game.var.printMaterialListAndAmount();
	}

	public String capitaliseFirstChar(String string)
	{
		char first = Character.toUpperCase(string.charAt(0));
		String result = first + string.substring(1);
		return result;
	}

	public void doActionAfterDelay(final String action, final int delay)
	{
		Runnable run = new Runnable()
		{
			@Override
			public void run() 
			{
				long currentTime = System.currentTimeMillis();
				boolean sent = false;

				while(!sent)
				{
					game.input.setEditable(false);
					if(System.currentTimeMillis() > currentTime + (delay * 1000))
					{
						if(action.equals("spawnMobAtChance"))
						{
							spawnMobAtChance(10, 5);
							sent = true;
						}
					}
				}
			}
		};
		Thread thread = new Thread(run);
		thread.start();
	}
}
