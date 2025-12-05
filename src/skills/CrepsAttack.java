package skills;

import main.Constant;
import main.GamePanel;
import java.awt.Rectangle;

import entity.Crep;
import entity.Player;

public class CrepsAttack {
    private GamePanel gp;
    private Crep caster;
    private int damage;
    private int worldX, worldY;
    private int screenX, screenY;
    private boolean expired = false;
    private int aAduration;
    private int maxAduration = 10;
    private int cooldown = 0;
    private int direction; // 0: left, 1: right, 2: up, 3: down
    private Rectangle attackArea = new Rectangle();

    public CrepsAttack(GamePanel gp, Crep caster, int damage) {
        this.gp = gp;
        this.caster = caster;
        this.damage = damage;
    }

    public void setAttackDirection() {
        int playerx = gp.mainCharacter.worldX;
        int playery = gp.mainCharacter.worldY;
        int dx = Math.abs(playerx - caster.worldX);
        int dy = Math.abs(playery - caster.worldY);
        if (dx < dy) {
            if (playery < caster.worldY) {
                direction = 2;
            } else if (playery > caster.worldY) {
                direction = 3;
            }
        } else {
            if (playerx < caster.worldX) {
                direction = 0;
            } else if (playerx > caster.worldX) {
                direction = 1;
            }
        }
    }

    public void updatePosition(int direction) {
        if (direction == 0) { // left
            worldX = caster.worldX - caster.getWidth() / 2 - Constant.tileSize;
            worldY = caster.worldY - caster.getHeight() / 2 - Constant.tileSize / 2;
        } else if (direction == 1) { // right
            worldX = caster.worldX + caster.getWidth() / 2 + Constant.tileSize - Constant.tileSize;
            worldY = caster.worldY - caster.getHeight() / 2 - Constant.tileSize / 2;
        } else if (direction == 2) { // up
            worldX = caster.worldX - caster.getWidth() / 2 - Constant.tileSize / 2;
            worldY = caster.worldY - caster.getHeight() / 2 - Constant.tileSize;
        } else if (direction == 3) { // down
            worldX = caster.worldX - caster.getWidth() / 2 - Constant.tileSize / 2;
            worldY = caster.worldY + caster.getHeight() / 2 + Constant.tileSize - Constant.tileSize;
        }
    }

    public void updateScreenPosition(int direction) {
        screenX = worldX - gp.screenManagement.getScreenX();
        screenY = worldY - gp.screenManagement.getScreenY();
    }

    public void decreaseCooldown() {
        if (cooldown > 0) {
            cooldown--;
        }
    }

    public void execute() {
        if (cooldown > 0) {
            return;
        }

        aAduration = maxAduration;
        expired = false;

        setAttackDirection();
        updatePosition(direction);
        updateScreenPosition(direction);
        if (direction == 2 || direction == 3)
            attackArea.setFrame(worldX, worldY, Constant.tileSize * 2, Constant.tileSize);
        else
            attackArea.setFrame(worldX, worldY, Constant.tileSize, Constant.tileSize * 2);
        checkAttack();
        cooldown = 60; // Cooldown of 60 frames
    }

    public void checkAttack() {
        Rectangle playerHitBox = gp.mainCharacter.getHitBox();

        if (playerHitBox.intersects(attackArea)) {
            Player.getInstance(gp).takeDamage(caster);
        }

    }

    public void update() {
        if (aAduration > 0) {
            aAduration--;
            updateScreenPosition(direction);
            updatePosition(direction);
        } else {
            expired = true;
        }
    }

    public void draw(java.awt.Graphics g2) {
        if (expired)
            return;
        g2.setColor(java.awt.Color.BLUE);
        if (direction == 2 || direction == 3)
            g2.fillRect(screenX, screenY, Constant.tileSize * 2, Constant.tileSize);
        else
            g2.fillRect(screenX, screenY, Constant.tileSize, Constant.tileSize * 2);
    }

    public Crep getCaster() {
        return caster;
    }

    public void setCaster(Crep caster) {
        this.caster = caster;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public int getaAduration() {
        return aAduration;
    }

    public void setaAduration(int durations) {
        this.aAduration = durations;
    }

    public int getMaxAduration() {
        return maxAduration;
    }

    public int getCooldown() {
        return cooldown;
    }
}
