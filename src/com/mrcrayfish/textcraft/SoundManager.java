package com.mrcrayfish.textcraft;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum SoundManager 
{
	ZOMBIE("resources/sound/zombie.wav"),
	ZOMBIE_HURT("resources/sound/zombiehurt.wav"),
	BIRD_CHIRP("resources/sound/birdchirp.wav"),
	CRICKET("resources/sound/cricket.wav");
	
	
	private Clip clip;
	
	SoundManager(String file)
	{
		try 
		{
			URL url = this.getClass().getResource(file);
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audio);
        }
		catch(UnsupportedAudioFileException uae) 
		{
            System.out.println(uae);
        }
        catch(IOException ioe) 
        {
            System.out.println(ioe);
        }
        catch(LineUnavailableException lua) 
        {
            System.out.println(lua);
        }
	}
	
	public void play()
	{
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void play(String sound)
	{
		if(sound.equals("zombie")) ZOMBIE.play();
		if(sound.equals("zombiehurt")) ZOMBIE_HURT.play();
	}
	
	public boolean isRunning()
	{
		return clip.isRunning();
	}
	
	public void stop()
	{
		if(clip.isRunning())
		{
			clip.stop();
		}
	}
	
	public void loop(int times)
	{
		if(clip.isRunning())
		{
			clip.stop();
		}
		clip.setFramePosition(0);
		clip.loop(times);
	}

}
