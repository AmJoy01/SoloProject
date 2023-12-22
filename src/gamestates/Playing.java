package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import level.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements StateMethods{
	
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private PauseOverlay pauseOverlay;
	private boolean paused = false; //show the pause screen or not
	
	/*********IMAGES**********/
	private BufferedImage backgroundImg, bigClouds, darkClouds;

	/*******CAMERA*******/
	private int xLvlOffset;
	private int leftBorder = (int) (0.3 * Game.GAME_WIDTH); // 30%
	private int rightBorder = (int) (0.7 * Game.GAME_WIDTH); // 70%
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	
	
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
	}
	
	public void init() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(200, 200, (int)(32*Game.SCALE), (int)(32*Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
	}
	
	@Override
	public void update() {
		
		if(paused) {			
			pauseOverlay.update();
		}
		else{
			levelManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData());
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
		}
	}

	private void drawClouds(Graphics pen) {
		for(int i = 0; i < 3; i++) 
			pen.drawImage(bigClouds, i * (int)(bigClouds.getWidth()*Game.SCALE) - (int) (xLvlOffset * 0.4), (int)(50 * Game.SCALE), (int)(bigClouds.getWidth()*Game.SCALE),(int)(bigClouds.getHeight()* Game.SCALE), null);
		
		for(int i = 0; i < 5; i++)
			pen.drawImage(darkClouds, i * (int)(darkClouds.getWidth()*Game.SCALE) - (int) (xLvlOffset * 0.6), (int)(100 * Game.SCALE), (int)(darkClouds.getWidth()*Game.SCALE),(int)(darkClouds.getHeight()* Game.SCALE), null);			
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
		
	}
	
	public void mouseDragged(MouseEvent e) {
		if(paused)		pauseOverlay.mouseDragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(paused)		pauseOverlay.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(paused)		pauseOverlay.mouseReleased(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(paused)		pauseOverlay.mouseMoved(e);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
		case LT_KEY: 	
		case LT_ARROW_KEY:	player.setLeft(true); 	 break;
		case RT_KEY: 	
		case RT_ARROW_KEY: 	player.setRight(true);   break;
		case UP_KEY:
		case JP_KEY: 		player.setJump(true); 	 break;
		case SHIFT_KEY: 	player.setRunning(true); break;
		case ESC_KEY: 		paused = !paused;		 break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case LT_KEY: 	
		case LT_ARROW_KEY: player.setLeft(false); player.setRunning(false);  break;
		case RT_KEY: 	
		case RT_ARROW_KEY: player.setRight(false); player.setRunning(false); break;
		case UP_KEY:
		case JP_KEY: 	   player.setJump(false); 	 break;
//		case SHIFT_KEY:	   player.setRunning(false); break;
		}
		
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
}
