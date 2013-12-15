package com.mrcrayfish.textcraft;

import java.util.ArrayList;

public enum CommandsStandard 
{	
	ATTACK(false, "attack", "attack"),
	BATTLE(true, "battle", "battle"),
	BUILD(false, "build house", "build house", "field"),
	CRAFT(false, "craft", "craft <item>", "house", "woods", "field", "cave"),
	DEPOSIT(false, "deposit", "deposit <item> <amount>",  "house"),
	DEVMODE(false, "devmode", "devmode", "house", "woods", "field", "cave"),
	EAT(false, "eat", "eat <item>", "house", "woods", "field", "cave"),
	EQUIP(false, "equip", "equip <armour>"),
	EXIT(false, "exit", "exit", "house", "woods", "field", "cave"),
	GATHER_FOOD(false, "gather food", "gather food", "woods", "field"),
	GIVE(true, "give", "give <item> <amount>"),
	GOTO(false, "goto", "goto <location>", "house", "woods", "field", "cave"),
	HELP(false, "help", "help", "house", "woods", "field", "cave"),
	HUNT(false, "hunt", "hunt", "woods", "field"),
	INVENTORY(false, "inventory", "inventory", "house", "woods", "field", "cave"),
	MINE(false, "mine", "mine", "cave"),
	PLACE(false, "place", "place <storgebox/furnace>", "house"),
	PUNCH_TREE(false, "punch tree", "punch tree", "woods"),
	REST(false, "rest", "rest", "house", "woods", "field", "cave"),
	RUN(false, "run", "run"),
	SMELT(false, "smelt", "smelt <item>", "house"),
	TIME_SET(true, "time set", "time set <hour>"),
	USE(false, "use", "use <item>"),
	WIELD(false, "wield", "wield <sword>"),
	WITHDRAW(false, "withdraw", "withdraw <item> <amount>", "house");
	
	String[] battleCommands = new String[]{"attack", "eat", "use", "wield", "inventory", "leave", "run", "devmode"};
	public ArrayList<String> validLocationsForCommand = new ArrayList<String>();
	public boolean isDevCommand;
	public boolean isBattleCommand;
	public String command;
	public String usage;

	CommandsStandard(boolean isDevCommand, String command, String usage, String ... locations)
	{
		this.isDevCommand = isDevCommand;
		this.command = command;
		this.usage = usage;
		for(int i = 0; i < battleCommands.length; i++)
		{
			if(battleCommands[i].equals(command))
			{
				isBattleCommand = true;
				break;
			}
			else
			{
				isBattleCommand = false;
			}
		}
		for(String key : locations)
		{
			validLocationsForCommand.add(key);
		}
	}
	
	public String getCommand()
	{
		return command;
	}

	public boolean isLocationValidForCommand(String location)
	{
		for(int i = 0; i < validLocationsForCommand.size(); i++)
		{
			if(location.equals(validLocationsForCommand.get(i)))
			{
				return true;
			}
		}
		return false;		
	}
	

}
