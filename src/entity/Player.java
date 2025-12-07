package entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Constant;
import main.GamePanel;
import utils.KeyHandler;
import skills.AutoAttack;

import javax.imageio.ImageIO;

public class Player extends Entity {
    private GamePanel gp;
    private static Player instance = null;
    
    public static Player getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new Player(gp);
        }
        return instance;
    }

    private int ticks = 0;
    private int stuntDurations = 0;
    private Rectangle hitBox = new Rectangle(0, 0, 64, 64);
    public AutoAttack autoAttackSkill;
    
    private Player(GamePanel gp) {
        super(gp, "Player", (int) (Constant.tileSize * 1.5), (int) (Constant.tileSize * 1.5),
        Constant.tileSize, Constant.tileSize, new Rectangle(48, 48), 5, 1, 5, 5,
        new boolean[4], new int[4]);
        this.gp = gp;
        autoAttackSkill = new AutoAttack(gp);
        getPlayerSprite();
    }
    

    private void handleMovementInput(KeyHandler keyH) {
        if (keyH.leftPressed) {
            setDirection(2);
            if (!getCollisionOn()[0])
                worldX = worldX - getSpeed();
            else
                worldX = getCollisionTile()[0] * Constant.tileSize + getWidth() / 2 - getColGap();
        } else if (keyH.rightPressed) {
            setDirection(3);
            if (!getCollisionOn()[1])
                worldX = worldX + getSpeed();
            else
                worldX = getCollisionTile()[1] * Constant.tileSize + Constant.tileSize - getWidth() / 2 + getColGap() - 1;
        }
        if (keyH.upPressed) {
            setDirection(0);
            if (!getCollisionOn()[2])
                worldY = worldY - getSpeed();
            else
                worldY = getCollisionTile()[2] * Constant.tileSize + getHeight() / 2 - getRowGap();
        } else if (keyH.downPressed) {
            setDirection(1);
            if (!getCollisionOn()[3])
                worldY = worldY + getSpeed();
            else
                worldY = getCollisionTile()[3] * Constant.tileSize + Constant.tileSize - getHeight() / 2 + getRowGap() - 1;
        }
    }

    public void checkCollisionWithEnemies() {
        for (Crep enemy : gp.crepsManager.getCreps()) {
            if (!enemy.isAlive()) {
                continue;
            }
            Rectangle playerDamageZone = new Rectangle();
            playerDamageZone.width = getWidth();
            playerDamageZone.height = getHeight();
            playerDamageZone.x = worldX;
            playerDamageZone.y = worldY;

            Rectangle enemyBox = new Rectangle();
            enemyBox.width = enemy.getWidth();
            enemyBox.height = enemy.getHeight();
            enemyBox.x = enemy.worldX;
            enemyBox.y = enemy.worldY;

            if (playerDamageZone.intersects(enemyBox)) {
                takeDamage(enemy);
            }
        }
    }

    public void takeDamage(Crep enemy) {
        // Placeholder for getting damaged logic
        knockBack(Constant.tileSize / 2, this.worldX - enemy.worldX, this.worldY - enemy.worldY);
    }

    public void knockBack(int length, int dx, int dy) {
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        dx = (int) ((dx / distance) * length);
        dy = (int) ((dy / distance) * length);

        worldX += dx;
        worldY += dy;

        if (getCollisionOn()[0]) {
            worldX = getCollisionTile()[0] * Constant.tileSize - this.getColGap();
        }
        if (getCollisionOn()[1]) {
            worldX = getCollisionTile()[1] * Constant.tileSize - Constant.tileSize + this.getColGap() - 1;
        }
        if (getCollisionOn()[2]) {
            worldY = getCollisionTile()[2] * Constant.tileSize - this.getRowGap();
        }
        if (getCollisionOn()[3]) {
            worldY = getCollisionTile()[3] * Constant.tileSize - Constant.tileSize + this.getRowGap() - 1;
        }
        stuntDurations = 5;
    }

    public void autoAttack() {
        Point target = gp.mouseTrack.getMouseWorldPosition(gp);
        if (gp.cooldownManager.available(gp.player.autoAttackSkill)) {
            gp.player.autoAttackSkill.activate(this, target);
            gp.cooldownManager.start(gp.player.autoAttackSkill);
        }
    }

    public void ticksController() {
        ticks++;
        autoAttack();
        if (ticks > 60) {
            ticks = 0;
        }
    }

    public void updateHitBox() {
        hitBox.x = worldX + getColGap() / 2;
        hitBox.y = worldY + getRowGap() / 2;
        hitBox.width = Constant.tileSize - getColGap() * 2;
        hitBox.height = Constant.tileSize - getRowGap() * 2;
    }

    public void getPlayerSprite(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-up-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-up-2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-down-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-down-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-left-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-right-2.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void update(KeyHandler keyH) {
        if (stuntDurations > 0) {
            stuntDurations--;
            return;
        }
        handleMovementInput(keyH);
        gp.collisionChecker.checkTile(this);
        checkCollisionWithEnemies();
        updateHitBox();
        ticksController();
        // sprite animation
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (getDirection()) {
            case 0:
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case 1:
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case 2:
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case 3:
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, getScreenX(), getScreenY(), Constant.tileSize, Constant.tileSize, null);
    }

    public int getStuntDurations() {
        return stuntDurations;
    }

    public void setStuntDurations(int stuntDurations) {
        this.stuntDurations = stuntDurations;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

}
