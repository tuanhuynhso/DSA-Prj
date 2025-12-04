package skills;
import java.awt.Point;
import entity.Entity;
import main.GamePanel;

public abstract class Skill {
    public GamePanel gp;
    public String name;
    public double cooldown;
    public double manaCost;

    public Skill(GamePanel gp) {
        this.gp = gp;
    }

    abstract void activate(Entity caster, Point target);
}
