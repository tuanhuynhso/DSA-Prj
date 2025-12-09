package utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter {
    private static MouseManager instance = null;
    public static MouseManager getInstance() {
        if (instance == null) {
            instance = new MouseManager();
        }
        return instance;
    }
    
    public int mouseX, mouseY;
    public boolean clicked = false;

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = false;
    }
}
