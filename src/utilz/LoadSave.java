package utilz;

import static main.Game.TILES_HEIGHT;
import static main.Game.TILES_WIDTH;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.InputStream;


import javax.imageio.ImageIO;

public class LoadSave {
	public static final String PLAYER = "characters/AnimationSheet_Character.png";
	public static final String LEVEL = "levels/outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "data/level_one_data.png";
	
	
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
		
int[][] lvlData = new int[TILES_HEIGHT][TILES_WIDTH];
BufferedImage img = GetSprite(LEVEL_ONE_DATA);

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
