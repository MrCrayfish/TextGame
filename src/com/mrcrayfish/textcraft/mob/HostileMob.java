package com.mrcrayfish.textcraft.mob;

import java.util.Random;


public class HostileMob extends Mob
{
	public String[] name = {"Zombie", "Ghost", "Troll", "Gremlin", "Witch"};
	public String type;
	public Random rand = new Random();
	
	public HostileMob()
	{
		this.type = name[rand.nextInt(1)];
		this.health = 20;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	@Override
	public int getMaxHealth() 
	{
		return 20;
	}
}

