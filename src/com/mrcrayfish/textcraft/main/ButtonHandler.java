package com.mrcrayfish.textcraft.main;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;

import com.mrcrayfish.textcraft.Game;

public class ButtonHandler  implements ActionListener
{
	private JFrame frame;

	public ButtonHandler(JFrame frame)
	{
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(frame instanceof WindowMainMenu)
		{
			WindowMainMenu mainMenu = (WindowMainMenu)frame;
			if(e.getSource() == mainMenu.button_new)
			{
				new Game();
				frame.dispose();
			}
			if(e.getSource() == mainMenu.button_load)
			{
				new WindowLoadGame();
				frame.setVisible(false);
				frame.dispose();
			}
			if(e.getSource() == mainMenu.button_about)
			{
				//new WindowAbout();
				//frame.dispose();
			}
			if(e.getSource() == mainMenu.button_exit)
			{
				System.exit(1);
			}
		}
		if(frame instanceof WindowLoadGame)
		{
			WindowLoadGame loadGame = (WindowLoadGame)frame;
			if(e.getSource() == loadGame.button_load)
			{
				int[] selected = loadGame.list.getSelectedIndices();
				if(selected.length > 0)
				{
					for(int i = 0; i < selected.length; i++)
					{
						Object save = loadGame.list.getModel().getElementAt(selected[i]);
						String saveName = save.toString();
						new Game().loadGame(saveName);
					}
					frame.dispose();
				}
			}
			if(e.getSource() == loadGame.button_rename)
			{
				int[] selected = loadGame.list.getSelectedIndices();
				if(selected.length > 0)
				{
					for(int i = 0; i < selected.length; i++)
					{
						Object save = loadGame.list.getModel().getElementAt(selected[i]);
						String saveName = save.toString();
						new WindowRename((WindowLoadGame)frame, saveName);
					}
					frame.setEnabled(false);
				}
			}
			if(e.getSource() == loadGame.button_delete)
			{
				int[] selected = loadGame.list.getSelectedIndices();
				if(selected.length > 0)
				{
					for(int i = 0; i < selected.length; i++)
					{
						Object selectedSave = loadGame.list.getModel().getElementAt(selected[i]);
						String saveName = selectedSave.toString();
						File saveFile = new File(System.getenv("APPDATA") + "/textadventure/saves/" + saveName + ".sav");
						saveFile.delete();
						loadGame.loadFilesToList(true);
					}
				}
			}
			if(e.getSource() == loadGame.button_back)
			{
				new WindowMainMenu();
				frame.setVisible(false);
				frame.dispose();
			}
		}
	}

}
