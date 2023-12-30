package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import entities.Rect;
import level.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.AudioPlayer;
import utilz.LoadSave;

public class Playing extends State implements StateMethods{
	
	private Player player;
	
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private AudioPlayer menuMusic, playingMusic;
	
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	
	
	private boolean paused = false; //show the pause screen or not
	private boolean gameOver;
	private boolean lvlCompleted = false;
	private boolean inMenu = true;
	/*********IMAGES**********/
	private BufferedImage backgroundImg, bigClouds, darkClouds;

	/*******CAMERA*******/
	private int xLvlOffset;
	private int leftBorder = (int) (0.3 * Game.GAME_WIDTH); // 30%
	private int rightBorder = (int) (0.7 * Game.GAME_WIDTH); // 70%
	private int maxLvlOffsetX;
	
	
	/*********INPUT KEYS************/
	private final int UP_KEY = KeyEvent.VK_W;
	
	
	private final int LT_KEY = KeyEvent.VK_A;
	private final int LT_ARROW_KEY = KeyEvent.VK_LEFT;
	
	private final int RT_KEY = KeyEvent.VK_D;
	private final int RT_ARROW_KEY = KeyEvent.VK_RIGHT;
	
	
	private final int JP_KEY = KeyEvent.VK_SPACE;
	private final int SHIFT_KEY = KeyEvent.VK_SHIFT;
	private final int ESC_KEY = KeyEvent.VK_ESCAPE;
	
	public Playing(Game game) {
		super(game);
		init();
		/*Images for Background*/
		backgroundImg = LoadSave.GetSprite(LoadSave.PLAYING_BACKGROUND_IMG);
		bigClouds = LoadSave.GetSprite(LoadSave.BIG_CLOUDS);
		darkClouds = LoadSave.GetSprite(LoadSave.BIG_DARK_CLOUDS);
		
		calcLvlOffset();
		loadStartLevel();
	
		menuMusic = new AudioPlayer("res/audio/sonatina_letsadventure_2Harbingers.wav");
	    playingMusic = new AudioPlayer("res/audio/sonatina_letsadventure_3ToArms.wav");
	    menuMusic.loop();
	}
	
	
	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
	}
	
	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getCurrentLevel());		
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
	}

	public void init() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(200, 200, (int)(32*Game.SCALE), (int)(32*Game.SCALE), this);
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
	}
	
	@Override
	public void update() {
		
		if (paused){
			pauseOverlay.update();
			playingMusic.stop();
		}else if (lvlCompleted){
			levelCompletedOverlay.update();
		}else if(!gameOver) {
			if(inMenu) {
				menuMusic.stop();
				playingMusic.loop();
				inMenu = false;
			}
			levelManager.update();			
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
		}
	}

	
	
	private void checkCloseToBorder() {
		int playerX = (int) player.getHitBox().x;
		int diff = playerX - xLvlOffset;
		
		if(diff > rightBorder)	xLvlOffset += diff - rightBorder;
		else if(diff < leftBorder)	xLvlOffset += diff - leftBorder;
		
		if(xLvlOffset > maxLvlOffsetX) xLvlOffset = maxLvlOffsetX;
		else if(xLvlOffset < 0)	xLvlOffset = 0;
	}

	@Override
	public void draw(Graphics pen) {
		/*Draw background*/
		
		pen.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_WIDTH, null);
		
		drawClouds(pen);
		levelManager.draw(pen, xLvlOffset);
		player.render(pen, xLvlOffset);
		enemyManager.draw(pen, xLvlOffset);
		
		if(paused)	{
			pen.setColor(new Color(0,0,0,150));
			pen.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(pen);
		}else if(gameOver) {
			gameOverOverlay.draw(pen);
		}else if(lvlCompleted) {			
			levelCompletedOverlay.draw(pen);
		}
		
//		debugInfo(pen);
	}

	private void drawClouds(Graphics pen) {
		for(int i = 0; i < 3; i++) 
			pen.drawImage(bigClouds, i * (int)(bigClouds.getWidth()*Game.SCALE) - (int) (xLvlOffset * 0.4), (int)(50 * Game.SCALE), (int)(bigClouds.getWidth()*Game.SCALE),(int)(bigClouds.getHeight()* Game.SCALE), null);
		
		for(int i = 0; i < 5; i++)
			pen.drawImage(darkClouds, i * (int)(darkClouds.getWidth()*Game.SCALE) - (int) (xLvlOffset * 0.6), (int)(100 * Game.SCALE), (int)(darkClouds.getWidth()*Game.SCALE),(int)(darkClouds.getHeight()* Game.SCALE), null);			
	}
	
	public void resetAll() {
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void checkEnemyHit(Rect attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
		
	}
	
	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
			if(paused)		pauseOverlay.mouseDragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {			
			if(paused) {
				pauseOverlay.mousePressed(e);
			}
			else if(lvlCompleted) {
				levelCompletedOverlay.mousePressed(e);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver) {
			if(paused)	{
				pauseOverlay.mouseReleased(e);			
			}else if(lvlCompleted) {
				levelCompletedOverlay.mouseReleased(e);
			}
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
			if(paused)	{
				pauseOverlay.mouseMoved(e);			
			}else if(lvlCompleted) {
				levelCompletedOverlay.mouseMoved(e);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver) {
			gameOverOverlay.keyPressed(e);
			
		}
		else {
			switch (e.getKeyCode()) {
			case LT_KEY: 	
			case LT_ARROW_KEY:	player.setLeft(true); 	 break;
			case RT_KEY: 	
			case RT_ARROW_KEY: 	player.setRight(true);   break;
			case UP_KEY:
			case JP_KEY: 		player.setJump(true); 	 break;
			case SHIFT_KEY: 	player.setRunning(true); break;
			case ESC_KEY: 		
				paused = !paused;
				if (paused) {
                    playingMusic.stop();
                    menuMusic.loop();
                    inMenu = true;
                } else {
                    menuMusic.stop();
                    playingMusic.loop();
                    inMenu = false;
                }
				break;
			}			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver)
			switch (e.getKeyCode()) {
			case LT_KEY: 	
			case LT_ARROW_KEY: player.setLeft(false); player.setRunning(false);  break;
			case RT_KEY: 	
			case RT_ARROW_KEY: player.setRight(false); player.setRunning(false); break;
			case UP_KEY:
			case JP_KEY: 	   player.setJump(false); 	 break;
//			case SHIFT_KEY:	   player.setRunning(false); break;
			}
		
	}

	public void debugInfo(Graphics g) {
        g.setColor(Color.RED);
        Font myFont = new Font ("Courier New", 1, 24);
        g.setFont(myFont);

        g.drawString(String.format("player x,y: %2f,%2f", player.x, player.y),10, 100);
        g.drawString(String.format("player hitbox x,y: %2f,%2f", player.getHitBox().x, player.getHitBox().y),10, 125);
//        g.drawString(String.format("sprite x,y: %2f,%2f", sprite.x, sprite.y),10, 30);
    }
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted;
	}
	
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public EnemyManager getEnemyManeger() {
		return enemyManager;
	}
}
