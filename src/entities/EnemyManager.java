package entities;

import java.awt.image.BufferedImage;
import static utilz.Constants.EnemyConstants.*;
import gamestates.Playing;
import utilz.LoadSave;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] slimeArr;
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
//		loadEnemyImgs();
	}


	private void loadEnemyImgs() {
		slimeArr = new BufferedImage[4][7];
		BufferedImage temp = LoadSave.GetSprite(LoadSave.GREEN_SLIME);
		for(int row = 0; row < slimeArr.length; row++) {
			for(int col = 0; col < slimeArr[col].length; col++) {
				slimeArr[row][col] = temp.getSubimage(col * SLIME_WIDTH_DEFAULT, row * SLIME_HEIGHT_DEFAULT, SLIME_WIDTH_DEFAULT, SLIME_HEIGHT_DEFAULT);
			}
		}
	}
}
