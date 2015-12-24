
public class SoundRunnable implements Runnable
{
	private Sound sound;
	private String soundName;
	
	public SoundRunnable(String name)
	{
		soundName=name;
	}
	public void run() 
	{
		sound.playSound(soundName);		
	}
}
