package com.mrcrayfish.textcraft;

import java.awt.Color;

public class StringData 
{
	private String stringToPrint;
	private Color colourForString;
	private int delayBeforePrint;
	
	public StringData(String message, Color colour, int delay)
	{
		this.stringToPrint = message;
		this.colourForString = colour;
		this.delayBeforePrint = delay;
	}

	public String getMessage()
	{
		return stringToPrint;
	}
	
	public Color getColour()
	{
		return colourForString;
	}
	
	public int getDelay()
	{
		return delayBeforePrint;
	}	
}
