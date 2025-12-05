package inGameEntity.enemies;

import main.GamePanel;
import skills.EnemyProjectile;

import java.awt.Rectangle;
import java.util.ArrayList;

import entity.Entity;

public class BossManticore extends Entity {

    public ArrayList<EnemyProjectile> shots = new ArrayList<>();
    private int shootCooldown = 0;

    public BossManticore(GamePanel gp, int x, int y) {
        super(gp, "Manticore",
            x, y,
            90, 90,
            new Rectangle(24, 24, 32, 32),
            2,
            6,
            150,
            150,
            new boolean[4],
            new int[4]
        );
    }

    public void update() {
        if (!isAlive()) return;

        // meelee fight
        int px = gp.player.worldX;
        int py = gp.player.worldY;

        if (px < worldX) worldX -= getSpeed();
        if (px > worldX) worldX += getSpeed();
        if (py < worldY) worldY -= getSpeed();
        if (py > worldY) worldY += getSpeed();

        // ranged attack
        if (shootCooldown <= 0) {
            shots.add(new EnemyProjectile(worldX, worldY, 4));
            shootCooldown = 40;
        }
        shootCooldown--;

        shots.removeIf(s -> !s.active);
        shots.forEach(s -> s.update(gp));
    }
}
