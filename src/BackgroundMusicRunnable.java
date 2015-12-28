
public class BackgroundMusicRunnable implements Runnable 
{
	private boolean soundActivation=true;
	private Sound sound,music;
	private float vol;
	
	
	public void setVolume(float vol)
	{
		try 
		{
			music.setVolume(vol);
			this.vol=vol;
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
				music.setVolume(vol);
				music.join();
				
			}
		}
		
	}

}
