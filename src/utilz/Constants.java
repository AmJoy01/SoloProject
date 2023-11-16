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
