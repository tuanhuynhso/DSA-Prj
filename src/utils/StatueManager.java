package utils;

import java.awt.Point;
import java.util.ArrayList;
import entity.Statue;
import main.Constant;
import main.GamePanel;

public class StatueManager {
    private GamePanel gp;
    private static StatueManager instance = null;

    public static StatueManager getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new StatueManager(gp);
        }
        return instance;
    }

    ArrayList<Statue> statues;

    public StatueManager(GamePanel gp) {
        this.gp = gp;
        statues = new ArrayList<>();
    }

    public void addStatue(Point position) {
        statues.add(new Statue(gp, position.x * Constant.tileSize + Constant.tileSize / 2,
                position.y * Constant.tileSize + Constant.tileSize / 2));
    }

    public void drawStatues(java.awt.Graphics g2) {
        for (Statue statue : statues) {
            statue.draw(g2);
        }
    }
}
