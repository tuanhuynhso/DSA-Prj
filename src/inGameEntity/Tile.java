package inGameEntity;
import java.awt.Graphics2D;

import main.Constant;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile{
    private int tileNum;
    private BufferedImage tileImage;
    private boolean collision = false;

    public Tile(int tileNum) {
        this.tileNum = tileNum;
        if (tileNum == 1) {
            collision = true;
        }
        this.tileImage = tileImage(tileNum);
    }

    private BufferedImage tileImage(int tileNum) {
        if (tileNum == 2){
            BufferedImage img = new BufferedImage(Constant.tileSize, Constant.tileSize, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g = img.createGraphics();
            g.setColor(new java.awt.Color(128, 128, 128));
            g.fillRect(0, 0, Constant.tileSize, Constant.tileSize);
            g.dispose();
            return img;
        }
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tile" + tileNum + ".png"));
            if (img == null) return null;
            return img;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading tile image: " + e.getMessage());
            return null;
        }
    }

    public void draw(Graphics2D g2d, int x, int y) {
        g2d.drawImage(tileImage, x, y, Constant.tileSize, Constant.tileSize, null);
    }

    public int getTileNum() {
        return tileNum;
    }

    public void setTileNum(int tileNum) {
        this.tileNum = tileNum;
    }

    public BufferedImage getTileImage() {
        return tileImage;
    }

    public void setTileImage(BufferedImage tileImage) {
        this.tileImage = tileImage;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean isCollidable) {
        this.collision = isCollidable;
    }

    public void getTileImage(BufferedImage tileImage) {
        this.tileImage = tileImage;
    }

}
