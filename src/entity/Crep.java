package entity;

import main.Constant;
import main.GamePanel;
import skills.CrepsAttack;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crep extends Entity {
    private GamePanel gp;

    private boolean inAction = false;

    private CrepsAttack attack;

    public Crep(GamePanel gp, int worldX, int worldY) {
        super(gp, "Creps", worldX, worldY, 46, 46, new Rectangle(46, 46), 2, 5, 2, 2, new boolean[4], new int[4]);
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
        // // refresh collision info for current position
        // gp.collisionChecker.checkTile(this);
        int playerX = player.worldX;
        int playerY = player.worldY;
        // System.out.println("Creps at (" + worldX + ", " + worldY + ") is finding
        // player at (" + playerX + ", " + playerY + ")");
        if (worldX < playerX) {
            // Move right
            setDirection(3);
            if (!getCollisionOn()[1]) {
                worldX += Math.min(getSpeed(), Math.abs(playerX - worldX));
            } else {
                worldX = getCollisionTile()[1] * Constant.tileSize + Constant.tileSize - getWidth() / 2 + getColGap()
                        - 1;
            }
        } else if (worldX > playerX) {
            // Move left
            setDirection(2);
            if (!getCollisionOn()[0]) {
                worldX -= Math.min(getSpeed(), Math.abs(worldX - playerX));
            } else {
                worldX = getCollisionTile()[0] * Constant.tileSize + getWidth() / 2 - getColGap();
            }
        }
        if (worldY < playerY) {
            // Move down
            setDirection(1);
            if (!getCollisionOn()[3]) {
                worldY += Math.min(getSpeed(), Math.abs(playerY - worldY));
            } else {
                worldY = getCollisionTile()[3] * Constant.tileSize + Constant.tileSize - getHeight() / 2 + getRowGap()
                        - 1;
            }
        } else if (worldY > playerY) {
            // Move up
            setDirection(0);
            if (!getCollisionOn()[2]) {
                worldY -= Math.min(getSpeed(), Math.abs(worldY - playerY));
            } else {
                worldY = getCollisionTile()[2] * Constant.tileSize + getHeight() / 2 - getRowGap();
            }
        }

    }

    public void knockBack(int length, int dx, int dy) {
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        dx = (int) ((dx / distance) * length);
        dy = (int) ((dy / distance) * length);

        worldX += dx;
        worldY += dy;
    }

    public void takeDamage(int damage) {
        this.damageHp(damage);
    }

    public void manageAction() {
        if (!attack.isExpired()) {
            inAction = true;
            attack.update();
        } else {
            inAction = false;
        }
    }

    public boolean isInFrame(){
        int screenX = getScreenX();
        int screenY = getScreenY();
        return screenX + getWidth() > 0 && screenX < Constant.screenWidth &&
                screenY + getHeight() > 0 && screenY < Constant.screenHeight;
    }

    public void drawHealthBar(Graphics g, int x, int y, int currentHP, int maxHP) {
        int width = 46;
        int height = 6;

        float hpPercent = (float) (currentHP+1) / (maxHP+1);
        if (hpPercent < 0)
            hpPercent = 0;

        // Background bar4
        g.setColor(Color.darkGray);
        g.fillRect(x, y, width, height);

        // Health portion
        g.setColor(Color.red);
        g.fillRect(x, y, (int) (width * hpPercent), height);

        // Outline
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }

    public void update() {
        if (!isAlive()) {
            return;
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
