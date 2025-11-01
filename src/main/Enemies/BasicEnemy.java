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

public class BasicEnemy extends GameObject {

    private Handler handler;   
    private Random r = new Random();
    private int velX;
    private int velY;
    
    public BasicEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        // Define possible velocities for each direction
        int[] possibleVelX = {5, -5, 5, -5};
        int[] possibleVelY = {-5, -5, 5, 5};

        // Randomly choose one of the four directions
        int direction = r.nextInt(4);

        // Set the velocities based on the chosen direction
        velX = possibleVelX[direction];
        velY = possibleVelY[direction];
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }

    public void tick() {
        x += velX; // Adds Velocity Variables to x and y positions
        y += velY; // allowing movement

        if (y <= 0 || y >= Game.HEIGHT - 46) velY *= -1; // Sets Border Y-Axis
        if (x <= 0 || x >= Game.WIDTH - 26) velX *= -1;  // Sets Border X-Axis

        handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.red, 16, 16, 0.05f, handler));
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, 16, 16);
    }
}
