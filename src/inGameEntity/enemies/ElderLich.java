package inGameEntity.enemies;

import main.GamePanel;
import skills.HomingOrb;

import java.awt.Rectangle;

import entity.Entity;

public class ElderLich extends Entity {

    private HomingOrb orb = null;
    private int cooldown = 0;

    public ElderLich(GamePanel gp, int x, int y) {
        super(gp, "Elder Lich",
            x, y,
            70, 70,
            new Rectangle(20, 20, 28, 28),
            0,       // đứng yên
            0,
            15,
            15,
            new boolean[4],
            new int[4]
        );
    }

    public void update() {
        if (!isAlive()) return;

        if (orb == null && cooldown <= 0) {
            orb = new HomingOrb(worldX, worldY);
            cooldown = 90;
        }

        if (orb != null) {
            orb.update(gp);
            if (!orb.active) orb = null;
        }

        cooldown--;
    }
}
