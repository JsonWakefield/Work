package main.Core;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {

	public static float HEALTH = 100;
	
	private float greenValue = 255;
	
	
	private int score = 0;
	private int level = 1;
	
	private int fps = 0;
	
	public void setFPS(int fps) {
		this.fps = fps;
	}
	
	
	
	
	public void tick() {
				
		HEALTH = (int) Game.clamp(HEALTH, 0, 100);
		greenValue = (int) Game.clamp(greenValue,  0, 255);
		greenValue = (255 * HEALTH)/100;
		score++;
		
	}
	
	
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 450, 32);
		g.setColor(new Color(150,(int)greenValue,0));
		g.fillRect(15, 15, (int) ((int)HEALTH * 4.5), 32);
		g.setColor(Color.white);
		g.drawRect(14, 14, 452, 34);
		
		
		
		
		g.drawString("Score:" + score, 15, 64);
		g.drawString("Level:" + level, 15, 80);
		g.drawString("FPS: " + fps, 15, 96); // Display FPS
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
