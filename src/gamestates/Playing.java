package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import level.LevelManager;
import main.Game;
import ui.PauseOverlay;

public class Playing extends State implements StateMethods{
	
	private Player player;
	private LevelManager levelManager;
	private PauseOverlay pauseOverlay;
	private boolean paused = false; //show the pause screen or not
	
	private final int UP_KEY = KeyEvent.VK_W;
	private final int LT_KEY = KeyEvent.VK_A;
	private final int RT_KEY = KeyEvent.VK_D;
	private final int JP_KEY = KeyEvent.VK_SPACE;
	
	public Playing(Game game) {
		super(game);
		init();
	}
	
	public void init() {
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int)(32*Game.SCALE), (int)(32*Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay();
	}
	
	@Override
	public void update() {
		levelManager.update();
		player.update();
		pauseOverlay.update();
	}

	@Override
	public void draw(Graphics pen) {
		levelManager.draw(pen);
		player.render(pen);
		pauseOverlay.draw(pen);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)		player.setAttacking(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case LT_KEY: player.setLeft(true); 	break;
		case RT_KEY: player.setRight(true); break;
		case UP_KEY:
		case JP_KEY: player.setJump(true); 	break;
		case KeyEvent.VK_BACK_SPACE: Gamestate.state = Gamestate.MENU;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case LT_KEY: player.setLeft(false);  	break;
		case RT_KEY: player.setRight(false); 	break;
		case UP_KEY:
		case JP_KEY: player.setJump(false); 	break;
		}
		
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}
}
