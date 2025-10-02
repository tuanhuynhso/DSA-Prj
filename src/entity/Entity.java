package entity;

import main.GamePanel;
import java.awt.Rectangle;


public class Entity {
    public GamePanel gp;
    private int worldX, worldY;      // World coordinates
    private int screenX, screenY;    // Screen coordinates
    
    private int speedX = 0;
    private int speedY = 0;

    
    private String direction = "right";
    private int colGap;
    private int rowGap;
    private boolean[] collisionOn = new boolean[4]; // 1 = up, 2 = down, 3 = left, 4 = right check direction of collision
    private int[] collisionTile = new int[4]; // 1 = up, 2 = down, 3 = left, 4 = right check tile number of collision
    private Rectangle collisionBox;
    private boolean isAlive = true;

    public Entity(GamePanel gp) {
        this.gp = gp;
        collisionBox = new Rectangle(0, 0, gp.tileSize/2, gp.tileSize/2); // Default size, can be adjusted
        colGap = (gp.tileSize - collisionBox.width) / 2;
        rowGap = (gp.tileSize - collisionBox.height) / 2;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
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

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getColGap() {
        return colGap;
    }

    public void setColGap(int colGap) {
        this.colGap = colGap;
    }

    public int getRowGap() {
        return rowGap;
    }

    public void setRowGap(int rowGap) {
        this.rowGap = rowGap;
    }

    public boolean[] getCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(int index, boolean collisionOn) {
        this.collisionOn[index] = collisionOn;
    }

    public int[] getCollisionTile() {
        return collisionTile;
    }

    public void setCollisionTile(int index, int collisionTile) {
        this.collisionTile[index] = collisionTile;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    
}
