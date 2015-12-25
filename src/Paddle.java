import java.awt.Color;
import java.awt.Graphics;

public class Paddle
{

	private int paddleNumber;

	private int x, y, width = 30, height = 200;

	private int score;

	public Paddle(Pong pong, int paddleNumber)
	{
		this.paddleNumber = paddleNumber;

		if (paddleNumber == 1)
		{
			this.x = 0;
		}

		if (paddleNumber == 2)
		{
			this.x = pong.getWidth() - width;
		}

		this.y = pong.getHeight() / 2 - this.height / 2;
	}
	
	public void setScore(int s)
	{
		score=s;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int getPaddleNumber()
	{
		return paddleNumber;
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

	public void render(Graphics g,Color color)
	{
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

	public void move(boolean up)
	{
		int speed = 15;

		if (up)
		{
			if (y - speed > 0)
			{
				y -= speed;
			}
			else
			{
				y = 0;
			}
		}
		else
		{
			if (y + height + speed < Pong.pong.getHeight())
			{
				y += speed;
			}
			else
			{
				y = Pong.pong.getHeight() - height;
			}
		}
	}

}
