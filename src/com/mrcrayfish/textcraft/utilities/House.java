package com.mrcrayfish.textcraft.utilities;

public class House
{
	private StorageBox storage;

	public boolean hasStorageBox;
	public boolean hasFurnace;
	public boolean hasBed;

	public boolean addStorageBox()
	{
		if(!hasStorageBox)
		{
			setStorage(new StorageBox());
			hasStorageBox = true;
			return true;
		}
		return false;
	}

	public boolean addFurnace()
	{
		if(!hasFurnace) 
		{
			hasFurnace = true;
			return true;
		}
		return false;
	}

	public boolean addBed()
	{
		if(!hasBed) 
		{
			hasBed = true;
			return true;
		}
		return false;
	}

	public void setStorage(StorageBox storage) 
	{
		this.storage = storage;
	}

	public StorageBox getStorage() 
	{
		return storage;
	}
}
