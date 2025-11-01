package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.Core.Game;
import main.Core.GameObject;
import main.Core.HUD;
import main.Core.Handler;
import main.Core.ID;




public class Player extends GameObject {

	Random r = new Random();
	Handler handler;
	
	
	
	
	public Player(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
	}
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,32,32);
		
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		x = Game.clamp(x, 0, Game.WIDTH - 48);
		y = Game.clamp(y, 0, Game.HEIGHT - 70);
		
		handler.addObject(new Trail((int)x, (int)y,ID.Trail,Color.white,32,32,0.08f,handler));
		
		collision();
	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Enemy || tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())) {
					
					
					HUD.HEALTH -= 2;
				}else if(tempObject.getId() == ID.Boss) {
					HUD.HEALTH -= 16;
				}
			}
			
			
		}
	}

	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int) x,(int) y, 32, 32);
		
	}


	
	

	
	
	
	
}
