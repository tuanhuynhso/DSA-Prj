package main;

import java.awt.Color;
import java.awt.Graphics2D;
import utils.CardManager;
import utils.MouseManager;

public class CardChoosingState {
    private GamePanel gp;
    private static CardChoosingState instance = null;
    public static CardChoosingState getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new CardChoosingState(gp);
        }
        return instance;
    }
    private boolean cardsGenerated = false;
    private CardManager cardManager = CardManager.getInstance();

    public CardChoosingState(GamePanel gp) {
        this.gp = gp;
    }

    public void update() {
        if (!cardsGenerated) {
            cardManager.generateCards();
            cardsGenerated = true;
        }
        if (MouseManager.getInstance().clicked) {
            int mouseX = MouseManager.getInstance().mouseX;
            int mouseY = MouseManager.getInstance().mouseY;
            cardManager.checkCardClick(mouseX, mouseY);
            MouseManager.getInstance().clicked = false; // Reset click state
            cardsGenerated = false; // Prepare for next card generation 
            gp.gameState = GamePanel.GameState.PLAYING;            
        }
    }

    public void draw(Graphics2D g2) {
        if (!cardsGenerated) {
            return;
        }
         // Darken the background
        g2.setColor(new Color(0, 0, 0, 150)); // 150 opacity = 60% dark
        g2.fillRect(0, 0, Constant.screenWidth, Constant.screenHeight);
        cardManager.drawCards(g2);
    }
}
