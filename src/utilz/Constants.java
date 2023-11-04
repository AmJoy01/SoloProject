package utilz;

public class Constants {
	
	public static class Directions{
		public static final int LT = 0;
		public static final int RT = 1;
		public static final int UP = 2;
		public static final int DN = 3;
		
	}
	
	public static class PlayerConstants{
		public static final int IDLE = 0;
		public static final int WALKING = 2;
		public static final int RUNNING = 3;
		public static final int ATTACKING = 8;
		
	
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) {
			case RUNNING: return 8;
			case ATTACKING: return 8;
			case WALKING: return 4;
			case IDLE: return 2;
			default: return 1;
			}
		}
	}
}
