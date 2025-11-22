package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import main.Core.Game;
import main.Core.HUD;
import main.Core.Handler;
import main.Core.ID;
import main.Core.Game.STATE;
import main.Enemies.BasicEnemy;
import main.Enemies.Dasher;
import main.Enemies.HardEnemy;
import main.Enemies.Snake;
import main.ResLoading.AudioPlayer;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private Random r = new Random();
    private HUD hud;

    public Menu(Game game, Handler handler, HUD hud) {
        this.game = game;
        this.hud = hud;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.gameState == STATE.Menu) {

            // Play Button
            if (mouseOver(mx, my, 100, 160, 200, 50)) {
                game.gameState = STATE.Select;
                handler.clearEnemies();
                AudioPlayer.playMenuSound();
                
                
            }
            if (mouseOver(mx, my, 0, 0, Game.WIDTH - 32, Game.HEIGHT - 32) && !mouseOver(mx, my, 100, 160, 200, 50)) {
                handler.addObject(new MenuParticle(mx, my, ID.Enemy, handler));  // Start screen pixel spawner on click
            }
            // Options Button
            if (mouseOver(mx, my, 100, 220, 200, 50)) {
                game.gameState = STATE.OptionsMenu;
                AudioPlayer.playMenuSound();
            }
            // Quit Button
            if (mouseOver(mx, my, 100, 280, 200, 50)) {
                System.exit(1);
                AudioPlayer.playMenuSound();
            }
        }
        if(game.gameState == STATE.Select) {				// Select Menu
        	if (mouseOver(mx, my, 600, 300, 200, 50)) {		// Normal Difficulty
                game.gameState = STATE.Game;
                AudioPlayer.playMenuSound();
                
                game.diff = 0;
                
                
             // Stop and restart game music
                AudioPlayer.stopMusic();  // Stop any currently playing music
                AudioPlayer.playGameSound();  // Start playing game music
                
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
                handler.addObject(new Dasher((Game.WIDTH / 4) - 45, 150, ID.Enemy,handler));
               /* handler.addObject(new Snake((Game.WIDTH / 2) - 45, 20, ID.Enemy,handler));
                handler.addObject(new Snake((Game.WIDTH / 4) - 45, 20, ID.Enemy,handler));
                handler.addObject(new Snake((Game.WIDTH / 6) - 45, 20, ID.Enemy,handler));
                */
                //handler.addObject(new Dasher((Game.WIDTH / 4) - 45, 40, ID.Enemy,handler));
                
                //handler.addObject(new BasicEnemy(r.nextInt((int) (Game.WIDTH - 50)), r.nextInt((int) (Game.HEIGHT - 50)), ID.Enemy, handler));
            }
        	
        	if (mouseOver(mx, my, 600, 400, 200, 50)) {		// Hard Difficulty
                game.gameState = STATE.Game;
                AudioPlayer.playMenuSound();
                
                game.diff = 1;
                
                
             // Stop and restart game music
                AudioPlayer.stopMusic();      // Stop any currently playing music
                AudioPlayer.playGameSound();  // Start playing game music
                
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
                handler.addObject(new HardEnemy(r.nextInt((int) (Game.WIDTH - 50)), r.nextInt((int) (Game.HEIGHT - 50)), ID.Enemy, handler));
            }
        	
        	if (mouseOver(mx, my, 600, 500, 200, 50)) {		// Back to Menu
                game.gameState = STATE.Menu;
                AudioPlayer.playMenuSound();
            }
        }

        
        
        
        if (game.gameState == STATE.OptionsMenu) {
            if (mouseOver(mx, my, 100, 160, 200, 50)) {
                game.gameState = STATE.Menu;
                AudioPlayer.playMenuSound();
            }
        }

        
        
        
        if (game.gameState == STATE.End) {
            // Ensure music stops when the game state is End
            AudioPlayer.stopMusic();  // Stop the music when game state is End

            if (mouseOver(mx, my, 530, 600, 220, 70)) {
                game.gameState = STATE.Menu;  // Go to Menu
                hud.setLevel(1);
                hud.setScore(0);
                AudioPlayer.playMenuSound();
            }
            if (mouseOver(mx, my, 530, 500, 220, 70)) {
                game.gameState = STATE.Game;  // Restart Game
                hud.setLevel(1);
                hud.setScore(0);
                handler.clearEnemies();
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
                handler.addObject(new BasicEnemy(r.nextInt((int) (Game.WIDTH - 50)), r.nextInt((int) (Game.HEIGHT - 50)), ID.Enemy, handler));
                AudioPlayer.stopMusic();  // Stop any currently playing music
                AudioPlayer.playGameSound();  // Start playing game music
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else return false;
    }

    public void tick() {
        // Check if game state is End and stop music if it is
        if (game.gameState == STATE.End) {
            AudioPlayer.stopMusic();  // Stop the music when game state is End
        }
    }

    public void render(Graphics g) {
        Font Bold = new Font("Arial", Font.BOLD, 100);
        Font Basic = new Font("Arial", Font.PLAIN, 48);

        if (game.gameState == STATE.Menu) {
            g.setFont(Bold);
            g.setColor(Color.white);
            g.drawString("Wave", 100, 140);

            g.setFont(Basic);
            g.drawRect(100, 160, 200, 50);
            g.drawString("Play", 150, 199);
            g.drawRect(100, 220, 200, 50);
            g.drawString("Options", 120, 259);
            g.drawRect(100, 280, 200, 50);
            g.drawString("Quit", 150, 322);
        }
        if (game.gameState == STATE.OptionsMenu) {
            g.setFont(Basic);
            g.setColor(Color.white);
            g.drawRect(100, 160, 200, 50);
            g.drawString("Back", 145, 199);
        }
        if (game.gameState == STATE.End) {
            g.setFont(Bold);
            g.setColor(Color.white);
            g.drawString("GAME OVER", Game.WIDTH / 3 - 100, 400);

            g.setFont(Basic);
            g.drawString("You lost with a Score of: " + hud.getScore(), Game.WIDTH / 4 + 15, 470);

            g.setFont(Basic);
            g.drawRect(550, 500, 220, 70);
            g.drawString("Try Again", Game.WIDTH / 2 - 170, 550);

            g.drawRect(550, 600, 220, 70);
            g.drawString("Menu", Game.WIDTH / 2 - 130, 650);
        }else if (game.gameState == STATE.Select) {
            g.setFont(Bold);
            g.setColor(Color.white);
            g.drawString("SELECT DIFFICULTY", 250, 250);

            g.setFont(Basic);
            g.drawRect(600, 300, 200, 50);
            g.drawString("Normal", 626, 342);
            g.drawRect(600, 400, 200, 50);
            g.drawString("Hard", 646, 442);
            g.drawRect(600, 500, 200, 50);
            g.drawString("Back", 646, 542);
        }
    }
}
