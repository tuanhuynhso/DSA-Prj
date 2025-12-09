package entity;

import main.Constant;
import main.GamePanel;
import skills.CrepsAttack;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crep extends Entity {
    private GamePanel gp;

    private boolean inAction = false;
    private CrepsAttack attack;

    public Crep(GamePanel gp, int worldX, int worldY) {
        super(gp, "Creps", worldX, worldY, 46, 46, new Rectangle(46, 46), 2, 1, 2, 2, new boolean[4], new int[4], 100);
        this.gp = gp;
        this.attack = new CrepsAttack(gp, this, this.getAttackPower());
        this.setColGap(0);
        this.setRowGap(0);
        loadSprites();
    }

    public void loadSprites() {
        try{
            up1 = ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-up-1.png"));
            up2 = ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-up-2.png"));
            down1 = ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-down-1.png"));
            down2 = ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-down-2.png"));
            left1 = ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-left-1.png"));
            left2 = ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-left-2.png"));
            right1 = ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-right-1.png"));
            right2 = ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-right-2.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void findPlayer(Player player) {

        int playerX = player.worldX;
        int playerY = player.worldY;
        if (worldX < playerX) {
            // Move right
            setDirection(3);
            if (!getCollisionOn()[1]) {
                worldX += Math.min(getSpeed(), Math.abs(playerX - worldX));
            } else {
                worldX = getCollisionTile()[1] * Constant.tileSize + Constant.tileSize - getWidth() / 2 + getColGap()- 1;
            }
        } else if (worldX > playerX) {
            setDirection(2);
            if (!getCollisionOn()[0]) {
                worldX -= Math.min(getSpeed(), Math.abs(worldX - playerX));
            } else {
                worldX = getCollisionTile()[0] * Constant.tileSize + getWidth() / 2 - getColGap();
            }
        }
        if (worldY < playerY) {
            setDirection(1);
            if (!getCollisionOn()[3]) {
                worldY += Math.min(getSpeed(), Math.abs(playerY - worldY));
            } else {
                worldY = getCollisionTile()[3] * Constant.tileSize + Constant.tileSize - getHeight() / 2 + getRowGap()
                        - 1;
            }
        } else if (worldY > playerY) {
            setDirection(0);
            if (!getCollisionOn()[2]) {
                worldY -= Math.min(getSpeed(), Math.abs(worldY - playerY));
            } else {
                worldY = getCollisionTile()[2] * Constant.tileSize + getHeight() / 2 - getRowGap();
            }
        }

    }

    public void manageAction() {
        if (!attack.isExpired()) {
            inAction = true;
            attack.update();
        } else {
            inAction = false;
        }
    }

    public void update() {
        if (!isAlive()) {
            return;
        }

        spriteCounter++;
        if (spriteCounter > 15) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        attack.decreaseCooldown();
        manageAction();

        if (!inAction) {
            if (Math.abs(worldX - gp.player.worldX) < Constant.tileSize * 1.2 &&
                    Math.abs(worldY - gp.player.worldY) < Constant.tileSize * 1.2 &&
                    attack.getCooldown() == 0) {
                attack.execute();
                attack.update();
            } else {
                findPlayer(Player.getInstance(gp));
            }
        }
        gp.collisionChecker.checkTile(this);
    }

    public void draw(Graphics g2) {
        BufferedImage image = null;
        switch(getDirection()){
            case 0:
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case 1:
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case 2:
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2){
                image = left2;
                }
                break;
            case 3:
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, getScreenX(), getScreenY(),46 , 46, null);
        drawHealthBar(g2, getScreenX(), getScreenY() - 10, this.getHp(), this.getMaxHp());
        attack.draw(g2);
    }
}
