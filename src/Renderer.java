
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Renderer extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	void Renderer()
	{
		
	}

	public void paintComponent(Graphics g)
	{		
		System.out.println("opopop");

		Pong.pong.render((Graphics2D) g);
	}

}