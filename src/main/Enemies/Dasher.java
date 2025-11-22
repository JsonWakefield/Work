package main.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Core.GameObject;
import main.Core.Handler;
import main.Core.ID;

public class Dasher extends GameObject {
    private Handler handler;
    private GameObject player;

    // Movement parameters you can tune
    private float walkSpeed = .4f;            // base chasing speed
    private float dashMultiplier = 12.0f;      // how many times faster while dashing

    // Dash timing (in ticks)
    private int dashCooldownTicks = 50;       // ticks between dashes (cooldown)
    private int windUpTicks = 20;             // ticks to wind-up (telegraph) before dash
    private int dashDurationTicks = 80;        // how many ticks the dash lasts

    // Internal state
    private int cooldownTimer = 0;            // counts down the cooldown until next wind-up
    private int windUpTimer = 0;              // counts down the wind-up remaining ticks
    private int dashTimer = 0;                // counts down remaining dash ticks

    private boolean windingUp = false;
    private boolean dashing = false;

    // Locked dash direction (set when wind-up starts) so dash goes straight
    private float dashDirX = 0f;
    private float dashDirY = 0f;

    // Visual tuning for wind-up indicator
    private Color normalColor = Color.yellow;
    private Color windUpColor = Color.red;
    private float windUpScale = 1.5f;         // how much the square scales during wind-up

    public Dasher(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        // Find player object reference (assumes player already exists in handler)
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Player) {
                player = handler.object.get(i);
                break;
            }
        }

        // initialize cooldown so it dashes after dashCooldownTicks if desired
        cooldownTimer = dashCooldownTicks;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public void tick() {
        if (player == null) {
            // attempt to find player in case it was added after construction
            for (int i = 0; i < handler.object.size(); i++) {
                if (handler.object.get(i).getId() == ID.Player) {
                    player = handler.object.get(i);
                    break;
                }
            }
            if (player == null) return; // still not found, bail out
        }

        if (windingUp) {
            // During wind-up: no movement, lock direction already set
            velX = 0;
            velY = 0;

            windUpTimer--;
            if (windUpTimer <= 0) {
                // Start dashing
                windingUp = false;
                dashing = true;
                dashTimer = dashDurationTicks;
            }
        } else if (dashing) {
            // Move in the locked dash direction (straight line)
            velX = dashDirX * walkSpeed * dashMultiplier;
            velY = dashDirY * walkSpeed * dashMultiplier;

            dashTimer--;
            if (dashTimer <= 0) {
                // End dash, start cooldown
                dashing = false;
                cooldownTimer = dashCooldownTicks;
                velX = 0;
                velY = 0;
            }
        } else {
            // Regular chasing behavior: compute direction toward player
            float diffX = player.getX() - x;
            float diffY = player.getY() - y;

            float distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);
            if (distance == 0) {
                velX = 0;
                velY = 0;
            } else {
                float dirX = diffX / distance;
                float dirY = diffY / distance;

                velX = dirX * walkSpeed;
                velY = dirY * walkSpeed;
            }

            // Countdown cooldown until next wind-up
            cooldownTimer--;
            if (cooldownTimer <= 0) {
                // Begin wind-up: lock dash direction using player's current position
                float lockDiffX = player.getX() - x;
                float lockDiffY = player.getY() - y;
                float lockDistance = (float) Math.sqrt(lockDiffX * lockDiffX + lockDiffY * lockDiffY);
                if (lockDistance == 0) {
                    // If overlapping, dash in current velocity direction (or fallback)
                    if (velX == 0 && velY == 0) {
                        dashDirX = 1f;
                        dashDirY = 0f;
                    } else {
                        float vlen = (float) Math.sqrt(velX * velX + velY * velY);
                        dashDirX = velX / vlen;
                        dashDirY = velY / vlen;
                    }
                } else {
                    dashDirX = lockDiffX / lockDistance;
                    dashDirY = lockDiffY / lockDistance;
                }

                // Start wind-up
                windingUp = true;
                windUpTimer = windUpTicks;

                // Optional: spawn a pre-dash particle, play sound, set animation state, etc.
                // e.g., handler.addObject(new PreDashIndicator(x, y, ...));
            }
        }

        // Apply velocities to position
        x += velX;
        y += velY;
    }

    public void render(Graphics g) {
        // Draw base square. If winding up, flash color and scale slightly.
        if (windingUp) {
            g.setColor(windUpColor);

            // Simple pulsing effect during wind-up based on remaining ticks
            float progress = 1f - (float) windUpTimer / (float) Math.max(1, windUpTicks); // 0 -> 1
            // You can tweak how size changes; here we grow from 1.0 to windUpScale
            float currentScale = 1.0f + (windUpScale - 1.0f) * progress;

            int baseSize = 16;
            int size = Math.round(baseSize * currentScale);
            int drawX = Math.round(x - (size - baseSize) / 2.0f);
            int drawY = Math.round(y - (size - baseSize) / 2.0f);

            g.fillRect(drawX, drawY, size, size);
        } else {
            g.setColor(normalColor);
            g.fillRect((int) x, (int) y, 16, 16);
        }
    }
}
