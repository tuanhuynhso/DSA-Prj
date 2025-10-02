package inGameEntity;

import java.awt.Graphics2D;

import entity.Entity;
import main.GamePanel;
import utils.KeyHandler;

public class MainCharacter extends Entity {
    private static MainCharacter instance = null;

    private MainCharacter(GamePanel gp) {
        super(gp);
        setDefaultValue();
    }

    void setDefaultValue() {
        setScreenX(gp.screenWidth / 2);
        setScreenY(gp.screenHeight / 2);
        setWorldX(gp.tileSize * 1); // Initial position in the world
        setWorldY(0);
        setSpeedX(5);
        setSpeedY(5);
    }

    public static MainCharacter getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new MainCharacter(gp);
        }
        return instance;
    }

    // Projectile 
    

    private void handleMovementInput(KeyHandler keyH) {
        if (keyH.leftPressed) {
            if (!getCollisionOn()[0])
                setWorldX(getWorldX() - getSpeedX());
            else
                setWorldX(getCollisionTile()[0] * gp.tileSize - this.getColGap());
        } else if (keyH.rightPressed) {
            if (!getCollisionOn()[1])
                setWorldX(getWorldX() + getSpeedX());
            else
                setWorldX(getCollisionTile()[1] * gp.tileSize - gp.tileSize + this.getColGap() - 1);
        }
        if (keyH.upPressed) {
            if (!getCollisionOn()[2])
                setWorldY(getWorldY() - getSpeedY());
            else
                setWorldY(getCollisionTile()[2] * gp.tileSize - this.getRowGap());
        } else if (keyH.downPressed) {
            if (!getCollisionOn()[3])
                setWorldY(getWorldY() + getSpeedY());
            else
                setWorldY(getCollisionTile()[3] * gp.tileSize - gp.tileSize + this.getRowGap() - 1);
        }

    }

    public void draw(Graphics2D g2) {
        g2.setColor(java.awt.Color.RED);
        g2.fillRect(getScreenX(), getScreenY(), gp.tileSize, gp.tileSize);
    }

    public void update(KeyHandler keyH) {
        handleMovementInput(keyH);
        gp.collisionChecker.checkTile(this);
    }
}
