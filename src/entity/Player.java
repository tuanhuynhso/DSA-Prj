package entity;

import java.awt.Color;
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
    public int getLife(){
        return getHp();
    }

    public static Player getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Player instance has not been initialized yet.");
        }
        return instance;
    }

    private int ticks = 0;
    private int stuntDurations = 0;
    private Rectangle hitBox = new Rectangle(0, 0, 64, 64);
    public AutoAttack autoAttackSkill;
    private float expScale = 1;
    private int exp = 600;
    private int level = 1;
    private int expToNextLevel = 100;
    private int hpRegenCooldown = 4000; // in ticks
    private int hpRegen = 1; // HP per second

    private Player(GamePanel gp) {
        super(gp, "Player", (int) (Constant.tileSize * 1.5), (int) (Constant.tileSize * 1.5),
                Constant.tileSize, Constant.tileSize, new Rectangle(48, 48), 4, 1, 10, 10,
                new boolean[4], new int[4], 0);
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
                worldX = getCollisionTile()[1] * Constant.tileSize + Constant.tileSize - getWidth() / 2 + getColGap()
                        - 1;
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
                worldY = getCollisionTile()[3] * Constant.tileSize + Constant.tileSize - getHeight() / 2 + getRowGap()
                        - 1;
        }
    }

    public void checkCollisionWithEnemies() {
        for (Entity enemy : gp.enemiesManager.getEnemies()) {
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

    public void takeDamage(Entity enemy) {
        this.damageHp(enemy.getAttackPower());
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
        if (ticks > (int)1e6) {
            ticks = 0;
        }
    }

    public void updateHitBox() {
        hitBox.x = worldX + getColGap() / 2;
        hitBox.y = worldY + getRowGap() / 2;
        hitBox.width = Constant.tileSize - getColGap() * 2;
        hitBox.height = Constant.tileSize - getRowGap() * 2;
    }

    public void getPlayerSprite() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-up-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-up-2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-down-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-down-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-left-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/main_char/medium/med-right-2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(KeyHandler keyH) {
        if (!isAlive())
            return;
        
        if (checkExpForLevelUp()) {
            return;
        }
        if (stuntDurations > 0) {
            stuntDurations--;
            return;
        }
        regenHP();
        handleMovementInput(keyH);
        gp.collisionChecker.checkTile(this);
        checkCollisionWithEnemies();
        updateHitBox();
        ticksController();
        // sprite animation
        spriteCounter++;
        if (spriteCounter > 15) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void regenHP() {
        System.out.println("Ticks: " + ticks);
        if (ticks % hpRegenCooldown == 0) {
            System.out.println("Regenerating HP");
            this.healHp(hpRegen);
        }
    }

    public void healHp(int amount) {
        this.setHp(Math.min(this.getHp() + amount, this.getMaxHp()));
    }

    public void drawHealthBar(Graphics2D g) {
        int barWidth = 250;
        int barHeight = 20;

        float ratio = (float) getHp() / getMaxHp();
        int fill = (int) (barWidth * ratio);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(18f));
        g.drawString("HP", 20, 30);

        g.setColor(Color.GRAY);
        g.fillRect(20, 35, barWidth, barHeight);

        g.setColor(Color.RED);
        g.fillRect(20, 35, fill, barHeight);

        g.setColor(Color.WHITE);
        g.drawRect(20, 35, barWidth, barHeight);
    }

    public void drawExpBar(Graphics2D g) {
        int barWidth = 250;
        int barHeight = 15;

        float ratio = (float) getExp() / getExpToNextLevel();
        int fill = (int) (barWidth * ratio);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(14f));
        g.drawString("EXP", 20, 80);

        g.setColor(Color.GRAY);
        g.fillRect(20, 85, barWidth, barHeight);

        g.setColor(Color.BLUE);
        g.fillRect(20, 85, fill, barHeight);

        g.setColor(Color.WHITE);
        g.drawRect(20, 85, barWidth, barHeight);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(18f));
        g.drawString("LV: " + getLevel(), 20, 130);

    }

    public void draw(Graphics2D g2) {
        if (!isAlive())
            return;
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
        drawHealthBar(g2);
        drawExpBar(g2);
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

    public int getExp() {
        return exp;
    }

    public int getExpToNextLevel() {
        return expToNextLevel;
    }

    public void gainExp(int exp) {
        this.exp += exp * expScale;
    }

    public boolean checkExpForLevelUp() {
        if (this.exp >= this.expToNextLevel) {
            this.exp -= this.expToNextLevel;
            levelUp();
            gp.gameState = GamePanel.GameState.CARD_CHOOSING;
            return true;
        }
        return false;
    }

    public void increaseExpScale(float amount) {
        this.expScale += amount;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
        if (this.level % 5 == 0) {
            gp.enemiesManager.enemiesGrowth();
            System.out.println("Enemies have grown stronger!");
        }
        // freeze the game for player to choose stat increase
        this.expToNextLevel = level * 100;
    }

    public void attackPowerUp(int bonus) {
        this.setAttackPower(this.getAttackPower() + bonus);
    }

    public void maxHpUp(int bonus) {
        this.setMaxHp(this.getMaxHp() + bonus);
        this.setHp(this.getHp() + bonus);
    }

    public void speedUp(int bonus) {
        this.setSpeed(this.getSpeed() + bonus);
    }

    public void attackSpeedUp(int bonus) {
        this.autoAttackSkill.decreaseCooldown(bonus);
    }
}
