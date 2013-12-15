package com.mrcrayfish.textcraft;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import com.mrcrayfish.textcraft.crafting.CraftingManager;
import com.mrcrayfish.textcraft.crafting.SmeltingManager;
import com.mrcrayfish.textcraft.mob.MobBattle;

public class Game extends JFrame
{
	private static final long serialVersionUID = 1L;

	public JFrame frame;
	public JTextField input = new JTextField();
	public JTextPane output = new JTextPane();
	public JScrollPane scrollBar = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public SpringLayout spring = new SpringLayout();

	public MobBattle mobBattle;
	public Actions action;
	public Variables var = new Variables(this);
	public GameGraphics graphics = new GameGraphics(this);
	public CraftingManager crafting = new CraftingManager(this);
	public SmeltingManager furnace = new SmeltingManager(this);
	public History history = new History(this);
	
	public boolean isInBattle;
	
	
	public void superPaint(Graphics g)
	{
		super.paint(g);
	}
	
	public void paint(Graphics g)
	{
		graphics.paint(g);
	}

	public void append(String s, Color color) 
	{
		Style style = output.addStyle("I'm a style", null);
		StyleConstants.setForeground(style, color);

		try 
		{
			Document doc = output.getDocument();
			doc.insertString(doc.getLength(), s, style);
		} 
		catch(BadLocationException exc) 
		{
			exc.printStackTrace();
		}
	}

	public void printMessage(String message, Color color)
	{
		append("\n" + message, color);
		updateScrollBar();
	}

	/**
	 * @param message Message to print to feed
	 * @param delay Time to delay in seconds
	 */
	public void printMessage(String message, int delay, Color color)
	{
		PrintingManager.getInstance().printString(message, color, delay);
	}

	public void updateScrollBar() 
	{
		output.setCaretPosition(output.getDocument().getLength());
	}

	public void init()
	{
		PrintingManager.initialise(this);
		action = new Actions(this);
		addLocations();
		setConstraints();
		setPositions();
		setLayout(spring);
		add(scrollBar);
		add(input);
		frame.setVisible(true);
	}
	
	public void addLocations()
	{
		var.addLocation("woods");
		var.addLocation("field");
		var.addLocation("house");
	}
	
	public void setConstraints()
	{
		//Scroll Bar
		scrollBar.setPreferredSize(new Dimension(350, 220));
		
		//Input
		ActionHandler actionHandler = new ActionHandler(this);
		input.addActionListener(actionHandler);
		input.addKeyListener(history);
		input.setPreferredSize(new Dimension(350, 25));
		input.setBackground(new Color(210, 180, 140));
		input.setMargin(new Insets(0, 5, 0, 0));
		input.setEditable(false);
		
		//Output
		output.setEditable(false);
		output.setCaretColor(Color.red);
		output.setMargin(new Insets(5, 5, 5, 5));
		output.setBackground(new Color(210, 180, 140));
		Font font = new Font("Serif", Font.BOLD, 15);
		output.setFont(font);
	}
	
	public void setPositions()
	{
		spring.putConstraint(SpringLayout.NORTH, scrollBar, 10, SpringLayout.NORTH, this);
		spring.putConstraint(SpringLayout.WEST, scrollBar, 10, SpringLayout.WEST, this);
		spring.putConstraint(SpringLayout.NORTH, input, 240, SpringLayout.NORTH, this);
		spring.putConstraint(SpringLayout.WEST, input, 10, SpringLayout.WEST, this);
	}

	public Game()
	{
		frame = this;
		frame.getContentPane().setBackground(new Color(235, 209, 168));
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		frame.setTitle("TextCraft");
		frame.setSize(new Dimension(505, 340));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		init();
		openingScene();
	}
	
	public Game loadGame(String fileName)
	{
		try 
		{
			new LoadGame(this, fileName);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return this;
	}

	public void openingScene()
	{
		SoundManager.BIRD_CHIRP.loop(1000);
		this.append("Type 'help' for a list of commands.", CommonColours.COMMON.getColour());
		//append("Type 'help' for a list of commands.", CommonColours.COMMON.getColour());
		//printMessage("? is a text based game developed by MrCrayfish", 1, CommonColours.COMMON.getColour());
		input.setEditable(true);

	}
}
