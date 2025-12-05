package skills;

import main.GamePanel;

public class HomingOrb {

    public int x, y;
    public int speed = 2;
    public int damage = 5;
    public boolean active = true;

    public HomingOrb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(GamePanel gp) {
        int px = gp.player.worldX;
        int py = gp.player.worldY;

        double dx = px - x;
        double dy = py - y;
        double len = Math.sqrt(dx * dx + dy * dy);

        x += (dx / len) * speed;
        y += (dy / len) * speed;

        // nổ khi đến gần
        if (Math.abs(x - px) < 10) {
            gp.player.damageHp(damage);
            active = false;
        }
    }
}
