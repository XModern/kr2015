import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener, KeyListener
{
	public static Pong pong;
	private int width = 700;
	private int height = 700;
	private Renderer renderer;
	private Paddle player1;
	private Paddle player2;
	private Ball ball=new Ball();
	private boolean bot = false;
	private boolean selectingDifficulty;
	private boolean w;
	private boolean s;
	private boolean up;
	private boolean down;
	private int gameStatus = 0;
	private int scoreLimit = 7; 
	private int playerWon;
	private int botDifficulty;
	private int botMoves;
	private int botCooldown = 0;
	private int Volume= 0;
	//public Random random;
	private JFrame jframe;	
	private Sound sound;	
	private Thread soundThread;	
	private BackgroundBall BgBall;
	private BackgroundMusicRunnable SoundControl;
	private int color=1;

	public Pong()
	{
		Timer timer = new Timer(20, this);
		
		//random = new Random();

		jframe = new JFrame("Pong");
		
		System.out.println("timer.start");
		//timer.start();
		System.out.println("timer.stop");

		renderer = new Renderer();
		
		//timer.start();

		jframe.setSize(width + 15, height + 35);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(renderer);
		jframe.addKeyListener(this);
		BgBall= new BackgroundBall(this);
		
		System.out.println("before");
		timer.start();
		System.out.println("after");
		soundThread= new Thread(SoundControl= new BackgroundMusicRunnable());
		soundThread.start();
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

	public void start()
	{
		gameStatus = 2;
		player1 = new Paddle(this, 1);
		player2 = new Paddle(this, 2);
		ball = new Ball(this);
	}

	public void update()
	{
		if (player1.getScore() >= scoreLimit)
		{
			playerWon = 1;
			gameStatus = 3;
		}

		if (player2.getScore() >= scoreLimit)
		{
			gameStatus = 3;
			playerWon = 2;
		}

		if (w)
		{
			player1.move(true);
		}
		if (s)
		{
			player1.move(false);
		}

		if (!bot)
		{
			if (up)
			{
				player2.move(true);
			}
			if (down)
			{
				player2.move(false);
			}
		}
		else
		{
			if (botCooldown > 0)
			{
				botCooldown--;

				if (botCooldown == 0)
				{
					botMoves = 0;
				}
			}
			
			System.out.println("botMoves: "+botMoves);
			if (botMoves < 10)
			{
				if (player2.getY() + player2.getHeight() / 2 < ball.getY())
				{
					player2.move(false);
					botMoves++;
				}

				else if (player2.getY() + player2.getHeight() / 2 > ball.getY())
				{
					player2.move(true);
					botMoves++;
				}

				if (botDifficulty == 0)
				{
					botCooldown = 20;
				}
				if (botDifficulty == 1)
				{
					botCooldown = 15;
				}
				if (botDifficulty == 2)
				{
					botCooldown = 10;
				}
			}
		}
////////////////////////////////////////////////
		ball.update(player1, player2);
	}

	public void render(Graphics2D g)
	{
		System.out.println("i ");
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, width, height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if ((gameStatus)!=2)
		{
			BgBall.move();
			BgBall.render(g);
		}

		if (gameStatus == 0)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PING-PONG", width / 2 - 150, 50);

			System.out.println(selectingDifficulty);
			if (!selectingDifficulty)
			{
				System.out.println("2 ");
				g.setFont(new Font("Arial", 1, 30));

				g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 25);
				g.drawString("Press Shift to Play with Bot", width / 2 - 200, height / 2 + 25);
				g.drawString("Press 'O' to open the options", width / 2 - 210, height / 2 + 75);
				g.drawString("<< Score Limit: " + scoreLimit + " >>", width / 2 - 150, height / 2 + 125);
			}
		}

		if (selectingDifficulty)
		{
			String string;
			System.out.println("botDifficulty: "+botDifficulty);
			if(botDifficulty == 0)
			{
				string= "Easy";
			}
			else if(botDifficulty == 1)
			{
				string= "Medium";
			}
			else
			{
				string= "Hard";
			}
			
			g.setFont(new Font("Arial", 1, 30));

			g.drawString("<< Bot Difficulty: " + string + " >>", width / 2 - 180, height / 2 - 25);
			g.drawString("Press Space to Play", width / 2 - 150, height / 2 + 25);
		}

		if (gameStatus == 1)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
		}

		if (gameStatus == 2)
		{
			g.setColor(Color.WHITE);

			g.setStroke(new BasicStroke());

			g.drawLine(width / 2, 0, width / 2, height);

			g.setStroke(new BasicStroke());

			g.drawOval(width / 2 - 150, height / 2 - 150, 300, 300);

			g.setFont(new Font("Arial", 1, 50));
			
			g.setColor(Color.BLACK);

			g.drawString(String.valueOf(player1.getScore()), width / 2 - 90, 50);
			g.drawString(String.valueOf(player2.getScore()), width / 2 + 65, 50);

			player1.render(g,Color.RED);
			player2.render(g,Color.BLUE);
			ball.render(g,color);
		}

		if (gameStatus == 3)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PING-PONG", width / 2 - 75, 50);

			if (bot && playerWon == 2)
			{
				g.drawString("The Bot Wins!", width / 2 - 170, 200);
			}
			else
			{
				g.drawString("Player " + playerWon + " Wins!", width / 2 - 165, 200);
			}

			g.setFont(new Font("Arial", 1, 30));

			g.drawString("Press Space to Play Again", width / 2 - 185, height / 2 - 25);
			g.drawString("Press ESC for Menu", width / 2 - 140, height / 2 + 25);
			//SoundControl.stop();
			//soundThread.stop();
			
			
		}
		
		if (gameStatus == 4)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("OPTIONS", width / 2 - 150, 50);

			System.out.println("Volume Option");
			if (!selectingDifficulty)
			{
				g.setFont(new Font("Arial", 1, 30));

				g.drawString("On/Off Background Music", width / 2 - 210, height / 2 -100);
				g.drawString("the left arrow or the right arrow", width / 2 - 235, height / 2 + -50);
				if (Volume==0)
				{
					g.drawString("<< Volume: " + "Off" + " >>", width / 2 - 140, height / 2 + 0);
				}
				else
				{
					g.drawString("<< Volume: " + "On" + " >>", width / 2 - 140, height / 2 + 0);
				}
				SoundControl.setVolume((float)Volume);
				
				g.drawString("Choose Ball color", width / 2 - 160, height / 2 +75);
				g.drawString("the up arrow or the down arrow", width / 2 - 235, height / 2 + +125);
				if (color==1)
				{
					g.drawString("<< Color: " + "Black" + " >>", width / 2 - 160, height / 2 + 175);
				}
				else if (color==2)
				{
					g.drawString("<< Color: " + "Blue" + " >>", width / 2 - 140, height / 2 + 175);
				}
				else if (color==3)
				{
					g.drawString("<< Color: " + "Cyan" + " >>", width / 2 - 140, height / 2 + 175);
				}
				else if (color==4)
				{
					g.drawString("<< Color: " + "Magenta" + " >>", width / 2 - 140, height / 2 + 175);
				}
				else if (color==5)
				{
					g.drawString("<< Color: " + "Orange" + " >>", width / 2 - 140, height / 2 + 175);
				}
				else if (color==6)
				{
					g.drawString("<< Color: " + "Pink" + " >>", width / 2 - 140, height / 2 + 175);
				}
				else if (color==7)
				{
					g.drawString("<< Color: " + "Red" + " >>", width / 2 - 140, height / 2 + 175);
				}
				else if (color==8)
				{
					g.drawString("<< Color: " + "Yellow" + " >>", width / 2 - 140, height / 2 + 175);
				}
			}		
			
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("in");
		if (gameStatus == 2)
		{
			update();
		}

		renderer.repaint();
		
		//Graphics g = null;
		//pong.render((Graphics2D) g);

	}

	public static void main(String[] args)
	{
		pong = new Pong();
	}


	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			w = true;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = true;
		}
		else if (id == KeyEvent.VK_UP)
		{
			if(gameStatus==4)
			{
				if (color<8)
				{
					color++;
				}
				else
				{
					color=1;
				}
			}
			else
			{
				up = true;
			}
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			if(gameStatus==4)
			{
				if (color>1)
				{
					color--;
				}
				else
				{
					color=8;
				}
			}
			else
			{
				down = true;
			}
		}
		else if (id == KeyEvent.VK_RIGHT)
		{
			if(gameStatus == 0) 
			{
				if (selectingDifficulty)
				{
					if (botDifficulty < 2)
					{
						botDifficulty++;
					}
					else
					{
						botDifficulty = 0;
					}
				}
				else if (gameStatus == 0)
				{
					scoreLimit++;
				}
			}
			if(gameStatus == 4)
			{
				if (Volume<1)
				{
					Volume++;
				}
				else
				{
					Volume=0;
				}
			}
		}
		else if (id == KeyEvent.VK_LEFT)
		{
			if(gameStatus == 0)
			{
				if (selectingDifficulty)
				{
					if (botDifficulty > 0)
					{
						botDifficulty--;
					}
					else
					{
						botDifficulty = 2;
					}
				}
				else if (gameStatus == 0 && scoreLimit > 1)
				{
					scoreLimit--;
				}
			}
			if(gameStatus == 4)
			{
				if (Volume>0)
				{
					Volume--;
				}
				else
				{
					Volume=1;
				}
			
			}
		}
		else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3|| gameStatus == 4))
		{
			gameStatus = 0;
		}
		else if (id == KeyEvent.VK_SHIFT && gameStatus == 0)
		{
			bot = true;
			selectingDifficulty = true;
		}
		else if (id == KeyEvent.VK_O)
		{
			gameStatus = 4;
		}
		else if (id == KeyEvent.VK_SPACE)
		{
			if (gameStatus == 0 || gameStatus == 3)
			{
				if (!selectingDifficulty)
				{
					bot = false;
				}
				else
				{
					selectingDifficulty = false;
					//bot =true;
				}
				
				
				//File file = new File("music/Turtles_tournament_-_Street.wav"); 
				//sound = new Sound(file);
				//sound.playInf();
				
			//sdsg	
				start();
				
				
				//File file = new File("music/Turtles_tournament_-_Street.wav"); 
				//sound = new Sound(file);
//while(true)
//{
				//sound.playInf();
//}
			}
			else if (gameStatus == 1)
			{
				gameStatus = 2;
			}
			else if (gameStatus == 2)
			{
				gameStatus = 1;
			}
		}
	}

	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			w = false;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = false;
		}
		else if (id == KeyEvent.VK_UP)
		{
			up = false;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			down = false;
		}

	}

	public void keyTyped(KeyEvent e)
	{

	}
}