package utilz;

import main.Game;
import entities.*;

public class CollisionMethods extends Rect{

	
	public CollisionMethods(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	public static boolean CanMoveHere(double x, double y, double width, double height, int[][]lvlData) {
		if(!IsSolid(x,y,lvlData) 				&&
		   !IsSolid(x+width, y+height, lvlData) &&
		   !IsSolid(x+width, y+height, lvlData) &&
		   !IsSolid(x+width, y, lvlData)		&& 
		   !IsSolid(x, y+height, lvlData))
		{		
		   return true;
		}
				
				return false;
	}
	
	private static boolean IsSolid(double x, double y, int[][]lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if(x < 0 || x >= maxWidth) return true;
		if(y < 0 || y >= Game.GAME_HEIGHT) return true;
		
		double xIndex = x/Game.TILES_SIZE;
		double yIndex = y/Game.TILES_SIZE;
		int value = lvlData[(int)yIndex][(int)xIndex];
		
		/*NEED TO CHANGE THIS BASED ON TILESET */
		/* USING DEFAULT TILESET WITH 48 TILES (12 COLUMNS AND 4 ROWS) 26 X 14 lvlData */
		if(value >= 48 || value < 0 || value != 11) return true; 
		
		return false;
	}
	
	public static double GetEntityXPosNextToWall(Rect hitbox, double xSpeed) {
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
	
	public static double GetEntityYPosUnderRoofOrAboveFloor(Rect hitbox, double airSpeed) {
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
	
	public static boolean IsEntityOnFloor(Rect hitbox, int[][] lvlData) {
		// Check the pixel below bottom left and bottom right
		
		if(!IsSolid(hitbox.x, hitbox.y + hitbox.h + 1, lvlData) &&
		   !IsSolid(hitbox.x+hitbox.w, hitbox.y + hitbox.h+1, lvlData)) 
		{
			return false;
		}
		return true;
	}
}
