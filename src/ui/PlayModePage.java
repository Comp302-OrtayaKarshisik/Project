package ui;

import ui.Swing.Panels.GamePanel;

import javax.swing.*;

public class PlayModePage extends Page {

    private GamePanel gamePanel;

    public PlayModePage() {
        super();
        initUI();
    }

    @Override
    protected void initUI() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        this.gamePanel = new GamePanel();

        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGame();
    }
}
