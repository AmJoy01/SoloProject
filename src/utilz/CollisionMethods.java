package utilz;

import main.Game;

public class CollisionMethods {

	
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
}
