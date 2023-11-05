package utilz;

import main.Game;
import entities.*;

public class CollisionMethods extends Rect{

	
	public CollisionMethods(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	public static boolean canMoveHere(double x, double y, double width, double height, int[][]lvlData) {
		if(!isSolid(x,y,lvlData) 				&&
		   !isSolid(x+width, y+height, lvlData) &&
		   !isSolid(x+width, y+height, lvlData) &&
		   !isSolid(x+width, y, lvlData)		&& 
		   !isSolid(x, y+height, lvlData))
		{		
		   return true;
		}
				
				return false;
	}
	
	private static boolean isSolid(double x, double y, int[][]lvlData) {
		if(x < 0 || x >= Game.GAME_WIDTH) return true;
		if(y < 0 || y >= Game.GAME_HEIGHT) return true;
		
		double xIndex = x/Game.TILES_SIZE;
		double yIndex = y/Game.TILES_SIZE;
		int value = lvlData[(int)yIndex][(int)xIndex];
		
		/*NEED TO CHANGE THIS BASED ON TILESET */
		/* USING DEFAULT TILESET WITH 48 TILES (12 COLUMNS AND 4 ROWS) 26 X 14 lvlData */
		if(value >= 48 || value < 0 || value != 11) return true; 
		
		return false;
	}
	
	public static double getEntityXPosNextToWall(Rect hitbox, double xSpeed) {
		int currentTile = (int) (hitbox.x/Game.TILES_SIZE);
		
		if(xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitbox.w);
			return tileXPos + xOffset - 1;
		}else {
			// Left
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static double getEntityYPosUnderRoofOrAboveFloor(Rect hitbox, double airSpeed) {
		int currentTile = (int) (hitbox.y/Game.TILES_SIZE);
		
		if(airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitbox.h);
			return tileYPos + yOffset - 1;
		}else {
			// Jumping
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static boolean isEntityOnFloor(Rect hitbox, int[][] lvlData) {
		// Check the pixel below bottom left and bottom right
		
		if(!isSolid(hitbox.x, hitbox.y + hitbox.h + 1, lvlData) &&
		   !isSolid(hitbox.x+hitbox.w, hitbox.y + hitbox.h+1, lvlData)) 
		{
			return false;
		}
		return true;
	}
}
