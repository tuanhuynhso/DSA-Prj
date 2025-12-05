package utils;

import main.Constant;
import main.GamePanel;

public class ScreenManagement {
    private static ScreenManagement instance = null;

    public static ScreenManagement getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new ScreenManagement(gp);
        }
        return instance;
    }
    private GamePanel gp;

    private float lerp = 0.03f; // smoothing amount
    private float screenX, screenY;

    private ScreenManagement(GamePanel gp) {
        this.gp = gp;
        this.screenX = Constant.screenWidth / 2;
        this.screenY = Constant.screenHeight / 2;
    }

    public void update() {
        screenX+=(gp.player.worldX-screenX-Constant.screenWidth/2 + Constant.tileSize)*lerp;
        screenY+=(gp.player.worldY-screenY-Constant.screenHeight/2 + Constant.tileSize*2)*lerp;
    }

    public int getScreenX() {
        return (int)screenX;
    }

    public int getScreenY() {
        return (int)screenY;
    }
}
