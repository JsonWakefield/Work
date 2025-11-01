package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.Core.Game;
import main.Core.GameObject;
import main.Core.Handler;
import main.Core.ID;

public class MenuParticle extends GameObject{

	
	
	
	private Handler handler;   
	private Color col;
	
	Random r = new Random();
	
	

	
	
	public MenuParticle(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;

		
		
		
		
		velX = (r.nextInt(15- -15) + -15);
		velY = (r.nextInt(15- -15) + -15);
		
		
		col = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y,10,10);
		
	}


	public void tick() {
		
		x += velX;					//Adds Velocity Variables to x and y positions
		y += velY;							//allowing movement
		
		
		if(y <= 0 || y >= Game.HEIGHT - 46) velY *= -1;      //Sets Border Y-Axis
		if(x <= 0 || x >= Game.WIDTH - 26) velX *= -1;		//Sets Border X-Axis
		
		
		handler.addObject(new Trail((int) x,(int) y,ID.Trail,col,10,10,0.05f,handler));
		
	}

	
	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int) x,(int) y, 10, 10);
		
	}

}
