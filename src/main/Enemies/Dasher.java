package main.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Trail;
import main.Core.GameObject;
import main.Core.Handler;
import main.Core.ID;

public class Dasher extends GameObject {

    private Handler handler;
    private GameObject player;
    private int dashTimer = 50;
    private boolean dashing = false;

    public Dasher(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Player) {
                player = handler.object.get(i);
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public void tick() {
        x += velX;
        y += velY;

    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect((int) x, (int) y, 16, 16);
    }
}
