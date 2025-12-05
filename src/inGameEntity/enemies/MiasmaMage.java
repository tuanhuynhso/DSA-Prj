package inGameEntity.enemies;

import main.GamePanel;
import skills.EnemyProjectile;

import java.awt.Rectangle;
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
            20,      // hp
            20,
            new boolean[4],
            new int[4]
        );
    }

    public void update() {
        if (!isAlive()) return;

        // nhẹ nhàng di chuyển trái-phải
        worldX += Math.sin(System.currentTimeMillis() / 300.0) * 1;

        // bắn đạn
        if (shootCooldown <= 0) {
            projectiles.add(new EnemyProjectile(worldX, worldY, 3));
            shootCooldown = 60;
        }
        shootCooldown--;

        projectiles.removeIf(p -> !p.active);
        projectiles.forEach(p -> p.update(gp));
    }
}
