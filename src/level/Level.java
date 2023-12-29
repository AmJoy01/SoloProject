package level;

import static utilz.HelperMethods.GetLevelData;
import static utilz.HelperMethods.GetSlimes;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Slime;
import main.Game;

public class Level {
	
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Slime> slimes;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;	
	}

	private void createEnemies() {
		slimes = GetSlimes(img);
		
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
		
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData(){
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
	
	public ArrayList<Slime> getSlimes(){
		return slimes;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
}
