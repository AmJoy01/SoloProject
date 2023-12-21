package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Slime;
import main.Game;

import static utilz.Constants.EnemyConstants.SLIME;

public class LoadSave {
	public static final String PLAYER = "characters/AnimationSheet_Characters.png";
	public static final String LEVEL = "levels/dungeon_tileset.png";
	public static final String LEVEL_ONE_DATA = "data/level_one_data_long.png";
	public static final String MENU_BACKGROUND = "menu/menu_background.png";
	public static final String MENU_BUTTONS = "menu/button_atlas.png";
	public static final String PAUSE_MENU = "menu/pause_menu_background.png";
	public static final String SOUND_BUTTONS = "menu/sound_button.png";
	public static final String URM_BUTTONS = "menu/pause_menu_buttons.png"; // Unpause, Replay Level, Menu
	public static final String VOLUME_BUTTONS = "menu/volume_buttons.png"; 
	public static final String BACKGROUND_MENU = "levels/1.png";
	public static final String PLAYING_BACKGROUND_IMG = "levels/1.png";
	public static final String BIG_CLOUDS = "levels/3.png";
	public static final String BIG_DARK_CLOUDS = "levels/4.png";
	public static final String HEALTH_BAR = "characters/healthbar.png";	
	public static final String GREEN_SLIME = "characters/green_slime.png";	
	
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
	
	public static ArrayList<Slime> GetSlimes(){
		BufferedImage img = GetSprite(LEVEL_ONE_DATA);
		ArrayList<Slime> list = new ArrayList<>();
		
		for(int j = 0; j < img.getHeight(); j++) {
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if(value == SLIME) {
					list.add(new Slime(i* Game.TILES_SIZE, j * Game.TILES_SIZE));
				}
			}
		} 
		return list;
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
