package com.mrcrayfish.textcraft;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import com.mrcrayfish.textcraft.mob.MobBattle;

public class ActionHandler implements ActionListener
{
	ArrayList<CommandsStandard> commandListStandard = new ArrayList<CommandsStandard>(Arrays.asList(CommandsStandard.values()));
	private Game game;
	public static Actions action;

	public ActionHandler(Game game)
	{
		this.game = game;
		this.action = game.action;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(game.input.isEditable())
		{
			if(!game.isInBattle)
			{
				if(e.getSource() == game.input)
				{
					String option = game.input.getText().toLowerCase();

					boolean isCommandValidForLocation = isCommandValidForLocation(option);

					if(isCommandValidForLocation)
					{
						if(option.equalsIgnoreCase("punch tree"))
						{
							action.cutTree();
							updateTrees();
							resetInput();
						}
						else if(option.equalsIgnoreCase("mine"))
						{
							action.goMining();
							updateTrees();
							resetInput();
						}
						else if(option.equalsIgnoreCase("gather food"))
						{
							action.gatherFood();
							updateTrees();
							resetInput();
						}
						else if(option.equalsIgnoreCase("hunt"))
						{
							action.hunt();
							updateTrees();
							resetInput();
						}
						else if(option.equalsIgnoreCase("rest"))
						{
							action.rest();
							updateTrees();
							resetInput();
						}
						else if(option.equalsIgnoreCase("inventory"))
						{
							game.input.setEditable(false);
							action.inventory();
							resetInput();
						}
						else if(option.startsWith("craft"))
						{
							game.input.setEditable(false);
							String[] data = option.split(" ");
							String item = getItem(data, 0);
							if(!item.isEmpty())
							{
								action.craft(item);
							}
							else
							{
								game.printMessage("Usage: " + CommandsStandard.CRAFT.usage, CommonColours.INFO.getColour());
							}
							resetInput();
						}
						else if(option.equalsIgnoreCase("build house"))
						{
							game.input.setEditable(false);
							action.build();
							updateTrees();
							resetInput();
						}
						else if(option.startsWith("eat"))
						{
							String[] data = option.split(" ");
							String foodName = getItem(data, 0);
							if(!foodName.isEmpty())
							{
								action.eat(foodName);
							}
							else
							{
								game.printMessage("Usage: " + CommandsStandard.EAT.usage, CommonColours.INFO.getColour());
							}
							resetInput();
						}
						else if(option.startsWith("goto"))
						{
							String location = option.replace("goto", "");
							location = location.replace(" ", "");
							if(!location.isEmpty())
							{
								action.goTo(location);
							}
							else
							{
								game.printMessage("Usage: goto [location]", CommonColours.INFO.getColour());
							}
							resetInput();
						}
						else if(option.startsWith("place"))
						{
							if(game.var.location.equals("house"))
							{
								String objectToPlace = option.replace("place", "");
								objectToPlace = objectToPlace.replace(" ", "");
								if(!objectToPlace.isEmpty())
								{
									action.place(objectToPlace);
								}
								else
								{
									game.printMessage("Usage: place [storagebox/furnace]", CommonColours.INFO.getColour());
								}
							}
							else
							{
								game.printMessage("You can't do that here.", CommonColours.INFO.getColour());
							}
							resetInput();
						}
						else if(option.startsWith("deposit"))
						{
							if(game.var.location.equals("house"))
							{
								if(game.var.hasHouse)
								{
									if(game.var.house.hasStorageBox)
									{
										String objectToStore = option.replace("deposit", "");
										objectToStore = objectToStore.substring(1);
										if(!objectToStore.isEmpty())
										{
											action.deposit(objectToStore);
										}
										else
										{
											game.printMessage("Usage: deposit [item]", CommonColours.INFO.getColour());
										}
									}
									else
									{
										game.printMessage("You don't have a storage box.", CommonColours.ERROR.getColour());
									}
								}
							}
							else
							{
								game.printMessage("You can't do that here.", CommonColours.INFO.getColour());
							}
							resetInput();
						}
						else if(option.startsWith("withdraw"))
						{
							if(game.var.location.equals("house"))
							{
								if(game.var.hasHouse)
								{
									if(game.var.house.hasStorageBox)
									{
										String objectToTake = option.replace("withdraw", "");
										objectToTake = objectToTake.substring(1);
										if(!objectToTake.isEmpty())
										{
											action.withdraw(objectToTake);
										}
										else
										{
											game.printMessage("Usage: withdraw [item]", CommonColours.INFO.getColour());
										}
									}
									else
									{
										game.printMessage("You don't have a storage box.", CommonColours.ERROR.getColour());
									}
								}
							}
							else
							{
								game.printMessage("You can't do that here.", CommonColours.INFO.getColour());
							}
							resetInput();
						}
						else if(option.startsWith("smelt"))
						{
							String itemToSmelt = option.replace("smelt", "");
							itemToSmelt = itemToSmelt.replace(" ", "");
							if(!itemToSmelt.isEmpty())
							{
								action.smelt(itemToSmelt);
								updateTrees();
							}
							else
							{
								game.printMessage("Usage: smelt [item]", CommonColours.INFO.getColour());
							}
							resetInput();
						}
						else if(option.equals("help"))
						{
							game.printMessage("Commands:", CommonColours.INFO.getColour().darker());
							for(int x = 0; x < commandListStandard.size(); x++)
							{
								if(commandListStandard.get(x).isLocationValidForCommand(game.var.location))
								{
									if(!commandListStandard.get(x).getCommand().equals("devmode") && !commandListStandard.get(x).isDevCommand)
									{
										game.printMessage("- " + commandListStandard.get(x).usage, CommonColours.INFO.getColour());
									}
								}
							}
							resetInput();
						}
						else if(option.equalsIgnoreCase("exit"))
						{
							System.exit(1);
						}
						else
						{
							if(option.startsWith("devmode"))
							{
								game.var.cheatsEnabled ^= true;
								resetInput();
							}
							else if(game.var.cheatsEnabled)
							{
								System.out.println("Trying to do a dev command");
								if(option.startsWith("time set"))
								{
									String[] data = option.split(" ");
									if(data[2] != null)
									{
										if(this.isAllCharsDigits(data[2]))
										{
											game.var.timeHour = Integer.parseInt(data[2]);
										}
										else
										{
											game.printMessage("USAGE:" + CommandsStandard.TIME_SET.usage, CommonColours.INFO.getColour());
										}
									}
									else
									{
										game.printMessage("USAGE:" + CommandsStandard.TIME_SET.usage, CommonColours.INFO.getColour());
									}
									resetInput();
								}
								else if(option.startsWith("give"))
								{								
									String[] data = option.split(" ");
									String item = "";
									for(int i = 1; i < (data.length - 1); i++)
									{
										if(i > 1) item += " ";
										item += data[i];
									}

									int amount = 1;
									if(isAllCharsDigits(data[data.length - 1]))
									{
										amount = Integer.parseInt(data[data.length - 1]);
									}
									else
									{
										game.printMessage("Usage: " + CommandsStandard.GIVE.usage, CommonColours.INFO.getColour());
									}

									if(!item.isEmpty() && isAllCharsLetters(item))
									{
										game.var.addMaterial(item, false, amount, false);
									}
									else
									{
										game.printMessage("Usage: " + CommandsStandard.GIVE.usage, CommonColours.INFO.getColour());
									}
									resetInput();
								}
								else if(option.startsWith("save "))
								{
									String fileName = option.replace("save ", "");
									try 
									{
										new SaveGame(game, fileName);
									} 
									catch (Exception e1) 
									{
										e1.printStackTrace();
									}
									resetInput();
								}
								else if(option.startsWith("load"))
								{
									String fileName = option.replace("load", "");
									fileName = fileName.replace(" ", "");
									if(!fileName.isEmpty())
									{
										try 
										{
											new LoadGame(game, fileName);
										} 
										catch (Exception e1) 
										{
											e1.printStackTrace();
										}
									}
									else
									{
										game.printMessage("Usage: load <name>", CommonColours.INFO.getColour());
									}
									resetInput();
								}
								else if(option.equalsIgnoreCase("damage"))
								{
									game.var.currentHealth -= 4;
									System.out.println("CURRENT HEALTH: " + game.var.currentHealth);
								}
								else if(option.equalsIgnoreCase("battle"))
								{
									System.out.println("Trying to start battle");
									game.input.setEditable(false);
									action.spawnMobAtChance(1, 1);
									resetInput();
								}
								else
								{
									game.printMessage("No such action: " + option, CommonColours.ATTACK.getColour());
									resetInput();
								}
							}
						}
					}
				}
			}
			else if(game.isInBattle)
			{
				if(e.getSource() == game.input)
				{					
					String option = game.input.getText();
					MobBattle battle = game.mobBattle;

					boolean isCommandValidForLocation = isCommandValidForLocation(option);

					if(isCommandValidForLocation)
					{
						if(option.equalsIgnoreCase("attack"))
						{
							battle.attack();
							resetInput();
						}
						else if(option.startsWith("eat"))
						{
							String[] data = option.split(" ");
							String foodName = getItem(data, 0);
							if(!foodName.isEmpty())
							{
								battle.eat(foodName);
							}
							else
							{
								game.printMessage(CommandsStandard.EAT.usage, CommonColours.INFO.getColour());
							}
							resetInput();
						}
						else if(option.equalsIgnoreCase("inventory"))
						{
							game.input.setEditable(false);
							action.inventory();
							resetInput();
						}
						else if(option.equalsIgnoreCase("run"))
						{
							battle.leave();
							resetInput();
						}
						else if(option.startsWith("devmode"))
						{
							game.var.cheatsEnabled ^= true;
							resetInput();
						}
						else if(game.var.cheatsEnabled)
						{
							System.out.println("Trying to do a dev command");
							if(option.startsWith("time set "))
							{
								String time = option.replace("time set ", "");
								game.var.timeHour = Integer.parseInt(time);
								resetInput();
							}
							else if(option.startsWith("give"))
							{								
								String[] data = option.split(" ");
								String item = "";
								for(int i = 1; i < (data.length - 1); i++)
								{
									if(i > 1) item += " ";
									item += data[i];
								}

								int amount = 1;
								if(isAllCharsDigits(data[data.length - 1]))
								{
									amount = Integer.parseInt(data[data.length - 1]);
								}
								else
								{
									game.printMessage("Usage: " + CommandsStandard.GIVE.usage, CommonColours.INFO.getColour());
								}

								if(!item.isEmpty() && isAllCharsLetters(item))
								{
									game.var.addMaterial(item, false, amount, false);
								}
								else
								{
									game.printMessage("Usage: " + CommandsStandard.GIVE.usage, CommonColours.INFO.getColour());
								}
								resetInput();
							}
							else if(option.equalsIgnoreCase("damage"))
							{
								game.var.currentHealth -= 4;
								System.out.println("CURRENT HEALTH: " + game.var.currentHealth);
							}
							else
							{
								game.printMessage("No such action: " + option, CommonColours.ATTACK.getColour());
								resetInput();
							}
						}
						else
						{
							game.printMessage("No such action: " + option, CommonColours.ATTACK.getColour());
							resetInput();
						}
					}
				}
			}
		}
	}

