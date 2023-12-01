package utilz;

import static main.Game.TILES_HEIGHT;
import static main.Game.TILES_WIDTH;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.InputStream;


import javax.imageio.ImageIO;

public class LoadSave {
	public static final String PLAYER = "characters/AnimationSheet_Characters.png";
	public static final String LEVEL = "levels/dungeon_tileset.png";
//	public static final String LEVEL_ONE_DATA = "data/level_one_data.png";
	public static final String LEVEL_ONE_DATA = "data/level_one_data_long.png";
	public static final String MENU_BACKGROUND = "menu/menu_background.png";
	public static final String MENU_BUTTONS = "menu/button_atlas.png";
	public static final String PAUSE_MENU = "menu/pause_menu_background.png";
	public static final String SOUND_BUTTONS = "menu/sound_button.png";
	public static final String URM_BUTTONS = "menu/pause_menu_buttons.png"; // Unpause, Replay Level, Menu
	public static final String VOLUME_BUTTONS = "menu/volume_buttons.png"; 
	public static final String BACKGROUND_MENU = "levels/1.png";
	public static BufferedImage GetSprite(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		
		try {			
			img = ImageIO.read(is);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	
	
	public static int[][] GetLevelData(){
		
		BufferedImage img = GetSprite(LEVEL_ONE_DATA);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for(int j = 0; j < img.getHeight(); j++) {
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if(value >= 48) {
					value = 0;
				}
				lvlData[j][i] = value;
			}
		}
		return lvlData;
		
	}
	
}
