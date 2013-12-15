package com.mrcrayfish.textcraft.mob;

import java.util.Random;

import javax.swing.ImageIcon;

import com.mrcrayfish.textcraft.CommonColours;
import com.mrcrayfish.textcraft.Game;
import com.mrcrayfish.textcraft.SoundManager;
import com.mrcrayfish.textcraft.resources.*;

public class MobBattle {
	private Game game;
	
	public static Mob mob;
	final Random rand = new Random();

	public MobBattle(Game game)
	{
		System.out.println("Starting Mob Battle");
		this.game = game;
		game.isInBattle = true;
		game.input.setEditable(true);
		
		if(isDay())
		{
			mob = new FriendlyMob();
			String type = mob.getType();
			ImageIcon image = new ImageIcon(getClass().getResource("/com/mrcrayfish/textcraft/resources/mobs/" + type + ".png"));
			game.graphics.changeMobImage(image);
			game.printMessage("-- Battle Started --", CommonColours.INFO.getColour());
			game.printMessage("You stumble upon a " + type, 1, CommonColours.COMMON.getColour());
			game.printMessage("Attack, Run or Help?", 1, CommonColours.COMMON_LIGHT.getColour());
		}
		else
		{
			mob = new HostileMob();
			String type = mob.getType();
			ImageIcon image = new ImageIcon(getClass().getResource("/com/mrcrayfish/textcraft/resources/mobs/" + type + ".png"));
			game.graphics.changeMobImage(image);
			SoundManager.ZOMBIE.play();
			game.printMessage("A " + type + " appears!", CommonColours.INFO.getColour());
			game.printMessage("Attack, Run or Help?", 2, CommonColours.INFO.getColour());
		}
	}

	public void attack()
	{
		int damage = 0;
		if(game.var.hasSword())
		{
			damage = rand.nextInt(6);
			if(damage == 0) damage = 1;
		}
		else damage = rand.nextInt(3);		

		if(mob instanceof FriendlyMob)
		{
			mob.health -= damage;
			game.repaint();
			if(mob.health <= 0)
			{
				game.printMessage("You deal " + damage + " damage and kill the " + mob.getType(), 0, CommonColours.ATTACK.getColour());
				if(mob.getType().equals("Pig")) game.var.addMaterial("Bacon", true, 3);
				if(mob.getType().equals("Cow")) game.var.addMaterial("Beef", true, 3);
				if(mob.getType().equals("Sheep")) game.var.addMaterial("Wool", true, 3);
				game.printMessage("-- Battle Finished --", 2, CommonColours.INFO.getColour());
				game.var.addTimeEvent();
				game.isInBattle = false;
				game.mobBattle = null; //finishes the battle
				mob = null;
			}
			else
			{
				game.printMessage("You deal " + damage + " damage to the " + mob.getType(), CommonColours.ATTACK.getColour());
				game.printMessage(mob.getType() + " Health: " + mob.health, 1, CommonColours.COMMON.getColour());
			}
			game.repaint();
		}
		if(mob instanceof HostileMob)
		{
			mob.health -= damage;
			SoundManager.ZOMBIE_HURT.play();
			game.repaint();
			if(mob.health <= 0)
			{
				game.printMessage("You deal " + damage + " damage and kill the " + mob.getType(), CommonColours.ATTACK.getColour());
				game.var.addTimeEvent();
				game.isInBattle = false;
				game.mobBattle = null;
				mob = null;
			}
			else
			{
				game.printMessage("You deal " + damage + " damage to the " + mob.getType(), CommonColours.ATTACK.getColour());
				game.printMessage(mob.getType() + " Health: " + mob.health, 1, CommonColours.COMMON.getColour());
				game.printMessage("The " + mob.getType() + " attacks you back", 2, CommonColours.INFO.getColour());
				this.takeDamage(3);
			}
		}
	}

	public void eat(String foodName)
	{
		game.action.eat(foodName);
	}

	public void leave()
	{
		if(mob instanceof FriendlyMob)
		{
			game.printMessage("You leave the " + mob.getType() + " to roam freely.", CommonColours.COMMON.getColour());
			game.printMessage("-- Battle Finished --", 1, CommonColours.INFO.getColour());
			game.var.addTimeEvent();
			game.isInBattle = false;
			game.mobBattle = null;
			mob = null;
		}
		else
		{
			if(rand.nextInt(2) == 0)
			{
				game.printMessage("You ran away back to home.", CommonColours.COMMON.getColour());
				game.printMessage("-- Battle Finished --", 1, CommonColours.INFO.getColour());
				game.isInBattle = false;
				game.mobBattle = null;
				mob = null;
			}
			else
			{
				game.printMessage("You try to run away but you couldn't escape the " + mob.getType(), CommonColours.COMMON.getColour());
				this.takeDamage(1);
			}
		}
	}

	public void takeDamage(final int delay)
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
						game.action.takeDamage();
						game.input.setEditable(true);
						sent = true;
					}
				}			
			}
		};
		Thread thread = new Thread(run);
		thread.start();
	}

	public boolean isDay()
	{
		if(game.var.timeHour >= 6 && game.var.timeHour <= 18)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
