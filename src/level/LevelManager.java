 package level;

import static main.Game.TILES_HEIGHT;
import static main.Game.TILES_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import level.Level;
import main.Game;
import utilz.LoadSave;

public class LevelManager {
	
	private Game game;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	
	public LevelManager(Game game) {
		this.game = game;
		importLevelSprite();
		levels = new ArrayList<>();
		buildAllLevels();
	}

	public void loadNextLevel() {
		lvlIndex++;
		if(lvlIndex >= levels.size()) {
			lvlIndex = 0;
			System.out.println("No more levels");
			Gamestate.state = Gamestate.MENU;
		}
		
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManeger().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
	}
	
   private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for(BufferedImage img : allLevels) {
			levels.add(new Level(img));
		}
		
	}

	private void importLevelSprite() {
		BufferedImage img = LoadSave.GetSprite(LoadSave.LEVEL);
		levelSprite = new BufferedImage[48];
		
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 12; i++) {
				int index = j*12 + i;
				levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
			}
		}
		
	}
	
	public void draw (Graphics pen, int lvlOffset) {
		for(int j = 0; j < TILES_HEIGHT; j++) {
			for(int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {	
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				pen.drawImage(levelSprite[index], TILES_SIZE*i - lvlOffset, TILES_SIZE *j ,TILES_SIZE, TILES_SIZE, null);
			}
		}
	}
	
	public void update() {
		
	}
	
	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}
	
	public int getAmountOfLevels() {
		return levels.size();
	}
}
