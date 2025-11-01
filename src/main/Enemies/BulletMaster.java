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

public class BulletMaster extends GameObject{

	
	
	
	private Handler handler;   
	private int timer = 45;
	private int timer2 = 80;
	
	Random r = new Random();
	
	public BulletMaster(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velX = 0;
		velY = 2;
		
		}

		
	
	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y,80,80);
		
	}


	public void tick() {
		
		x += velX;					//Adds Velocity Variables to x and y positions
		y += velY;							//allowing movement
		
		
		
		
		if(timer <= 0) velY = 0; 
		else timer--;
		
		
		if(timer <= 0) timer2--;
		if(timer2<= 0) {
			if(velX == 0) velX = 3;
			
			
			if(velX > 0)
				velX += 0.0005f;
			else if(velX< 0)
				velX -= 0.0005f;
			
			
			velX = Game.clamp(velX, -10, 10);
			
			
			
			int spawn = r.nextInt(5);
			if(spawn == 0) handler.addObject(new EnemyBullet((int)x+ 40,(int)y+ 40,ID.Bullet,handler));
			
		}
		
		if(x <= 0 || x >= Game.WIDTH - 80) velX *= -1;		//Sets Border X-Axis
		
		handler.addObject(new Trail((int) x ,(int) y,ID.Trail,Color.red,80,80,0.2f,handler));
		
	}

	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x,(int) y, 80, 80);
		
	}


}
