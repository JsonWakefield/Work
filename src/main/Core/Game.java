package main.Core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import main.Menu;
import main.Player;
import main.Enemies.Dasher;
import main.Enemies.Splitter;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -8531834078939069233L;

	public static final int WIDTH = 1460, HEIGHT = WIDTH / 13 * 8;
	
	private Thread thread;
	
	private boolean running = false;
	
	
	public static boolean paused = false;
	public static boolean slowmotion = true;
	
	
	public int diff = 0;
	
	// 0 = normal
	// 1 = hard
	
	
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	
	int frames = 0; // Count the number of frames rendered
	
	
	public enum STATE{
		Menu,
		Select,
		Pause,
		OptionsMenu,
		Store,
		End,
		Game;
	}
	
	public STATE gameState = STATE.Menu;
	
	
	
	
	
	public Game() {
		
		handler = new Handler();
		hud = new HUD();
		
		menu = new Menu(this, handler,hud);
		
		//AudioPlayer.playGameSound();

		spawner = new Spawn(handler, hud, this);
		this.addMouseListener(menu);
		
		
		this.addKeyListener(new KeyInput(handler,this));
		new Window(WIDTH, HEIGHT, "WAVE" , this);
		
		new Random();
		
		
		if(gameState == STATE.Game) {
			
			handler.addObject(new Player(WIDTH/2 - 32,HEIGHT/2 -32, ID.Player, handler));
			//handler.addObject(new Dasher((int) Game.WIDTH / 2 -60,(int) Game.HEIGHT ,ID.Enemy,handler));
		}
	}
	
	
	
	
	
	
	public synchronized void Start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	
		System.out.println(System.getProperty("java.runtime.version"));
			
		
	}
	public synchronized void Stop() {
		try {
			thread.join();
			running = false;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run() {
	    this.requestFocus(); // Request focus for the component, usually for keyboard input
	    
	    
	    // Get refresh rate of the monitor
	    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    int refreshRate = gd.getDisplayMode().getRefreshRate();
	    if(refreshRate <= 0) {
	    	refreshRate = 120;
	    }
	    
	    
	    
	    
	    
	    
	    long lastTime = System.nanoTime(); // Get the current time in nanoseconds
	    double amountOfTicks = 60.0; // Set the number of ticks per second (60 ticks per second)
	    double nsPerTick = 1000000000 / amountOfTicks; // Calculate the number of nanoseconds per tick
	    double nsPerFrame = 1000000000 / refreshRate; //Calculate the number of nanoseconds per frame
	    double delta = 0; // Delta to track time passed
	    long timer = System.currentTimeMillis(); // Get the current time in milliseconds
	    

	    
	    long lastRenderTime = System.nanoTime();  // Last render time in nanoSeconds
	   
	    try(BufferedWriter writer = new BufferedWriter(new FileWriter("frame_times.log"))){
	    	while(running) { // Main game loop, runs while the game is running
		        long now = System.nanoTime(); // Get the current time in nanoseconds
		        delta += (now - lastTime) / nsPerTick; // Calculate the change in time and add to delta
		        lastTime = now; // Update lastTime to the current time

		        while(delta >= 1) { // Ensure the game updates at the correct tick rate
		            tick(); // Update game logic
		            delta--; // Decrease delta
		        }
		        

		        if(running && (now - lastRenderTime) >= nsPerFrame) { // Check if the game is still running
		            render(); // Render the game graphics
		            lastRenderTime = now;
		            frames++;
		            
		            writer.write(now + "\n");   // Writes frameTime file to display how many frames are happening per tick
		        }

		        frames++; // Increase the frame count

		        if(System.currentTimeMillis() - timer > 1000) { // If one second has passed
		            timer += 1000; // Add one second to the timer
		            System.out.println("FPS:" + frames);
		            frames = 0; // Reset the frame count
		        }
		    }
	    } catch(IOException e) {
	    	e.printStackTrace();
	    }
	    
	    
	    
	    
	    Stop(); // Stop the game when running is no longer true
	}

	
	private void tick() {
		
		
		if(handler.getObjectCount() >= 4500) {
			System.out.println("CPU Frequency Overload Failure");
			System.exit(1);
		}
		
		
		
		if(gameState == STATE.Game) {
			if(!paused) {
				hud.tick();
				spawner.tick();
				handler.tick();
				
				
				if(HUD.HEALTH <= 0) {
					HUD.HEALTH = 100;
					
					gameState = STATE.End;
					handler.clearEnemies();
					handler.clearPlayer();
					
				}
				if(slowmotion) {
					handler.tick();
				}else {
					slowmotion = false;
				}
			}
			
			
			
			
		}else if(gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.OptionsMenu || gameState == STATE.Select) {
			handler.tick();
			menu.tick();
			
		}
		
		
	}
	
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect((int) 0,(int) 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if(paused) {
			Font Bold = new Font("Arial", Font.BOLD, 100);
			g.setFont(Bold);
			g.setColor(Color.white);
			g.drawString("PAUSED", 500, 430);
			Font Basic = new Font("Arial", Font.PLAIN, 12);
			g.setFont(Basic);
		}
		
		
		if(gameState == STATE.Game) {
			hud.render(g);
		}else if(gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.OptionsMenu || gameState == STATE.Select) {
			menu.render(g);
		}
		
		
		g.dispose();
		bs.show();
		
		
	}
	
	
	
	public static float clamp(float var, float min, float max) {
		if(var >= max) 
			return var = max;
		else if(var <= min)	
			return var = min;
		else
			return var;
		
	}
	
	
	
	public static void main(String args[]) {
		new Game();
	}

}
