package utils;

import main.GamePanel;
import java.util.ArrayList;
import entity.Crep;
import main.Constant;

public class CrepsManager {
    private static CrepsManager instance = null;
    public static CrepsManager getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new CrepsManager(gp);
        }
        return instance;
    }

    private ArrayList<Crep> creps = new ArrayList<>();

    private CrepsManager(GamePanel gp) {
        creps.add(new Crep(gp, (int)(4.5*Constant.tileSize), (int)(4.5*Constant.tileSize))); // Placeholder for index 0/
        creps.add(new Crep(gp, 10*Constant.tileSize, 5*Constant.tileSize)); // Placeholder for index 1
        creps.add(new Crep(gp, 15*Constant.tileSize, 10*Constant.tileSize)); // Placeholder for index 2
        creps.add(new Crep(gp, 20*Constant.tileSize, 15*Constant.tileSize)); // Placeholder for index 3
    }

    public void updateCreps() {
        for (int i = 0; i < creps.size(); i++) {
            Crep crep = creps.get(i);
            if (!crep.isAlive()) {
                creps.remove(i);
                i--;
                continue;
            }
            if (!crep.isInFrame()) {
                continue;
            }
            crep.update();
        }
    }

    public void drawCreps(java.awt.Graphics g2) {
        for (Crep crep : creps) {
            if (!crep.isAlive()) {
                continue;
            }
            crep.draw(g2);
        }
    }

    public ArrayList<Crep> getCreps() {
        return creps;
    }
}
