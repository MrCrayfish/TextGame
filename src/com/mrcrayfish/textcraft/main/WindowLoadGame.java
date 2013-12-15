package com.mrcrayfish.textcraft.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class WindowLoadGame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private SpringLayout layout = new SpringLayout();
	public static DefaultListModel<String> listModel = new DefaultListModel<String>();
	public JList<String> list = new JList<String>(listModel);
	private JScrollPane listScrollPane = new JScrollPane(list);
	private ImageIcon icon = new ImageIcon(getClass().getResource("/com/mrcrayfish/textcraft/resources/misc/title.png"));
	private JLabel title = new JLabel(icon);
	private JLabel subTitle = new JLabel("Load Game");
	public JButton button_load = new JButton("Load");
	public JButton button_rename = new JButton("Rename");
	public JButton button_delete = new JButton("Delete");
	public JButton button_back = new JButton("Back");
	ButtonHandler handler = new ButtonHandler(this);
	
	public void init()
	{
		listScrollPane.setPreferredSize(new Dimension(130, 170));
		setButtonProperties(button_load);
		setButtonProperties(button_rename);
		setButtonProperties(button_delete);
		setButtonProperties(button_back);
		setPosition(listScrollPane, 10, 90);
		setLabelProperties(subTitle);
		setTitlePosition();
		this.add(button_load);
		this.add(button_rename);
		this.add(button_delete);
		this.add(button_back);
		this.add(listScrollPane);
		this.add(title);
		this.add(subTitle);
		this.setVisible(true);
	}
	
	public void setPosition(JComponent component, int x, int y)
	{
		layout.putConstraint(SpringLayout.NORTH, component, y, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, component, x, SpringLayout.WEST, this);
	}
	
	public void setButtonProperties(JButton button)
	{
		button.addActionListener(handler);
		button.setPreferredSize(new Dimension(120, 30));
		button.setFont(new Font("Arial", Font.BOLD, 15));
		button.setForeground(Color.white);
		button.setBackground(new Color(183, 145, 91));
		button.setBorder(BorderFactory.createLineBorder(new Color(119, 94, 59)));
		button.setFocusable(false);
		setButtonPosition(button);
	}
	
	public void setLabelProperties(JLabel label)
	{
		label.setFont(new Font("Arial", Font.BOLD, 20));
	}
	
	int count = 0;
	
	public void setButtonPosition(JButton button)
	{
		layout.putConstraint(SpringLayout.NORTH, button, 90 + (46 * count), SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, button, 160, SpringLayout.WEST, this);
		count++;
	}
	
	public void setTitlePosition()
	{
		int center = (this.getWidth() / 2) - 98;
		layout.putConstraint(SpringLayout.NORTH, title, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, title, center, SpringLayout.WEST, this);
		
		FontMetrics fm = subTitle.getFontMetrics(subTitle.getFont());
		center = (this.getWidth() / 2) - (fm.stringWidth(subTitle.getText()) / 2);
		layout.putConstraint(SpringLayout.NORTH, subTitle, 60, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, subTitle, center, SpringLayout.WEST, this);
	}
	
	public void loadFilesToList(boolean reload)
	{
		if(reload)listModel.clear();
		
		File folder = new File(System.getenv("APPDATA") + "/textadventure/saves/");
		folder.mkdirs();
		File[] listOfFiles = folder.listFiles(); 
		String files;

		try
		{
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if (listOfFiles[i].isFile()) 
				{
					files = listOfFiles[i].getName();
					if (files.endsWith(".sav"))
					{
						listModel.addElement(files.replaceAll(".sav", ""));
					}
				}
			}
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}
	
	public WindowLoadGame()
	{
		this.setTitle("TextGame");
		this.getContentPane().setBackground(new Color(235, 209, 168));
		this.setLayout(layout);
		this.setSize(new Dimension(300, 300));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		loadFilesToList(false);
	}
}
