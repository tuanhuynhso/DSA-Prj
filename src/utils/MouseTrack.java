package utils;

import java.awt.MouseInfo;
import java.awt.Point;

public class MouseTrack {
    Point mousePos;
    
    public MouseTrack() {
        mousePos = getMousePosition();
    }

    public Point getMousePosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }
}