	public boolean isCommandValidForLocation(String option)
	{
		int startTime = (int)System.currentTimeMillis();
		boolean isValid = false;

		if(game.var.cheatsEnabled)
		{
			return true;
		}

		for(int x = 0; x < commandListStandard.size(); x++)
		{
			String command = commandListStandard.get(x).getCommand();

			String prepareCommand = option.replace(command, "");

			String newCommand = option.replace(prepareCommand, "");

			if(newCommand.isEmpty())
			{
				newCommand = option;
			}

			if(commandListStandard.get(x).getCommand().equals(newCommand))
			{
				if(commandListStandard.get(x).isDevCommand)
				{
					game.printMessage("You don't have access to that.", CommonColours.ERROR.getColour());
					isValid = false;
					resetInput();
					break;
				}
				
				if(game.isInBattle)
				{
					if(commandListStandard.get(x).isBattleCommand)
					{
						isValid = true;
						break;
					}
					else
					{
						game.printMessage("You can't do that action here.", CommonColours.ATTACK.getColour());
						isValid = false;
						resetInput();
						break;
					}
				}

				if(commandListStandard.get(x).isLocationValidForCommand(game.var.location))
				{
					isValid = true;
					break;
				}
				else
				{
					game.printMessage("You can't do that action here.", CommonColours.ATTACK.getColour());
					isValid = false;
					resetInput();
					break;
				}
			}

			if(x == commandListStandard.size() - 1)
			{
				game.printMessage("No such action: " + newCommand, CommonColours.ATTACK.getColour());
				resetInput();
			}
		}
		return isValid;
	}

