
public class BackgroundMusicRunnable implements Runnable 
{
	private boolean soundActivation=true;
	private Sound sound,music;
	
	public void stop()
	{
		soundActivation=false;
		//sound.stop();
	}
	
	public void setVolume(float vol)
	{
		try 
		{
			music.setVolume(vol);
		}
		catch(NullPointerException e)
		{
			
		}
	}

	public void run() 
	{
		while(soundActivation)
		{
			if(soundActivation!=false)
			{
				music=sound.playSound("music/Turtles_tournament_-_Street.wav");
				music.join();
				
			}
		}
		
	}

}
