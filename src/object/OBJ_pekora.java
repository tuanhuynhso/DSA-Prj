package object;

import main.Constant;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_pekora extends SuperObject {
    GamePanel gp;
    public OBJ_pekora(GamePanel gp) {
        this.gp = gp;
        setName("pekora");
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Object/pekora.png"));
            setImage(scaleToTile(image, Constant.tileSize));

        }
        catch(IOException e){
            e.printStackTrace();
        }
        setCollision(false);
    }
}
