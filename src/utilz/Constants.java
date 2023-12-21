package utilz;

import main.Game;

public class Constants {
	
	public static class EnemyConstants{
		public static final int SLIME = 0;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int DEAD = 2;
		public static final int ATTACK = 3;
		
		public static final int SLIME_WIDTH_DEFAULT = 32;
		public static final int SLIME_HEIGHT_DEFAULT = 32;
		public static final int SLIME_WIDTH = (int)(SLIME_WIDTH_DEFAULT * Game.SCALE);
		public static final int SLIME_HEIGHT = (int)(SLIME_HEIGHT_DEFAULT * Game.SCALE);
		
		//MIGHT NEED TO DRAW THE OFFSET OF X AND Y
		public static final int SLIME_DRAWOFFSET_X = (int)(26 * Game.SCALE); // start at the end of the camera
		public static final int SLIME_DRAWOFFSET_Y = (int)(9 * Game.SCALE); // start above the platform
		
		
		public static int GetSpriteAmount(int enemyType, int enemyState) {
			switch(enemyType) {
			case SLIME:
				switch(enemyState) {
				case DEAD: 
				case ATTACK:
				case IDLE: return 5;
				case RUNNING: return 7;	
				}
			}
			return 0;
		}
		
		//This is the Enemy's Health
		public static int GetMaxHealth(int enemyType) {
			switch(enemyType) {
			case SLIME: return 10;
			default: 	return 1;
			}
		}
		
		//Damage the Enemies
		public static int GetEnemyDmg(int enemyType) {
			switch(enemyType) {
			case SLIME: return 15;
			default:    return 0;
			}
		}	
	}
	
	public static class Environment{
	
	}
	
	public static class UI{
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
			
		}
		
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}
		
		public static class URMButtons{
			public static final int URM_WIDTH_DEFAULT = 48;
			public static final int URM_HEIGHT_DEFAULT = 16;
			public static final int URM_WIDTH = (int) (URM_WIDTH_DEFAULT * Game.SCALE);
			public static final int URM_HEIGHT = (int) (URM_HEIGHT_DEFAULT * Game.SCALE);
		}
		
		public static class VolumeButtons{
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;
			
			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
			
		}
	}
	
	public static class Directions{
		public static final int LT = 0;
		public static final int RT = 1;
		public static final int UP = 2;
		public static final int DN = 3;
		
	}
	
	public static class PlayerConstants{
		public static final int IDLE = 0;
		public static final int WALKING = 1;
		public static final int RUNNING = 2;
		public static final int JUMP = 3;
		public static final int FALLING = 4;
		public static final int DEAD = 5;		
		public static final int ATTACKING = 6;
		
	
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) {
			case DEAD: 		return 8;
			case RUNNING: 	return 8;
			case ATTACKING: return 8;
			case IDLE: 		return 6;
			case WALKING: 	return 4;
			case FALLING: 	return 2;
			case JUMP: 		return 1;
			default: 		return 1;
			}
		}
	}
}
