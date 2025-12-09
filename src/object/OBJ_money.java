package object;

import main.Constant;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_money extends SuperObject {
    GamePanel gp;
    public OBJ_money(GamePanel gp) {
        this.gp = gp;
        setName("money");
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Object/money_1.png"));
            setImage(scaleToTile(image, Constant.tileSize));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        setCollision(true);
    }
}
