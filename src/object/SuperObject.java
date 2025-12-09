package object;

import main.Constant;
import main.GamePanel;
import object.UtilityTools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    // Images
    private BufferedImage image;
    private BufferedImage HP_0, HP_1, HP_2, HP_3, HP_4;

    // Identity & properties
    private String name;
    private boolean collision = false;

    // World position
    private int worldX;
    private int worldY;

    // Collision box (local to the object)
    private Rectangle solidArea = new Rectangle(0, 0, 30, 30);
    private int solidAreaDefaultX = 0;
    private int solidAreaDefaultY = 0;

    // keep tools private, but expose a helper
    private final UtilityTools ut = new UtilityTools();

    // ===== PROTECTED HELPER FOR CHILD CLASSES =====
    protected BufferedImage scaleToTile(BufferedImage src, int tileSize) {
        return ut.scaleImage(src, tileSize, tileSize);
    }

    // ===== GETTERS / SETTERS =====
    public BufferedImage getImage() { return image; }
    public void setImage(BufferedImage image) { this.image = image; }

    public BufferedImage getHP_0() { return HP_0; }
    public void setHP_0(BufferedImage HP_0) { this.HP_0 = HP_0; }

    public BufferedImage getHP_1() { return HP_1; }
    public void setHP_1(BufferedImage HP_1) { this.HP_1 = HP_1; }

    public BufferedImage getHP_2() { return HP_2; }
    public void setHP_2(BufferedImage HP_2) { this.HP_2 = HP_2; }

    public BufferedImage getHP_3() { return HP_3; }
    public void setHP_3(BufferedImage HP_3) { this.HP_3 = HP_3; }

    public BufferedImage getHP_4() { return HP_4; }
    public void setHP_4(BufferedImage HP_4) { this.HP_4 = HP_4; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isCollision() { return collision; }
    public void setCollision(boolean collision) { this.collision = collision; }

    public int getWorldX() { return worldX; }
    public void setWorldX(int worldX) { this.worldX = worldX; }

    public int getWorldY() { return worldY; }
    public void setWorldY(int worldY) { this.worldY = worldY; }

    public Rectangle getSolidArea() { return solidArea; }
    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
    }

    public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
    public int getSolidAreaDefaultY() { return solidAreaDefaultY; }

    // ===== DRAW =====
    public void draw(Graphics2D g2, GamePanel gp) {
        int playerWorldX = gp.player.worldX;
        int playerWorldY = gp.player.worldY;

        int screenX = worldX - playerWorldX + gp.player.getScreenX();
        int screenY = worldY - playerWorldY + gp.player.getScreenY();

        if (
                worldX + Constant.tileSize > playerWorldX - gp.player.getScreenX() &&
                        worldX - Constant.tileSize < playerWorldX + gp.player.getScreenX() &&
                        worldY + Constant.tileSize > playerWorldY - gp.player.getScreenY() &&
                        worldY - Constant.tileSize < playerWorldY + gp.player.getScreenY()
        ) {
            g2.drawImage(image, screenX, screenY, Constant.tileSize, Constant.tileSize, null);
        }
    }
}
