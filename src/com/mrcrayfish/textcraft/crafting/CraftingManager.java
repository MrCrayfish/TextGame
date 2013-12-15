package com.mrcrayfish.textcraft.crafting;
import java.util.ArrayList;
import java.util.HashMap;

import com.mrcrayfish.textcraft.CommonColours;
import com.mrcrayfish.textcraft.Game;

@SuppressWarnings("rawtypes")
public class CraftingManager extends HashMap<String, ArrayList>
{
	private static final long serialVersionUID = 1L;
	private Game game;

	public CraftingManager(Game game)
	{
		this.game = game;
		this.addRecipe("stick", new CraftingData[]{new CraftingData("wood", 2)});
		this.addRecipe("pickaxe", new CraftingData[]{new CraftingData("stick", 2),new CraftingData("stone", 3)});
		this.addRecipe("sword", new CraftingData[]{new CraftingData("stick", 1),new CraftingData("stone", 2)});
		this.addRecipe("axe", new CraftingData[]{new CraftingData("stick", 2),new CraftingData("stone", 3)});
		this.addRecipe("bowl", new CraftingData[]{new CraftingData("wood", 3)});
		this.addRecipe("soup", new CraftingData[]{new CraftingData("bowl", 1), new CraftingData("mushroom", 1)});
		this.addRecipe("bed", new CraftingData[]{new CraftingData("wool", 3), new CraftingData("wood", 3)});
	}

	@SuppressWarnings("unchecked")
	public void addRecipe(String item, CraftingData[] extraObjects)
	{
		ArrayList materialList = new ArrayList();
		for(int x = 0; x < extraObjects.length; x++)
		{
			materialList.add(extraObjects[x]);
		}
		this.put(item, materialList);
	}

	public void getRecipe(String item)
	{
		@SuppressWarnings("unchecked")
		ArrayList<CraftingData> materialList = this.get(item.toLowerCase());
		if(materialList != null)
		{
			int count = 0;
			for(int x = 0; x < materialList.size(); x++)
			{
				CraftingData objects = materialList.get(x);
				String material = (String)objects.getFirst();
				int amount = (Integer)objects.getSecond();

				if(game.var.hasRequiredMaterialAndAmount(material, amount))
				{
					count++;
					if(count == materialList.size())
					{
						game.printMessage("You craft the materials together.", CommonColours.COMMON.getColour());
						game.var.addMaterial(this.capitaliseFirstChar(item), false, 1);
						takeRecipeMaterials(materialList);
					}			
				}
				else
				{
					game.printMessage("You don't have enough " + material + ". You need " + amount, CommonColours.INFO.getColour());
				}
			}
		}
		else
		{
			game.printMessage("No such item: " + item, CommonColours.ATTACK.getColour());
		}
	}

	public void takeRecipeMaterials(ArrayList<CraftingData> extraObjects)
	{
		for(int x = 0; x < extraObjects.size(); x++)
		{
			String material = (String) extraObjects.get(x).getFirst();
			int amount = (Integer)extraObjects.get(x).getSecond();
			game.var.takeMaterial(material, amount);
		}
	}
	
	public String capitaliseFirstChar(String string)
	{
		char first = Character.toUpperCase(string.charAt(0));
		String result = first + string.substring(1);
		return result;
	}
}
