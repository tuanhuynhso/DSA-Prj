package main;

import javax.swing.*;

public class Main {
    
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("HCMIU DSA");

        SwingUtilities.invokeLater(GameMenu::new);

        GamePanel panel = new GamePanel();
        window.add(panel);
        window.pack();

        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        panel.start();
    }
}
