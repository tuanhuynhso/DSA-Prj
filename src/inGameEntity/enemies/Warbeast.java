package inGameEntity.enemies;

import main.GamePanel;
import java.awt.Rectangle;

import entity.Entity;

public class Warbeast extends Entity {

    public Warbeast(GamePanel gp, int x, int y) {
        super(
            gp,
            "Warbeast",
            x, y,
            64, 64,                                // kích thước quái
            new Rectangle(20, 20, 24, 24),         // hitbox
            2,                                     // speed
            4,                                     // damage
            30,                                    // hp
            30,                                    // maxHp
            new boolean[4],
            new int[4]
        );
    }

    public void update() {
        if (!isAlive()) return;

        int px = gp.player.worldX;
        int py = gp.player.worldY;

        // hướng đuổi player
        if (px < worldX) worldX -= getSpeed();
        if (px > worldX) worldX += getSpeed();
        if (py < worldY) worldY -= getSpeed();
        if (py > worldY) worldY += getSpeed();
    }
}
