package main.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Trail;
import main.Core.Game;
import main.Core.GameObject;
import main.Core.Handler;
import main.Core.ID;
import main.Core.Spawn;

public class Splitter extends GameObject{
	
	private Handler handler; 
	private Spawn spawn;
	Random r = new Random();
	private int speed = 3;
	private int decayTimer = 800;
	private int timer = 3;
	private double step = 0.2d;
	
	private int velX, velY;
	
	
	
	
	public Splitter(float x, float y, ID id, Handler handler, Spawn spawn) {
		super(x, y, id);
		this.handler = handler;
		this.spawn = spawn;
		
		//Generate random velocity combinations
		List<int[]> velocityCombinations = generateVelocityCombinations(speed, step);
		//Select Random Combination
		int[] selectedCombinations = velocityCombinations.get(r.nextInt(velocityCombinations.size()));
		
		// Assign to velX and velY
		velX = selectedCombinations[0];
		velY = selectedCombinations[1];
		
	}
	// Generate velocity combinations method with integer casting
	public static List<int[]> generateVelocityCombinations(int speed,double step){
		List<int[]> combinations = new ArrayList<>();
		
		for(double angle = 0; angle < 2 * Math.PI; angle += step) {
			double x = speed * Math.cos(angle);
			double y = speed * Math.sin(angle);
			combinations.add(new int[] {(int)x, (int) y});
		}
		return combinations;
	}
	

	
	public void tick() {
		x += velX;
		y += velY;
		
		//Handle collisions with window boundaries
		if(y <= 0 || y >= Game.HEIGHT - 36) {
			velY *= -1;
			if(timer <= 0) {
				Split(true);
			}
		}
		if(x <= 0 || x >= Game.WIDTH - 26) {
			velX *= -1;
			if(timer <= 0) {
				Split(false);
			}
		}
		
		handler.addObject(new Trail((int) x,(int) y,ID.Trail,Color.gray,14,14,0.14f,handler));
		
		if(timer > 0) {
			timer--;
		}
		decayTimer--;
		
		if(decayTimer <= 0) {
			handler.removeObject(this);
		}
		
		
		
	}
	
	
	public void Split(boolean horizontalCollision) {
		//Adjust x and y positions to ensure the new splitter doesn't spawn in the wall
		float newX = x + (velX > 0 ? 3 : -3);
		float newY = y + (velY > 0 ? 3 : -3);
				
		//Create the new splitter with the opposite velocity
		if(newX >= 0 && newX <= Game.WIDTH - 16 && newY >= 0 && newY <= Game.HEIGHT - 16) {
			Splitter newSplitter = new Splitter(newX,newY,ID.Enemy,handler,spawn);
			if(horizontalCollision) {
				newSplitter.velY *= -1;
				newSplitter.velX *= -1;
			}else {
				newSplitter.velX *= -1;
				newSplitter.velY *= -1;
			}
			handler.addObject(newSplitter);
			
			
		}
		
		
		timer = 3;
		
	}
	

	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect((int) x, (int) y, 16, 16);
		
	}

	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,14,14);
	}

}
