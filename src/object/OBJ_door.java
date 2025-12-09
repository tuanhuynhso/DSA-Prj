package object;

import main.Constant;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_door extends SuperObject {
    GamePanel gp;
    public OBJ_door(GamePanel gp) {
        this.gp = gp;
        setName("door");

        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Object/door_1.png"));
            setImage(scaleToTile(image, Constant.tileSize));
        } catch(IOException e){
            e.printStackTrace();
        }
        setCollision(true);
        //test door: will open if you collect 2 coins
        //update: worked
    }
}
