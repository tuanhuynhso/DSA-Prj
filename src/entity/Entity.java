package entity;

import main.Constant;
import main.GamePanel;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public GamePanel gp;
    public int worldX, worldY; // World coordinates
    private int width, height;
    private int direction = 0; // 0: up, 1: down, 2: left, 3: right
    private String name;

    private int speed = 0;
    private int attackPower;
    private int hp;
    private int maxHp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // collision
    private Rectangle collisionBox;
    private boolean[] collisionOn = new boolean[4]; // 1 = up, 2 = down, 3 = left, 4 = right check direction of
    private int[] collisionTile = new int[4]; // 1 = up, 2 = down, 3 = left, 4 = right check tile number of collision
    private int colGap;
    private int rowGap;
    private int expValue = 0;

    private boolean isAlive = true;

    public Entity(GamePanel gp, String name, int worldX, int worldY, int width, int height, Rectangle collisionBox,
            int speed, int damage, int hp, int maxHp,
            boolean[] collisionOn,
            int[] collisionTile,
            int expValue) {
        this.gp = gp;
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
        this.collisionBox = collisionBox;
        colGap = (width - collisionBox.width) / 2;
        rowGap = (height - collisionBox.height) / 2;
        this.speed = speed;
        this.attackPower = damage;
        this.hp = hp;
        this.maxHp = maxHp;
        this.isAlive = true;
        this.name = name;
        this.expValue = expValue;
    }

    public void revive() {
        this.hp = this.maxHp;
        this.isAlive = true;
    }

    public void growth() {
        int plevel = gp.player.getLevel();
        this.maxHp += plevel/2;
        this.hp += plevel/2;
        this.attackPower += plevel / 3;
    }

    public void update() {
        // Default implementation does nothing
    }

    public void draw(java.awt.Graphics g2) {
        // Default implementation does nothing
    }

    public void drawHealthBar(java.awt.Graphics g, int x, int y, int currentHP, int maxHP) {        
        int width = this.width;
        int height = 6;
        
        float hpPercent = (float) (currentHP + 1) / (maxHP + 1);
        if (hpPercent < 0)
            hpPercent = 0;
        
        // Background bar4
        g.setColor(new Color(20, 20, 20)); // Darker than grey
        g.fillRect(x - 3, y - 3, width + 6, height + 6);
        
        // Health portion
        g.setColor(Color.RED);
        if (name.equals("Player")) g.setColor(Color.CYAN);
        g.fillRect(x, y, (int) (width * hpPercent), height);

    }

    public void knockBack(int length, int dx, int dy) {
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        dx = (int) ((dx / distance) * length);
        dy = (int) ((dy / distance) * length);

        worldX += dx;
        worldY += dy;
    }

    public boolean isInFrame() {
        int screenX = getScreenX();
        int screenY = getScreenY();
        return screenX + getWidth() > 0 && screenX < Constant.screenWidth &&
                screenY + getHeight() > 0 && screenY < Constant.screenHeight;
    }

    public int getScreenX() {
        return worldX - gp.screenManagement.getScreenX() - width / 2;
    }

    public int getScreenY() {
        return worldY - gp.screenManagement.getScreenY() - height / 2;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speedX) {
        this.speed = speedX;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
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

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int damage) {
        this.attackPower = damage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void damageHp(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
            this.isAlive = false;
        }
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpValue() {
        return expValue;
    }

    public void setExpValue(int expValue) {
        this.expValue = expValue;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            this.hp = 0;
            this.isAlive = false;
            gp.player.gainExp(this.expValue);
        }
    }
}