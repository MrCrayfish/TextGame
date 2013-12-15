package com.mrcrayfish.textcraft.mob;
import java.util.Random;


public class FriendlyMob extends Mob
{
	public String[] name = {"Cow", "Pig", "Sheep"};
	public String type;
	public Random rand = new Random();

	public FriendlyMob()
	{
		this.type = name[rand.nextInt(3)];
		this.health = 10;
	}

	public String getType()
	{
		return this.type;
	}

	@Override
	public int getMaxHealth() 
	{
		return 10;
	}
}
