package main.Core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.Core.Game.STATE;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	private int speed = 6;
	
	private boolean[] keyDown = new boolean[4];
	
	Game game;
	
	
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;

		keyDown[0]=false;
		keyDown[1]=false;
		keyDown[2]=false;
		keyDown[3]=false;
		
		
		
	}
	
	
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {       //Player Key events
				
				if(key == KeyEvent.VK_W) {tempObject.setVelY(-speed);keyDown[0]=true;}
				if(key == KeyEvent.VK_S) {tempObject.setVelY(speed);keyDown[1]=true;}
				if(key == KeyEvent.VK_A) {tempObject.setVelX(-speed);keyDown[2]=true;}
				if(key == KeyEvent.VK_D) {tempObject.setVelX(speed);keyDown[3]=true;}
				
				
				
				if(key == KeyEvent.VK_UP) {tempObject.setVelY(-speed);keyDown[0]=true;}
				if(key == KeyEvent.VK_DOWN) {tempObject.setVelY(speed);keyDown[1]=true;}
				if(key == KeyEvent.VK_LEFT) {tempObject.setVelX(-speed);keyDown[2]=true;}
				if(key == KeyEvent.VK_RIGHT) {tempObject.setVelX(speed);keyDown[3]=true;}
				
				
				//if(key == KeyEvent.VK_SPACE);
				//System.out.println(key);
			}
			
			
		}
		if(key == KeyEvent.VK_SPACE)
		{
			if(game.gameState == Game.STATE.Game) {
				if(Game.slowmotion) Game.slowmotion = false;
				else Game.slowmotion = true;
			}
		}
		
		
		if(key == KeyEvent.VK_ESCAPE) {
			if(game.gameState == STATE.Game) {
				if(Game.paused) Game.paused = false;
				else Game.paused = true;
			}
			
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
			
			for(int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				
				if(tempObject.getId() == ID.Player) {       //Player Key events
					
					
					
					
					if(key == KeyEvent.VK_W) keyDown[0]=false;   
					if(key == KeyEvent.VK_S) keyDown[1]=false;   
					if(key == KeyEvent.VK_A) keyDown[2]=false;   
					if(key == KeyEvent.VK_D) keyDown[3]=false;   
					
					if(key == KeyEvent.VK_UP) keyDown[0]=false;   
					if(key == KeyEvent.VK_DOWN) keyDown[1]=false;   
					if(key == KeyEvent.VK_LEFT) keyDown[2]=false;   
					if(key == KeyEvent.VK_RIGHT) keyDown[3]=false;   
					
					
					
					
					//vertical movement
					
					if(!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
					
					//horizontal movement
					
					if(!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
				}
				
				
			}
		}
		
}
	
	

