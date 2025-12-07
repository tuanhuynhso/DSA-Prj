package object;

import main.Constant;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Life extends SuperObject {

    public OBJ_Life(GamePanel gp) {
        setName("Life");
        try {
            BufferedImage hp0 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_0.png"));
            BufferedImage hp1 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_1.png"));
            BufferedImage hp2 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_2.png"));
            BufferedImage hp3 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_3.png"));
            BufferedImage hp4 = ImageIO.read(getClass().getResourceAsStream("/Object/HP_4.png"));

            // Scale using the helper from SuperObject
            setHP_0(scaleToTile(hp0, Constant.tileSize));
            setHP_1(scaleToTile(hp1, Constant.tileSize));
            setHP_2(scaleToTile(hp2, Constant.tileSize));
            setHP_3(scaleToTile(hp3, Constant.tileSize));
            setHP_4(scaleToTile(hp4, Constant.tileSize));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