	public boolean isAllCharsLetters(String string)
	{
		string = string.replace(" ", "");
		char[] charArray = string.toCharArray();
		for(int i = 0; i < charArray.length; i++)
		{
			if(!Character.isLetter(charArray[i]))
			{
				return false;
			}
		}
		return true;
	}

	public boolean isAllCharsDigits(String string)
	{
		char[] charArray = string.toCharArray();
		for(int i = 0; i < charArray.length; i++)
		{
			if(!Character.isDigit(charArray[i]))
			{
				return false;
			}
		}
		return true;
	}

	public String getItem(String[] string, Integer ... dataToSkip)
	{
		String item = "";
		for(int i = 0; i < dataToSkip.length; i++)
		{
			for(int j = 0; j < string.length; j++)
			{
				if(!dataToSkip[i].equals(j))
				{
					if(j > 1) item += " ";
					item += string[j];
				}
			}
		}
		return item;	
	}


	public void resetInput()
	{
		game.history.addToHistory(game.input.getText());
		game.history.count = -1;
		updateSound();
		game.repaint();
		game.updateScrollBar();
		game.input.setText("");
	}

	public void updateSound()
	{
		if(!game.action.isDay())
		{
			if(SoundManager.BIRD_CHIRP.isRunning())
			{
				SoundManager.BIRD_CHIRP.stop();
				SoundManager.CRICKET.loop(1000);
			}
		}
		else
		{
			if(SoundManager.CRICKET.isRunning())
			{
				SoundManager.CRICKET.stop();
				SoundManager.BIRD_CHIRP.loop(1000);
			}
		}
	}

	public void updateTrees()
	{
		if(game.var.treeCount < 3)
		{
			game.var.treeGrowTime += 1;
			if(game.var.treeGrowTime == 5)
			{
				game.var.treeCount += 1;
				game.var.treeGrowTime = 0;
				if(game.var.location.startsWith("tree"))
				{
					game.graphics.changeLocationImage("tree" + game.var.treeCount);
				}
			}
		}
	}
}

