package main;

public final class Constant {
    public static final int FPS = 30;
    
    public static final int originalTileSize = 32;
    public static final int tileSize = originalTileSize*2;
    public static final int maxScreenCol = 22;
    public static final int maxScreenRow = 16;
    public static final int screenWidth = tileSize * maxScreenCol; // tileSize * maxScreenCol;
    public static final int screenHeight = tileSize * maxScreenRow; // tileSize * maxScreenRow;
    public static final int maxWorldCol = 50; // map
    public static final int maxWorldRow = 25; // map
    public static final int interactDistance = tileSize;
    public static final int cardWidth = 300;
    public static final int cardHeight = 450;
    public static final int cardGap = 30;
    
}
