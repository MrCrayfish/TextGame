package com.mrcrayfish.textcraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.mrcrayfish.textcraft.utilities.House;

public class LoadGame 
{
	BufferedReader br;

	public LoadGame(Game game, String name) throws Exception
	{		
		File folder = new File(System.getenv("APPDATA") + "/textadventure/saves/");
		folder.mkdirs();
		String saveFile = name + ".sav";
		br = new BufferedReader(new FileReader(new File(folder, saveFile)));
		
		game.var.inventory = new HashMap<String, Integer>();
		
		String data = null;
		while((data = br.readLine()) != null)
		{
			data = Encrypter.decryptString(data);
			if(data.startsWith("location:"))
			{
				String locationData = data.replace("location:", "");
				game.var.location = locationData;
				game.graphics.changeLocationImage(locationData);
			}
			else if(data.startsWith("timeHour:"))
			{
				String timeData = data.replace("timeHour:", "");
				game.var.timeHour = Integer.parseInt(timeData);
			}
			else if(data.startsWith("timeMinute:"))
			{
				String timeData = data.replace("timeMinute:", "");
				game.var.timeMinute = Integer.parseInt(timeData);
			}
			else if(data.startsWith("maxHealth:"))
			{
				String healthData = data.replace("maxHealth:", "");
				game.var.maxHealth = Integer.parseInt(healthData);
			}
			else if(data.startsWith("currentHealth:"))
			{
				String healthData = data.replace("currentHealth:", "");
				game.var.currentHealth = Integer.parseInt(healthData);
			}
			else if(data.startsWith("maxEnergy:"))
			{
				String energyData = data.replace("maxEnergy:", "");
				game.var.maxEnergy = Integer.parseInt(energyData);
			}
			else if(data.startsWith("currentEnergy:"))
			{
				String energyData = data.replace("currentEnergy:", "");
				game.var.currentEnergy = Integer.parseInt(energyData);
			}
			else if(data.startsWith("treeCount:"))
			{
				String treeData = data.replace("treeCount:", "");
				game.var.treeCount = Integer.parseInt(treeData);
			}
			else if(data.startsWith("treeGrowTime:"))
			{
				String treeData = data.replace("treeGrowTime:", "");
				game.var.treeGrowTime = Integer.parseInt(treeData);
			}
			else if(data.startsWith("inventory:"))
			{
				String lineData = data.replace("inventory:", "");
				String[] itemData = lineData.split(":");
				String item = itemData[0];
				int amount = Integer.parseInt(itemData[1]);
				game.var.addMaterial(item, false, amount, false);
			}
			else if(data.startsWith("hasHouse:"))
			{
				String hasHouseData = data.replace("hasHouse:", "");
				boolean hasHouse = new Boolean(hasHouseData);
				game.var.hasHouse = hasHouse;
				if(hasHouse)
				{
					game.var.house = new House();
				}
			}
			else if(game.var.house != null)
			{
				if(data.startsWith("hasStorageBox:"))
				{
					String hasStorageBoxData = data.replace("hasStorageBox:", "");
					boolean hasStorageBox = new Boolean(hasStorageBoxData);
					if(hasStorageBox)
					{
						game.var.house.addStorageBox();
					}
				}
				else if(data.startsWith("hasFurnace:"))
				{
					String hasFurnaceData = data.replace("hasFurnace:", "");
					boolean hasFurnace = new Boolean(hasFurnaceData);
					if(hasFurnace)
					{
						game.var.house.addFurnace();
					}
				}
				else if(data.startsWith("hasBed:"))
				{
					String hasBedData = data.replace("hasBed:", "");
					boolean hasBed = new Boolean(hasBedData);
					if(hasBed)
					{
						game.var.house.addBed();
					}
				}
				else if(data.startsWith("contents:"))
				{
					if(game.var.house.getStorage() != null)
					{
						String lineData = data.replace("contents:", "");
						String[] itemData = lineData.split(":");
						String item = itemData[0];
						int amount = Integer.parseInt(itemData[1]);
						game.var.house.getStorage().addItem(item, amount);
					}
				}
			}
		}
		br.close();
		game.output.setText("");
		game.append("Loaded Save: " + name + ".dat", CommonColours.INFO.getColour());
	}
}
