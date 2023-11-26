package utilz;

import main.Game;

public class Constants {
	
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
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
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
		public static final int ATTACKING = 6;
		
	
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) {
			case RUNNING: return 8;
			case ATTACKING: return 8;
			case WALKING: return 4;
			case FALLING: return 2;
			case JUMP: return 1;
			case IDLE: return 6;
			default: return 1;
			}
		}
	}
}
