package com.mrcrayfish.textcraft.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class WindowRename  extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private WindowLoadGame previousFrame;
	private SpringLayout layout = new SpringLayout();
	public JButton button_rename = new JButton("Rename");
	public JButton button_cancel = new JButton("Cancel");
	public JTextField input = new JTextField();
	private Handler handler;
	
	public String fileName;
	
	public void init()
	{
		handler = new Handler(this, this.previousFrame, this.button_rename, this.button_cancel);
		setButtonProperties(button_rename);
		setButtonProperties(button_cancel);
		setPosition(button_rename, 10, 50);
		setPosition(button_cancel, 165, 50);
		setInputProperties();
		setPosition(input, 10, 10);
		this.add(button_rename);
		this.add(button_cancel);
		this.add(input);
		this.setVisible(true);
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
	}
	
	public void setInputProperties()
	{
		input.setPreferredSize(new Dimension(275, 30));
		input.setText(this.fileName);
	}

	public void setPosition(JComponent component, int x, int y)
	{
		layout.putConstraint(SpringLayout.NORTH, component, y, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, component, x, SpringLayout.WEST, this);
	}
	
	public WindowRename(WindowLoadGame previousFrame, String fileName)
	{
		this.previousFrame = previousFrame;
		this.fileName = fileName;
		this.setTitle("Rename");
		this.getContentPane().setBackground(new Color(235, 209, 168));
		this.setLayout(layout);
		this.setSize(new Dimension(300, 120));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setAlwaysOnTop(true);
		init();
	}
	
	class Handler implements ActionListener
	{
		WindowRename renameFrame;
		WindowLoadGame previousFrame;
		
		JButton button_rename;
		JButton button_cancel;
		
		public Handler(WindowRename renameFrame, WindowLoadGame previousFrame, JButton rename, JButton cancel)
		{
			this.renameFrame = renameFrame;
			this.previousFrame = previousFrame;
			this.button_rename = rename;
			this.button_cancel = cancel;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() == button_rename)
			{
				File save = new File(System.getenv("APPDATA") + "/textadventure/saves/" + renameFrame.fileName + ".sav");
				File newSave = new File(System.getenv("APPDATA") + "/textadventure/saves/" + renameFrame.input.getText() + ".sav");
				save.renameTo(newSave);
				renameFrame.dispose();
				previousFrame.loadFilesToList(true);
				previousFrame.setEnabled(true);	
				previousFrame.requestFocus();
			}
			if(e.getSource() == button_cancel)
			{
				renameFrame.dispose();
				previousFrame.setEnabled(true);	
				previousFrame.requestFocus();
			}
		}
		
	}
}
