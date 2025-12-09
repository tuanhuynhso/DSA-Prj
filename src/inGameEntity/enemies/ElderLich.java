package inGameEntity.enemies;

import main.Constant;
import main.GamePanel;
import skills.HomingOrb;

import java.awt.Rectangle;

import entity.Entity;

public class ElderLich extends Entity {
    private HomingOrb orb = null;
    private int cooldown = 0;
    private int castTime = 30;
    private int castCounter = 0;
    private boolean casting = false;

    public ElderLich(GamePanel gp, int x, int y) {
        super(gp, "Elder Lich",
                x, y,
                70, 70,
                new Rectangle(20, 20, 28, 28),
                0, // đứng yên
                0,
                8,
                8,
                new boolean[4],
                new int[4], 200);
        loadSprites();
    }

    private void loadSprites() {
        try {
            left1 = javax.imageio.ImageIO
                    .read(getClass().getResource("/res/monster/elder_lich/lich-left.png"));
            left2 = javax.imageio.ImageIO
                    .read(getClass().getResource("/res/monster/elder_lich/lich-left-cast.png"));
            right1 = javax.imageio.ImageIO
                    .read(getClass().getResource("/res/monster/elder_lich/lich-right.png"));
            right2 = javax.imageio.ImageIO
                    .read(getClass().getResource("/res/monster/elder_lich/lich-right-cast.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDirectionToPlayer() {
        int px = gp.player.worldX;

        if (px < worldX) {
            // Move left
            setDirection(2);
        } else {
            // Move right
            setDirection(3);
        }

    }

    public void update() {
        if (!isAlive())
            return;
        setDirectionToPlayer();
        if (orb == null && cooldown <= 0) {
            orb = new HomingOrb(worldX, worldY, gp);
            casting = true;
            cooldown = 150;
        }
        if (casting) {
            castCounter++;
            if (castCounter >= castTime) {
                // cast complete
                casting = false;
                castCounter = 0;
            }
        }

        if (orb != null) {
            if (cooldown <= 0){
                orb.active = false;
            }
            orb.update(gp);
            if (!orb.active)
                orb = null;
        }

        cooldown--;
    }

    @Override
    public void draw(java.awt.Graphics g2) {
        if (!isAlive())
            return;
        if (orb != null) {
            orb.draw(g2);
        }
        java.awt.image.BufferedImage image = null;
        switch (getDirection()) {
            case 2:
                image = (casting) ? left2 : left1;
                break;
            case 3:
                image = (casting) ? right2 : right1;
                break;
        }
        drawHealthBar(g2, getScreenX(), getScreenY() - 10, getHp(), getMaxHp());
        g2.drawImage(image, getScreenX(), getScreenY(), Constant.tileSize, Constant.tileSize, null);
    }
}
