package utilz;

import static utilz.Constants.EnemyConstants.SLIME;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Slime;
import main.Game;

public class LoadSave {
	public static final String PLAYER = "characters/AnimationSheet_Characters.png";
	public static final String LEVEL = "levels/dungeon_tileset.png";
	public static final String MENU_BACKGROUND = "menu/menu_background.png";
	public static final String MENU_BUTTONS = "menu/buttons_menu.png";
	public static final String PAUSE_MENU = "menu/pause_menu_background.png";
	public static final String SOUND_BUTTONS = "menu/sound_button.png";
	public static final String URM_BUTTONS = "menu/pause_menu_buttons.png"; // Unpause, Replay Level, Menu
	public static final String MR_BUTTONS = "menu/urm_buttons.png"; // Unpause, Replay Level, Menu
	public static final String VOLUME_BUTTONS = "menu/volume_buttons.png"; 
	public static final String BACKGROUND_MENU = "levels/cloud1.png";
	public static final String PLAYING_BACKGROUND_IMG = "levels/cloud1.png";
	public static final String BIG_CLOUDS = "levels/cloud3.png";
	public static final String BIG_DARK_CLOUDS = "levels/cloud4.png";
	public static final String HEALTH_BAR = "characters/healthbar.png";	
	public static final String GREEN_SLIME = "characters/green_slime.png";	
	public static final String COMPLETED_IMG = "menu/completed_level.png";		
	
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
	
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/data");
		File file = null;
		
		try {
			file = new File(url.toURI());
		}catch(Exception e) {
			e.printStackTrace();
		}
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];

			}

		BufferedImage[] imgs = new BufferedImage[filesSorted.length];

		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}

		return imgs;
	}
	
	
	
}
