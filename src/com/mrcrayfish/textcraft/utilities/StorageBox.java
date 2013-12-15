package com.mrcrayfish.textcraft.utilities;

import java.util.HashMap;
import java.util.Map;

public class StorageBox 
{
	public Map<String, Integer> contents = new HashMap<String, Integer>();

	public void addItem(String material, int amount)
	{
		if(contents.get(material.toLowerCase()) == null)
		{
			contents.put(material.toLowerCase(), 0);
		}

		int count = contents.get(material.toLowerCase());
		count += amount;
		contents.put(material.toLowerCase(), count);
	}

	public void takeMaterial(String material, int amount)
	{
		if(contents.get(material) != null)
		{
			int count = contents.get(material);
			count -= amount;
			if(count <= 0)
			{
				contents.remove(material);
			}
			else
			{
				contents.put(material, count);
			}
		}
	}

	public boolean hasRequiredMaterialAndAmount(String material, int amount)
	{
		boolean hasMaterial = false;
		if(contents.get(material) != null)
		{
			int currentAmount = contents.get(material);
			if(currentAmount >= amount)
			{
				hasMaterial = true;
			}
		}
		return hasMaterial;
	}
}
