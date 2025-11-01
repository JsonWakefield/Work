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

public class HardEnemy extends GameObject {

    private Handler handler;   
    private Random r = new Random();
    private int velX;
    private int velY;
    
    public HardEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        // Define possible velocities for each direction
        int[] possibleVelX = {6, -6, 6, -6};
        int[] possibleVelY = {-6, -6, 6, 6};

        // Randomly choose one of the four directions
        int direction = r.nextInt(4);

        // Set the velocities based on the chosen direction
        this.velX = possibleVelX[direction];
        this.velY = possibleVelY[direction];
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }

    public void tick() {
        x += velX; // Adds Velocity Variables to x and y positions
        y += velY; // allowing movement

        if (y <= 0 || y >= Game.HEIGHT - 46) velY *= -1; // Sets Border Y-Axis
        if (x <= 0 || x >= Game.WIDTH - 26) velX *= -1;  // Sets Border X-Axis

        handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.red, 35, 35, 0.05f, handler));
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, 35, 35);
    }
}
