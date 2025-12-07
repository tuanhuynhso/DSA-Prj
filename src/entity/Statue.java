package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Constant;
import main.GamePanel;

public class Statue {
    GamePanel gp;
    private int worldX, worldY;
    private int screenX, screenY;
    private BufferedImage buttonFImage;

    public Statue(GamePanel gp, int worldX, int worldY) {
        this.gp = gp;
        this.worldX = worldX;
        this.worldY = worldY;
        try {
            buttonFImage = ImageIO.read(getClass().getResourceAsStream("/res/"));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading Button_F.png: " + e.getMessage());
        }
    }

    public void drawInteractable(Graphics g) {
        if (Math.abs(gp.player.worldX - worldX) > Constant.interactDistance ||
                Math.abs(gp.player.worldY - worldY) > Constant.interactDistance) {
            return;
        }

        g.drawImage(buttonFImage, screenX - Constant.tileSize / 3, screenY - Constant.tileSize / 3, Constant.tileSize * 2 / 3,
            Constant.tileSize * 2 / 3, null);
    }

    public void draw(Graphics g) {
        screenX = worldX - gp.screenManagement.getScreenX();
        screenY = worldY - gp.screenManagement.getScreenY();
        g.setColor(Color.green);
        g.fillRect(screenX - Constant.tileSize/ 2, screenY - Constant.tileSize/2, Constant.tileSize, Constant.tileSize);
        drawInteractable(g);
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
}
