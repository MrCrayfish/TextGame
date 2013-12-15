package com.mrcrayfish.textcraft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGame 
{
	BufferedWriter br;

	public SaveGame(Game game, String name) throws Exception
	{
		File folder = new File(System.getenv("APPDATA") + "/textadventure/saves/");
		folder.mkdirs();
		String saveFile = name + ".sav";
		br = new BufferedWriter(new FileWriter(new File(folder, saveFile)));
		br.write(Encrypter.encryptString("location:" + game.var.location));
		br.newLine();
		br.write(Encrypter.encryptString("timeHour:" + Integer.toString(game.var.timeHour))); 
		br.newLine();
		br.write(Encrypter.encryptString("timeMinute:" + Integer.toString(game.var.timeMinute))); 
		br.newLine();
		br.write(Encrypter.encryptString("maxHealth:" + Integer.toString(game.var.maxHealth))); 
		br.newLine();
		br.write(Encrypter.encryptString("currentHealth:" + Integer.toString(game.var.currentHealth))); 
		br.newLine();
		br.write(Encrypter.encryptString("maxEnergy:" + Integer.toString(game.var.maxEnergy))); 
		br.newLine();
		br.write(Encrypter.encryptString("currentEnergy:" + Integer.toString(game.var.currentEnergy)));
		br.newLine();
		br.write(Encrypter.encryptString("treeCount:" + Integer.toString(game.var.treeCount)));
		br.newLine();
		br.write(Encrypter.encryptString("treeGrowTime:" + Integer.toString(game.var.treeGrowTime)));

		for(String key : game.var.inventory.keySet())
		{
			br.newLine();
			int amount = game.var.inventory.get(key);
			br.write(Encrypter.encryptString("inventory:" + key + ":" + Integer.toString(amount)));
		}

		br.newLine();
		br.write(Encrypter.encryptString("hasHouse:" + new Boolean(game.var.hasHouse).toString()));
		if(game.var.hasHouse)
		{
			br.newLine();
			br.write(Encrypter.encryptString("hasStorageBox:" + new Boolean(game.var.house.hasStorageBox).toString()));
			br.newLine();
			br.write(Encrypter.encryptString("hasFurnace:" + new Boolean(game.var.house.hasFurnace).toString()));
			br.newLine();
			br.write(Encrypter.encryptString("hasBed:" + new Boolean(game.var.house.hasBed).toString()));

			if(game.var.house.hasStorageBox)
			{
				for(String key : game.var.house.getStorage().contents.keySet())
				{
					br.newLine();
					int amount = game.var.house.getStorage().contents.get(key);
					br.write(Encrypter.encryptString("contents:" + key + ":" + Integer.toString(amount)));
				}
			}
		}


		br.close();
	}
}
