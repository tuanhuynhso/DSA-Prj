package utils;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.Card;
import entity.Player;
import main.Constant;

public class CardManager {
    private static CardManager instance;
    public static CardManager getInstance() {
        if (instance == null) {
            instance = new CardManager();
        }
        return instance;
    }

    private Card[] cards = new Card[3];

    public void generateCards() {
        Card[] pool = {
            new Card("HP", "Increase Max HP by 1", 1),
            new Card("Speed", "Increase speed by 1", 1),
            new Card("Attack", "Increase ATK by 1", 1),
            new Card("AttackSpeed", "Decrease Auto Attack cooldown by 0.1s", 1),
            new Card("EXP Scale", "Increase EXP gain by 0.5x", 5)
        };
        for (int i = 0; i < 3; i++) {
            cards[i] = pool[(int) (Math.random() * pool.length)];
        }
    }

    public void drawCards(Graphics2D g2) {
        int cardW = Constant.cardWidth;
        int cardH = Constant.cardHeight;

        int startX = (Constant.screenWidth / 2) - (cardW * 3 + Constant.cardGap * 2) / 2;
        int y = (Constant.screenHeight / 2) - (cardH / 2);

        for (int i = 0; i < 3; i++) {
            int x = startX + i * (cardW + Constant.cardGap);

            // Card backgroun
            g2.setColor(new Color(50, 50, 50, 220));
            g2.fillRoundRect(x, y, cardW, cardH, 25, 25);

            // Border
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(x, y, cardW, cardH, 25, 25);

            // Card text
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(50f));
            g2.drawString(cards[i].name, x + 20, y + 90);
            
            g2.setFont(g2.getFont().deriveFont(30f));
            String description = cards[i].description;
            int maxWidth = cardW - 40;
            String[] words = description.split(" ");
            String line = "";
            int lineY = y + 200;
            
            for (String word : words) {
                if (g2.getFontMetrics().stringWidth(line + word) < maxWidth) {
                    line += word + " ";
                } else {
                    g2.drawString(line, x + 20, lineY);
                    lineY += 40;
                    line = word + " ";
                }
            }
            g2.drawString(line, x + 20, lineY);
        }
    }

    public void checkCardClick(int mouseX, int mouseY) {
        int cardW = Constant.cardWidth;
        int cardH = Constant.cardHeight;

        int startX = (Constant.screenWidth / 2) - (cardW * 3 + Constant.cardGap * 2) / 2;
        int y = (Constant.screenHeight / 2) - (cardH / 2);
        if (mouseY >= y && mouseY <= y + cardH) {
            for (int i = 0; i < 3; i++) {
                int x = startX + i * (cardW + Constant.cardGap);
                if (mouseX >= x && mouseX <= x + cardW) {
                    applyCardEffect(cards[i]);
                }
            }
        }
    }

    public void applyCardEffect(Card card) {
        switch (card.name) {
            case "Attack":
                Player.getInstance().attackPowerUp(card.bonus);
                break;
            case "HP":
                Player.getInstance().maxHpUp(card.bonus);
                break;
            case "Speed":
                Player.getInstance().speedUp(card.bonus);
                break;
            case "AttackSpeed":
                Player.getInstance().attackSpeedUp(card.bonus);
                break;
            case "EXP Scale":
                Player.getInstance().increaseExpScale(card.bonus*0.1f);
                break;
            default:
                break;
        }
    }

}
