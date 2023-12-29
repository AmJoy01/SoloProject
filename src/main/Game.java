package main;

import java.awt.Graphics;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import utilz.LoadSave;


public class Game implements Runnable{
	private GameFrame gameFrame;
	private GamePanel gamePanel;
	private Thread t;
	private final int FPS_SET = 120; //FRAME PER SECOND
	private final int UPS_SET = 200; // UPDATES PER SECOND
	
	private Playing playing;
	private Menu menu;
	
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
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		gameLoop();
	}
	
	
	/* Initialize menu and playing in init method to show game */
	public void init() {
		menu = new Menu(this);
		playing = new Playing(this);
	}
	
	public void gameLoop() {
		t = new Thread(this);
		t.start();
	}
	
	public void update() {
		
		switch(Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			//If the game is in Playing Mode, update player and level (so play game)
			playing.update();
			break;
		case OPTIONS:
		case QUIT:
		default:
			System.exit(0);
			break;
		}
	}
	
	public void render(Graphics pen) {
		switch(Gamestate.state) {
		case MENU:
			menu.draw(pen);
			break;
		case PLAYING:
			//If the game is in Playing Mode, draw/render player and level (so play game)
			playing.draw(pen);
			break;
		default:
			break;
		}
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
	
	/* ONLY CARE ABOUT THIS FOR PLAYER */
	public void windowFocusLost() {
		if(Gamestate.state == Gamestate.PLAYING)	playing.getPlayer().resetDirBooleans();
	}
	
	public Menu getMenu() 		{return menu;}
	public Playing getPlaying() {return playing;}
}
