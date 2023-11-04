package main;

import java.awt.Graphics;

import entities.Player;
import level.LevelManager;

public class Game implements Runnable{
	private GameFrame gameFrame;
	private GamePanel gamePanel;
	private Thread t;
	private final int FPS_SET = 120; //FRAME PER SECOND
	private final int UPS_SET = 200; // UPDATES PER SECOND
	private Player player;
	private LevelManager levelManager;
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static double SCALE = 2.0;
	public final static int TILES_WIDTH = 26;
	public final static int TILES_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_HEIGHT;
	
	
	
	public Game() {
		init();
		gamePanel = new GamePanel(this);
		gameFrame = new GameFrame(gamePanel); // Calls the window size from GameFrame class
		gamePanel.requestFocus();
		gameLoop();
	}
	
	public void init() {
		levelManager = new LevelManager(this);
		player = new Player(200, 200, (int)(32*SCALE), (int)(32*SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
	}
	
	public void gameLoop() {
		t = new Thread(this);
		t.start();
	}
	
	public void update() {
		player.update();
		levelManager.update();
	}
	
	public void render(Graphics pen) {
		levelManager.draw(pen);
		player.render(pen);
	}
	
	public void run() {
		double timePerFrame  = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		
		long previousTime = System.nanoTime();
	
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
			
		}
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
