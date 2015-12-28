import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball
{

	private int x, y, width = 25, height = 25;

	private int motionX, motionY;

	public Random random;

	private Pong pong;
	
	private Color color=Color.BLACK;

	public int amountOfHits;
	
	//private Sound firstPlayerHit;	
	private Thread firstPlayerHitThread;	
	private SoundRunnable firstPlayerHitControl;
	
	//private Sound secoundPlayerHit;	
	private Thread secoundPlayerHitThread;	
	private SoundRunnable secoundPlayerHitControl;

	public Ball()
	{
		
	}
	public Ball(Pong pong)
	{
		this.pong = pong;

		this.random = new Random();

		spawn();
	}
	
	public int getX()
	{
		return x;
		
	}
	
	public int getY()
	{
		return y;
		
	}
	
	public int getWidth()
	{
		return width;
		
	}
	
	public int getHeight()
	{
		return height;
		
	}
	

	public void update(Paddle paddle1, Paddle paddle2)
	{
		int speed = 5;
		
		System.out.println("this.x: "+this.x+"; motionX: "+motionX +"\n"+
						   "this.y: "+this.y+"; motionY: "+motionY +"\n");

		this.x += motionX * speed;
		this.y += motionY * speed;

		if (this.y + height - motionY > pong.getHeight() || this.y + motionY < 0)
		{
			if (this.motionY < 0)
			{
				this.y = 0;
				this.motionY = 1;
			}
			else
			{
				this.motionY = -1;
				this.y = pong.getHeight() - height;
			}
		}
//System.out.println("ooo");
		if (checkCollision(paddle1) == 1)
		{
			this.motionX = 1 ;
			this.motionY = -2 + random.nextInt(4);

			firstPlayerHitThread= new Thread(firstPlayerHitControl= new SoundRunnable("music/FirstPlayerHits.wav"));
			firstPlayerHitThread.start();

			amountOfHits++;
		}
		else if (checkCollision(paddle2) == 1)
		{
			this.motionX = -1 ;
			this.motionY = -2 + random.nextInt(4);
			secoundPlayerHitThread= new Thread(secoundPlayerHitControl= new SoundRunnable("music/FirstPlayerHits.wav"));
			secoundPlayerHitThread.start();

			amountOfHits++;
		}

		if (checkCollision(paddle1) == 2)
		{
			paddle2.setScore(paddle2.getScore()+1);
			spawn();
		}
		else if (checkCollision(paddle2) == 2)
		{
			paddle1.setScore(paddle1.getScore()+1);
			spawn();
		}
	}

	public void spawn()
	{
		this.amountOfHits = 0;
		this.x = pong.getWidth() / 2 - this.width / 2;
		this.y = pong.getHeight() / 2 - this.height / 2;

		this.motionY = -2 + random.nextInt(4);

		if (motionY == 0)
		{
			motionY = 1;
		}

		if (random.nextBoolean())
		{
			motionX = 1;
		}
		else
		{
			motionX = -1;
		}
	}

	public int checkCollision(Paddle paddle)
	{
		if (this.x < paddle.getX() + paddle.getWidth() && this.x + width > paddle.getX() && this.y < paddle.getY() + paddle.getHeight() && this.y + height > paddle.getY())
		{
			System.out.println("Otskok");
			return 1; 
		}
		else if ((paddle.getX() > x && paddle.getPaddleNumber() == 1) || (paddle.getX() < x - width && paddle.getPaddleNumber() == 2))
		{
			System.out.println("Goal");
			return 2; 
		}

		return 0; 
	}

	public void render(Graphics g,int val)
	{
		if (val==1)
		{
			color=Color.BLACK;
		}
		else if (val==2)
		{
			color=Color.BLUE;
		}
		else if (val==3)
		{
			color=Color.CYAN;
		}
		else if (val==4)
		{
			color=Color.MAGENTA;
		}
		else if (val==5)
		{
			color=Color.ORANGE;
		}
		else if (val==6)
		{
			color=Color.PINK;
		}
		else if (val==7)
		{
			color=Color.RED;
		}
		else if (val==8)
		{
			color=Color.YELLOW;
		}
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}

}