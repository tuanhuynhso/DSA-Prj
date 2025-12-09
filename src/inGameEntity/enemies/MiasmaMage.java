package inGameEntity.enemies;

import main.GamePanel;
import skills.EnemyProjectile;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;

public class MiasmaMage extends Entity {

    public ArrayList<EnemyProjectile> projectiles = new ArrayList<>();
    private int shootCooldown = 0;

    public MiasmaMage(GamePanel gp, int x, int y) {
        super(gp, "Miasma Mage",
            x, y,
            60, 60,
            new Rectangle(20, 20, 28, 28),
            1,       // di chuyển nhẹ
            2,       // damage
            5,      // hp
            5,
            new boolean[4],
            new int[4], 100
        );
        loadSprites();
    }

    private void loadSprites() {
        try {
            up1 = javax.imageio.ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-up-1.png"));
            up2 = javax.imageio.ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-up-2.png"));
            down1 = javax.imageio.ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-down-1.png"));
            down2 = javax.imageio.ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-down-2.png"));
            left1 = javax.imageio.ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-left-1.png"));
            left2 = javax.imageio.ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-left-2.png"));
            right1 = javax.imageio.ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-right-1.png"));
            right2 = javax.imageio.ImageIO.read(getClass().getResource("/res/monster/miasma_mage/miasma-right-2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (!isAlive()) return;
        if (spriteCounter++ > 15) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
        // nhẹ nhàng di chuyển trái-phải
        int temp = (int)(Math.sin(System.currentTimeMillis() / 300.0) * 2);
        if (temp < 0)
            setDirection(2);
        else if (temp > 0)
            setDirection(3);
        worldX += temp;

        // bắn đạn
        if (shootCooldown <= 0) {
            projectiles.add(new EnemyProjectile(worldX, worldY, gp.player.worldX, gp.player.worldY, 3, gp));
            shootCooldown = 60;
        }
        shootCooldown--;

        projectiles.removeIf(p -> !p.active);
        projectiles.forEach(p ->{
            p.update();
        });
    }

    public void draw(Graphics g2) {
        if (!isAlive())
            return;
        drawHealthBar(g2, getScreenX(), getScreenY() - 10, this.getHp(), this.getMaxHp());
        BufferedImage image = null;
        switch (getDirection()) {
            case 0:
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case 1:
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case 2:
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case 3:
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, getScreenX(), getScreenY(), getWidth(), getHeight(), null);
        projectiles.forEach(p -> p.draw(g2));
    }
}
