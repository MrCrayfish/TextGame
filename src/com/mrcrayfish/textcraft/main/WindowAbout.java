package com.mrcrayfish.textcraft.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

public class WindowAbout extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private SpringLayout layout = new SpringLayout();
	public JTextPane description = new JTextPane();
	public JButton button_donate = new JButton("Donate");
	public JButton button_back = new JButton("Back");
	
	public WindowAbout()
	{
		this.setTitle("TextGame");
		this.getContentPane().setBackground(new Color(235, 209, 168));
		this.setLayout(layout);
		this.setSize(new Dimension(300, 300));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
