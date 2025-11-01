package main.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.Core.Game;
import main.Core.GameObject;
import main.Core.Handler;
import main.Core.ID;

public class Snake extends GameObject {

    private Handler handler;
    private int timerdown = 50;
    private int timerright = 80;
    private boolean movingRight = true;
    private boolean movingDown = true;
    
    Random r = new Random();
    

    //Timers set to allow the snake to go down on spawning in, travel either left or right, then head down and repeat until reaching 
    //the bottom of the screen, then wrapping to the top, up and too the right of where it was on spawning in. Then once reaching either 
    //the left or right limits it bounces and continues its loop until destroyed.
    
    
    
    private int velY, velX;
    private float initialX; // To store the initial x-coordinate

    public Snake(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        this.initialX = x;
        
        
        
        
        
        velX = 0;
        velY = 0;
    }

    public void tick() {
        x += velX; // Adds Velocity Variables to x and y positions
        y += velY; // allowing movement

        if (movingDown) {
            if (timerdown <= 0) {
                velY = 0;
                velX = movingRight ? 2 : -2;
                timerright = 80;
                movingDown = false;
            } else {
                timerdown--;
            }
        } else {
            if (timerright <= 0) {
                velX = 0;
                velY = 2;
                timerdown = 50;
                movingDown = true;
                movingRight = !movingRight; // Toggle direction
            } else {
                timerright--;
            }
        }

        // Boundary check to flip direction when hitting a wall
        if (x <= 40 || x >= Game.WIDTH - 60) {
            velX *= -1; 
            movingRight = !movingRight; // Toggle direction
        }

        // Screen wrapping logic: from bottom to top, 50 pixels to the right of the initial position
        if (y >= Game.HEIGHT) {
            y = 0;
            x = initialX += 100;
        }

        handler.addObject(new SnakeTrail((int) x, (int) y, ID.Enemy, Color.blue, 20, 20, 0.0011f, handler));
    }

    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect((int) x, (int) y, 20, 20);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 20, 20);
    }
}
