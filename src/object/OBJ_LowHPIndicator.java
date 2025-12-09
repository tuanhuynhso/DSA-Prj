package object;

import main.Constant;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_LowHPIndicator extends SuperObject {
    GamePanel gp;
    public OBJ_LowHPIndicator(GamePanel gp) {
    this.gp = gp;
    setName("pekora");
    try {
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Object/Low_HP.png"));
        setImage(scaleToTile(image, Constant.tileSize));
    }
    catch(IOException e){
        e.printStackTrace();
    }
    }

}
