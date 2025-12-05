package skills;

import main.GamePanel;

public class EnemyProjectile {

    public int x, y;
    public int speed = 4;
    public int damage = 2;
    public boolean active = true;

    public EnemyProjectile(int x, int y, int damage) {
        this.x = x;
        this.y = y;
        this.damage = damage;
    }

    public void update(GamePanel gp) {
        int px = gp.player.worldX;
        int py = gp.player.worldY;

        // bay thẳng đến player
        double dx = px - x;
        double dy = py - y;
        double len = Math.sqrt(dx * dx + dy * dy);

        x += (dx / len) * speed;
        y += (dy / len) * speed;

        // check va chạm player
        if (Math.abs(x - px) < 20 && Math.abs(y - py) < 20) {
            gp.player.damageHp(damage);
            active = false;
        }
    }
}
