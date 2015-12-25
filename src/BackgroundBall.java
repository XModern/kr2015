import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class BackgroundBall 
{
	private int x;
	private int y;
	private int width;
	private int height;
	private Pong pong;
	private int speed=10;
	private Random random;
	private int colorCounter=1;
	
	private boolean heightFlag=true;
	private boolean widthFlag=true;
	private Color color;
	
	private int motionX=3;
	private int motionY=6;
	
	public BackgroundBall(Pong pong)
	{
		this.pong = pong;
		this.x=pong.getWidth()/2;
		this.y=pong.getHeight()/2;
		height=10;
		width=10;
		random=new Random();
	}
	
	public void move()
	{
		x = x+ motionX;
		y = y+ motionY;
		
		System.out.println("BgBall: ["+x+"; "+y+"]");
		if (heightFlag)
		{
			height+=random.nextInt(5);
			System.out.println("heightFlag: "+heightFlag);
		}
		else 
		{
			if (height>5)
			{
				height-=random.nextInt(5);
			}
			else
			{
				heightFlag=true;
			}
		}
		
		if ((height>=249)&&(height<=255))
		{
			heightFlag=false;
		}
		
		if (widthFlag)
		{
			width+=random.nextInt(5);
		}
		else 
		{
			if (width>5)
			{
				width-=random.nextInt(5);
			}
			else
			{
				widthFlag=true;
			}
		}
		
		if ((width>=249)&&(width<=255))
		{
			widthFlag=false;
		}	
		
		
		if (checkCollision()==1)
		{
			x=0;
			motionX=-motionX;
		}
		if (checkCollision()==2)
		{
			x=pong.getWidth()-width;
			motionX=-motionX;
		}
		if(checkCollision()==3)
		{
			y=0;
			motionY=-motionY;
		}
		if(checkCollision()==4)
		{
			y=pong.getHeight()-height;
			motionY=-motionY;
		}
		colorCounter--;		
		if (colorCounter==0)
		{
			coloroficationCheck(random.nextInt(7)+1);
			colorCounter=20;
		}
	}
	
	public int checkCollision()
	{
		if (x<0)
		{
			return 1; 
		}
		else if (x+ width> pong.getWidth())
		{
			return 2; 
		}
		
		else if (y<0)
		{
			return 3; 
		}
		else if (y+height>pong.getHeight())
		{
			return 4; 
		}

		return 0; 
	}
	
	public void coloroficationCheck(int val)
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
	}
	
	public void render(Graphics g)
	{
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}
}
