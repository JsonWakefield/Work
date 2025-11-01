package main.Core;

import java.util.Random;

import main.Enemies.BasicEnemy;
import main.Enemies.BulletMaster;
import main.Enemies.FastEnemy;
import main.Enemies.HardEnemy;
import main.Enemies.SmartEnemy;
import main.Enemies.Snake;
import main.Enemies.Splitter;

public class Spawn {

    private Handler handler;
    private HUD hud;
    private Random r;
    private Game game;
    private Splitter splitter;
    
    private int scoreKeep = 0;
    private boolean bossLvlCompleted = false; 

    public enum EnemyType {
        BasicEnemy,
        HardEnemy,
        SmartEnemy,
        FastEnemy,
        Snake,
        Splitter,
        BulletMaster;
    }

    public Spawn(Handler handler, HUD hud, Game game) {
        this.handler = handler;
        this.hud = hud;
        this.game = game;
        r = new Random();
    }

    private void enemySpawner(EnemyType type) {
        switch (type) {
            case BasicEnemy:
                handler.addObject(new BasicEnemy(r.nextInt((int) (Game.WIDTH - 50)), r.nextInt((int) (Game.HEIGHT - 50)), ID.Enemy, handler));
                break;
            case HardEnemy:
                handler.addObject(new HardEnemy(r.nextInt((int) (Game.WIDTH - 50)), r.nextInt((int) (Game.HEIGHT - 50)), ID.Enemy, handler));
                break;
            case FastEnemy:
                handler.addObject(new FastEnemy(r.nextInt((int) (Game.WIDTH - 50)), r.nextInt((int) (Game.HEIGHT - 50)), ID.Enemy, handler));
                break;
            case SmartEnemy:
                handler.addObject(new SmartEnemy(r.nextInt((int) (Game.WIDTH - 50)), r.nextInt((int) (Game.HEIGHT - 50)), ID.Enemy, handler));
                break;
            case Splitter:
                handler.addObject(new Splitter((int)splitter.x, (int)splitter.y, ID.Enemy, handler,this));
                break;
            case Snake:
                handler.addObject(new Snake((int)splitter.x, (int)splitter.y, ID.Enemy, handler));
                break;
            case BulletMaster:
                handler.addObject(new BulletMaster((Game.WIDTH / 2) - 45, -90, ID.Boss, handler));
                break;
        }
    }

    public void tick() {
        scoreKeep++;
        //System.out.println("Current scoreKeep: " + scoreKeep);
        //System.out.println("Current level: " + hud.getLevel());

        if (isBossLevel(hud.getLevel()) && !bossLvlCompleted) {
            handleBossLevel();
        } else if (scoreKeep >= 400 && !isBossLevel(hud.getLevel())) {
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);
            System.out.println("Transitioned to level: " + hud.getLevel());

            spawnEnemiesForLevel(hud.getLevel());
        }
    }

    private boolean isBossLevel(int level) {
        return level == 10 || level == 20 || level == 30; // Add other boss levels as needed
    }

    private void handleBossLevel() {
        if (scoreKeep >= 2000) {
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);
            handler.clearEnemies();
            bossLvlCompleted = true;
            System.out.println("Transitioned to level: " + hud.getLevel());

            // Spawn enemies for the next level after the boss
            spawnEnemiesForLevel(hud.getLevel());
        }
    }
    
    
    
    
    // Level Editor

    private void spawnEnemiesForLevel(int level) {
    	
    	if(game.diff == 0) {
    		if (level == 2) {
                enemySpawner(EnemyType.BasicEnemy);
            } else if (level == 3) {
                enemySpawner(EnemyType.BasicEnemy);
            } else if (level == 4) {
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.BasicEnemy);
            } else if (level == 5) {
                enemySpawner(EnemyType.FastEnemy);
            } else if (level == 6) {
                enemySpawner(EnemyType.FastEnemy);
                enemySpawner(EnemyType.FastEnemy);
            } else if (level == 7) {
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.SmartEnemy);
            } else if (level == 8) {
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.SmartEnemy);
                enemySpawner(EnemyType.SmartEnemy);
            } else if (level == 9) {
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.FastEnemy);
                enemySpawner(EnemyType.FastEnemy);
            } else if (level == 10) {
                handler.clearEnemies();
                enemySpawner(EnemyType.BulletMaster);
            } else if (level == 11) {
                handler.clearEnemies();
                for (int i = 0; i < 3; i++) {
                    enemySpawner(EnemyType.FastEnemy);
                }
            } else if (level == 12) {
            	for (int i = 0; i < 2; i++) {
            	enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.Snake);
            	}
            } else if (level == 13) {
            	 enemySpawner(EnemyType.SmartEnemy);
                 enemySpawner(EnemyType.SmartEnemy);
               
            }
    	} else if(game.diff == 1) {
    		if (level == 2) {
                enemySpawner(EnemyType.HardEnemy);
            } else if (level == 3) {
                enemySpawner(EnemyType.HardEnemy);
            } else if (level == 4) {
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.BasicEnemy);
            } else if (level == 5) {
                enemySpawner(EnemyType.FastEnemy);
                enemySpawner(EnemyType.FastEnemy);
            } else if (level == 6) {
                enemySpawner(EnemyType.FastEnemy);
                enemySpawner(EnemyType.FastEnemy);
            } else if (level == 7) {
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.SmartEnemy);
            } else if (level == 8) {
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.HardEnemy);
                enemySpawner(EnemyType.SmartEnemy);
                enemySpawner(EnemyType.SmartEnemy);
            } else if (level == 9) {
                enemySpawner(EnemyType.BasicEnemy);
                enemySpawner(EnemyType.HardEnemy);
                enemySpawner(EnemyType.FastEnemy);
                enemySpawner(EnemyType.FastEnemy);
            } else if (level == 10) {
                handler.clearEnemies();
                enemySpawner(EnemyType.BulletMaster);
                enemySpawner(EnemyType.SmartEnemy);
                enemySpawner(EnemyType.SmartEnemy);
            } else if (level == 11) {
                handler.clearEnemies();
                for (int i = 0; i < 6; i++) {
                    enemySpawner(EnemyType.FastEnemy);
                }
            } else if (level == 12) {
            	for (int i = 0; i < 3; i++) {
            	enemySpawner(EnemyType.HardEnemy);
                enemySpawner(EnemyType.HardEnemy);
                enemySpawner(EnemyType.FastEnemy);
                enemySpawner(EnemyType.FastEnemy);
            	}
            } else if (level == 13) {
            	 enemySpawner(EnemyType.SmartEnemy);
                 enemySpawner(EnemyType.SmartEnemy);
               
            }
    	
    	}
    }
}
