package com.mrcrayfish.textcraft;

import java.awt.Color;

public enum CommonColours 
{
	COMMON(56, 42, 25),
	COMMON_LIGHT(91, 68, 41),
	ATTACK(155, 0, 0),
	HEAL(0, 143, 14),
	OBTAIN(0, 81, 0),
	INFO(255, 85, 0),
	ERROR(255, 0, 0);
	
	private Color colour;
	
	CommonColours(int r, int g, int b)
	{
		colour = new Color(r, g, b);
	}
	
	public Color getColour()
	{
		return colour;
	}
	
}
