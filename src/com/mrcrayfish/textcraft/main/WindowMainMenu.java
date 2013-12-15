package com.mrcrayfish.textcraft.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

public class WindowMainMenu extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private SpringLayout layout = new SpringLayout();
	private ImageIcon icon = new ImageIcon(getClass().getResource("/com/mrcrayfish/textcraft/resources/misc/title.png"));
	private JLabel title = new JLabel(icon);
	private JLabel subTitle = new JLabel("Main Menu");
	public JButton button_new = new JButton("New Game");
	public JButton button_load = new JButton("Load Game");
	public JButton button_about = new JButton("About");
	public JButton button_exit = new JButton("Exit");
	ButtonHandler handler = new ButtonHandler(this);
	
	public void init()
	{
		setButtonProperties(button_new);
		setButtonProperties(button_load);
		setButtonProperties(button_about);
		setButtonProperties(button_exit);
		setLabelProperties(subTitle);
		setTitlePosition();
		this.add(button_new);
		this.add(button_load);
		this.add(button_about);
		this.add(button_exit);
		this.add(title);
		this.add(subTitle);
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
		setButtonPosition(button);
	}
	
	public void setLabelProperties(JLabel label)
	{
		label.setFont(new Font("Arial", Font.BOLD, 20));
	}
	
	int count = 0;
	
	public void setButtonPosition(JButton button)
	{
		int center = (this.getWidth() / 2) - 60;
		layout.putConstraint(SpringLayout.NORTH, button, 90 + (46 * count), SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, button, center, SpringLayout.WEST, this);
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
	
	public WindowMainMenu()
	{
		this.setTitle("TextGame");
		this.getContentPane().setBackground(new Color(235, 209, 168));
		this.setLayout(layout);
		this.setSize(new Dimension(300, 300));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}
	
	public static void main(String[] args)
	{
		new WindowMainMenu();
	}

}
