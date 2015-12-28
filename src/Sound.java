import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound 
{
    private boolean released = false;
    private Clip clip = null;
    private FloatControl volumeC = null;
    private float vol;
    private boolean playing = false;
    private Sound snd;

    public Sound(File f) 
    {
        try 
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            //if (volumeC==null)
            //{
            	System.out.println("vol: "+vol);
            	volumeC = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            	volumeC.setValue(vol);
            //}
            //else 
            //{
            //}
            released = true;
        } 
        catch(IOException e) 
        {
            released = false;
        }
        catch(UnsupportedAudioFileException e)
        {
        	released = false;
        }
        catch(LineUnavailableException e)
        {
        	released = false;
        }
    }

    //true ���� ���� ������� ��������, false ���� ��������� ������
    public boolean isReleased() 
    {
        return released;
    }

    //������������� �� ���� � ������ ������
    public boolean isPlaying() 
    {
        return playing;
    }

    //������
	/*
	  breakOld ���������� ���������, ���� ���� ��� ��������
	  ���� breakOld==true, � ���� ����� ������� � ������� ������
	  ����� ������ �� ���������
	*/
    public void play(boolean breakOld) 
    {
        if (released) 
        {
            if (breakOld) 
            {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            } 
            else if (!isPlaying()) 
            {
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            }
        }
 
    }
    


    //�� �� �����, ��� � play(true)
    public void play() 
    {
        play(true);
    }

    //������������� ���������������
    public void stop() 
    {
        if (playing) 
        {
            clip.stop();
        }
    }

    //��������� ���������
	/*
	  x ����� ���� � �������� �� 0 �� 1 (�� ������ ������ � ������ ��������)
	*/
    public void setVolume(float x) 
    {
        if (x<0) x = 0;
        if (x>1) x = 1;
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        volumeC.setValue((max-min)*x+min);
        vol=volumeC.getValue();
    }

    //���������� ������� ��������� (����� �� 0 �� 1)
    public float getVolume() 
    {
        float v = volumeC.getValue();
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        return (v-min)/(max-min);
    }

    //���������� ��������� ������������ �����
    public void join() 
    {
        if (!released) return;
        synchronized(clip)
        {
            try 
            {
                while (playing) clip.wait();
            } 
            catch (InterruptedException exc) 
            {
            	
            }
        }
    }

    //����������� �����, ��� ��������
    public static Sound playSound(String s) 
    {
        File f = new File(s);
        Sound snd = new Sound(f);
        snd.play();
        return snd;
    }

    private class Listener implements LineListener 
    {
        public void update(LineEvent ev) 
        {
            if (ev.getType() == LineEvent.Type.STOP) 
            {
                playing = false;
                synchronized(clip) 
                {
                    clip.notify();
                }
            }
        }
    }
}