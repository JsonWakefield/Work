package main.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.Trail;
import main.Core.Game;
import main.Core.GameObject;
import main.Core.Handler;
import main.Core.ID;

public class EnemyBullet extends GameObject{

	
	
	
	private Handler handler;   
	Random r = new Random();
	
	
	public EnemyBullet(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;

		velX = (r.nextInt(10- -10) + -10);
		velY = 10;
		
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y,10,10);
		
	}


	public void tick() {
		
		x += velX;					//Adds Velocity Variables to x and y positions
		y += velY;							//allowing movement
		
		
		//if(y <= 0 || y >= Game.HEIGHT - 46) velY *= -1;      //Sets Border Y-Axis
		//if(x <= 0 || x >= Game.WIDTH - 26) velX *= -1;		//Sets Border X-Axis
		
		
		if(y >= Game.HEIGHT) handler.removeObject(this);
		
		handler.addObject(new Trail((int) x,(int) y,ID.Trail,Color.gray,10,10,0.05f,handler));
		
	}

	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect((int) x,(int) y, 10, 10);
		
	}

}
