 package level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

import static main.Game.*;
import utilz.LoadSave;

public class LevelManager {
	
	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;
	
	
	public LevelManager(Game game) {
		this.game = game;
		importLevelSprite();
		levelOne = new Level(LoadSave.GetLevelData());
	}

//*
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
//*/	

	
	public void draw (Graphics pen) {
		for(int j = 0; j < TILES_HEIGHT; j++) {
			for(int i = 0; i < TILES_WIDTH; i++) {	
				int index = levelOne.getSpriteIndex(i, j);
				pen.drawImage(levelSprite[index], TILES_SIZE*i, TILES_SIZE *j ,TILES_SIZE, TILES_SIZE, null);
			}
		}
	}
	
	public void update() {
		
	}
	
	public Level getCurrentLevel() {
		return levelOne;
	}
}
