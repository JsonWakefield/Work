package main.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Trail;
import main.Core.Game;
import main.Core.GameObject;
import main.Core.Handler;
import main.Core.ID;

public class FastEnemy extends GameObject{

	
	
	
	private Handler handler;   
	
	
	
	public FastEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;

		velX = 4;
		velY = 14;
		
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y,10,10);
		
	}


	public void tick() {
		
		x += velX;					//Adds Velocity Variables to x and y positions
		y += velY;							//allowing movement
		
		
		if(y <= 0 || y >= Game.HEIGHT - 46) velY *= -1;      //Sets Border Y-Axis
		if(x <= 0 || x >= Game.WIDTH - 26) velX *= -1;		//Sets Border X-Axis
		
		
		handler.addObject(new Trail((int) x,(int) y,ID.Trail,Color.cyan,10,10,0.05f,handler));
		
	}

	
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect((int) x,(int) y, 10, 10);
		
	}

}
