package main.Core;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);	
			
			int gameObjectCount = getObjectCount();
			
			//System.out.println("Number of game objects: " + gameObjectCount);
			
			if (tempObject == null) {
			    System.out.println("tempObject is null at Handler.tick()");
			    continue; //Skip this iteration
			}
			
			
			tempObject.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);	
			
			tempObject.render(g);
		}
	}
	
	public void clearEnemies() {
		for(int i = 0; i < object.size();i++) {
			GameObject tempObject = object.get(i);;
			if(tempObject.getId() != ID.Player) {
				removeObject(tempObject);
				i--;
			}
		}
		
	}public void clearPlayer() {
		for(int i = 0; i < object.size();i++) {
			GameObject tempObject = object.get(i);;
			if(tempObject.getId() == ID.Player) {
				removeObject(tempObject);
				i--;
			}
		}
		
	}
	
	
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
	public int getObjectCount() {
		return object.size();
	}
	
	
}
