package com.mrcrayfish.textcraft;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class GameGraphics 
{	
	BufferedImage bf;
	public ImageIcon scrollImage = new ImageIcon(getClass().getResource("resources/misc/scroll.png"));
	public ImageIcon timeImage = new ImageIcon(getClass().getResource("resources/misc/time.png"));
	public ImageIcon dayImage = new ImageIcon(getClass().getResource("resources/day.png"));
	public ImageIcon nightImage = new ImageIcon(getClass().getResource("resources/night.png"));
	public ImageIcon locationImage = new ImageIcon(getClass().getResource("resources/overlay/field.png"));
	public ImageIcon furnace = new ImageIcon(getClass().getResource("resources/overlay/storagebox.png"));
	public ImageIcon storageBox = new ImageIcon(getClass().getResource("resources/overlay/furnace.png"));
	public ImageIcon tree1 = new ImageIcon(getClass().getResource("resources/overlay/tree1.png"));
	public ImageIcon tree2 = new ImageIcon(getClass().getResource("resources/overlay/tree2.png"));
	public ImageIcon tree3 = new ImageIcon(getClass().getResource("resources/overlay/tree3.png"));
	public ImageIcon mobImage = new ImageIcon();

	private Game game;

	public GameGraphics(Game game)
	{
		this.game = game;
	}

	public void paint(Graphics g)
	{
		bf = new BufferedImage(505, 340, BufferedImage.TYPE_INT_RGB);
		Graphics bfg = bf.getGraphics();
		game.superPaint(bfg);
		drawScroll(bfg);
		drawLocation(bfg);
		drawStats(bfg);
		drawTime(bfg);
		drawMobBattle(bfg);
		drawHouse(bfg);
		drawWoods(bfg);
		g.drawImage(bf, 0, 0,null);	
	}
	
	public void drawScroll(Graphics g)
	{
		g.drawImage(scrollImage.getImage(), 362, 30, null);
		if(game.action.isDay())
		{
			g.drawImage(dayImage.getImage(), 384, 56, null);
		}
		else 
		{
			g.drawImage(nightImage.getImage(), 384, 56, null);
		}
	}

	public void drawLocation(Graphics g)
	{
		g.drawImage(locationImage.getImage(), 384, 56, null);		
	}

	public void drawStats(Graphics g)
	{
		g.setFont(new Font("Lucida Bright", Font.PLAIN, 12));
		
		//Health
		g.setColor(Color.RED);
		g.fillRect(384, 220, 100, 20);
		g.setColor(Color.GREEN);
		g.fillRect(384, 220, (int)(((double)game.var.currentHealth / game.var.maxHealth) * 100), 20);
		g.setColor(Color.BLACK);
		g.drawRect(384, 220, 100, 20);
		g.drawString("HP: " + game.var.currentHealth + "/" + game.var.maxHealth , 408, 235);

		//Energy
		g.setColor(Color.RED);
		g.fillRect(384, 250, 100, 20);
		g.setColor(new Color(0, 186, 228));
		g.fillRect(384, 250, (int)(((double)game.var.currentEnergy / game.var.maxEnergy) * 100), 20);
		g.setColor(Color.BLACK);
		g.drawRect(384, 250, 100, 20);
		g.drawString("EN: " + game.var.currentEnergy + "/" + game.var.maxEnergy , 408, 265);
	}

	public void drawTime(Graphics g)
	{
		g.drawImage(timeImage.getImage(), 382, 280, null);
		g.drawString(game.var.getTime(), 392, 305);
	}

	public void drawMobBattle(Graphics g)
	{
		if(game.mobBattle.mob != null)
		{
			double maxHealth = (double)game.mobBattle.mob.getMaxHealth();
			double currentHealth = (double)game.mobBattle.mob.health;
			g.setColor(Color.RED);
			g.fillRect(384, 160, 100, 20);
			g.setColor(Color.GREEN);
			g.fillRect(384, 160, (int)((currentHealth / maxHealth) * 100), 20);
			g.setColor(Color.BLACK);
			g.drawRect(384, 160, 100, 20);
			g.drawString("HP: " + (int)currentHealth + "/" + (int)maxHealth , 408, 175);
			g.drawImage(mobImage.getImage(), 394, 71, 80, 80, null);
		}
	}

	public void drawSlots(Graphics g)
	{

	}

	public void drawHouse(Graphics g)
	{
		if(game.var.location.equals("house"))
		{
			if(game.var.hasHouse)
			{
				if(game.var.house.hasStorageBox)
				{
					g.drawImage(storageBox.getImage(), 384, 56, null);
				}
				if(game.var.house.hasFurnace)
				{
					g.drawImage(furnace.getImage(), 384, 56, null);
				}
			}
		}
	}
	
	public void drawWoods(Graphics g)
	{
		if(game.var.location.equals("woods"))
		{
			if(game.var.treeCount == 1) g.drawImage(tree1.getImage(), 384, 56, null);
			else if(game.var.treeCount == 2) g.drawImage(tree2.getImage(), 384, 56, null);
			else if(game.var.treeCount == 3) g.drawImage(tree3.getImage(), 384, 56, null);
		}
	}

	public void changeLocationImage(String location)
	{
		game.invalidate();
		this.locationImage = new ImageIcon(getClass().getResource("resources/overlay/" + location + ".png"));
		game.repaint();
		game.validate();
	}

	public void changeMobImage(ImageIcon newImage)
	{
		game.invalidate();
		this.mobImage = newImage;
		game.repaint();
		game.validate();
	}

}
