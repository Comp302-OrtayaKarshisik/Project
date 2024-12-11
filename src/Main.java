
import UI.Swing.Panels.GamePanel;
import UI.Swing.Windows.mainscreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //System.out.println("k");
        //mainscreen screen = new mainscreen();

        //Code for movement
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGame();
    }
}
