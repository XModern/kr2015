
public class BackgroundMusicRunnable implements Runnable 
{
	private boolean soundActivation=true;
	private Sound sound;
	
	public void stop()
	{
		soundActivation=false;
		//sound.stop();
	}

	public void run() 
	{
		while(soundActivation)
		{
			if(soundActivation!=false)
			{
				sound.playSound("music/Turtles_tournament_-_Street.wav").join();
			}
		}
		
	}

}
