package skills;

import entity.Entity;
import inGameEntity.Crep;
import main.Constant;
import main.GamePanel;
import utils.ScreenManagement;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Projectile {
    private GamePanel gp;
    private Entity caster;
    private ScreenManagement screenM;
    private int damage;
    private int speed;
    private boolean expired = false;
    private int range; // max distance the projectile can travel
    private int[] cor = new int[4];

    public Projectile(GamePanel gp, Point target, Entity caster, int damage, int speed, int range) {
        this.gp = gp;
        this.screenM = ScreenManagement.getInstance(gp);
        this.caster = caster;
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        cor[0] = caster.worldX;
        cor[1] = caster.worldY;
        cor[2] = target.x;
        cor[3] = target.y;
    }
    
    public int getTravelledDistance() {
        int dx = cor[0] - caster.worldX;
        int dy = cor[1] - caster.worldY;
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    public void update() {
        int dx = cor[2] - cor[0];
        int dy = cor[3] - cor[1];
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed) {
            expired = true; // reached the target
            return;
        }

        double ratio = speed / distance;
        cor[0] += (int) (dx * ratio);
        cor[1] += (int) (dy * ratio);
        checkIfExpired();
    }

    public void checkIfExpired() {
        checkIfWalled();
        checkIfHitEntity();
        if (getTravelledDistance() >= range) {
            expired = true;
        }
    }

    public void checkIfHitEntity() {
        for (Crep crep : gp.crepsManager.getCreps()) {
            if (expired)
                return;
            // Simple bounding box collision detection
            int entityX = crep.worldX;
            int entityY = crep.worldY;
            if (cor[0] >= entityX - 23 && cor[0] <= entityX + 23 &&
                    cor[1] >= entityY - 23 && cor[1] <= entityY + 23) {
                expired = true;
                crep.takeDamage(damage);
                crep.knockBack(20, cor[2] - cor[0], cor[3] - cor[1]);
                return;
            }
        }
    }

    public void checkIfWalled() {
        // Implement wall collision logic here
        for (int i = screenM.getScreenX() / Constant.tileSize; i < Constant.maxWorldCol; i++) {
            for (int j = screenM.getScreenY() / Constant.tileSize; j < Constant.maxWorldRow; j++) {
                if (expired)
                    return;
                if (i < 0 || j < 0 || i >= Constant.maxWorldCol || j >= Constant.maxWorldRow) {
                    continue;
                }
                int tileNum = gp.tileM.getMapTileNum(i, j);
                if (gp.tileM.getTile(tileNum).isCollision()) {
                    // Simple bounding box collision detection
                    int tileX = i * Constant.tileSize;
                    int tileY = j * Constant.tileSize;
                    if (cor[0] < tileX + Constant.tileSize - 10 && cor[0] > tileX - 10 &&
                            cor[1] < tileY + Constant.tileSize - 10 && cor[1] > tileY - 10) { // Assuming projectile size 5x5
                        expired = true;
                        return;
                    }
                }
            }
        }
    }


    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(cor[0] - screenM.getScreenX(), cor[1] - screenM.getScreenY(), 15, 15); // Example
    
    }

    public void setCaster(Entity caster) {
        this.caster = caster;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int[] getCor() {
        return cor;
    }

    public void setCor(int[] cor) {
        this.cor = cor;
    }

}
