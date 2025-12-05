package skills;

import java.awt.Point;

import entity.Entity;
import main.GamePanel;

public class AutoAttack extends Skill {
    public AutoAttack(GamePanel gp) {
        super(gp);
        this.name = "Auto Attack";
        this.cooldown = 1d;
        this.manaCost = 0.0d; // no mana cost
    }

    @Override
    public void activate(Entity caster, Point target) {
        // Implementation of auto attack logic
        int damage = caster.getAttackPower();
        Projectile projectile = new Projectile(gp, target, caster, damage, 10, 1000);
        gp.projectileManager.addProjectile(projectile);
    }

}
