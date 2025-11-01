package main.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Trail;
import main.Core.GameObject;
import main.Core.Handler;
import main.Core.ID;

public class SmartEnemy extends GameObject{

	
	
	
	private Handler handler;   
	private GameObject player;
	
	
	public SmartEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		
		
		this.handler = handler;
		
		for(int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getId()== ID.Player) player = handler.object.get(i);
		}

		
		
		
		
		
		
		
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y,16,16);
		
	}


	public void tick() {
		
		x += velX;					//Adds Velocity Variables to x and y positions
		y += velY;							//allowing movement
		
		
		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY()	- 8;
		
		float distance = (float) Math.sqrt((x-player.getX())
									      *(x-player.getX())
										  +(y-player.getY())
										  *(y-player.getY()));
		
		
		velX =  ((-2/distance) * diffX);
		velY =  ((-2/distance) * diffY);
		
		
		//if(y <= 0 || y >= Game.HEIGHT - 46) velY *= -1;      //Sets Border Y-Axis
		//if(x <= 0 || x >= Game.WIDTH - 26) velX *= -1;		//Sets Border X-Axis
		
		
		handler.addObject(new Trail((int) x,(int) y,ID.Trail,Color.yellow,16,16,0.08f,handler));
		
	}

	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int) x,(int) y, 16, 16);
		
	}

}
