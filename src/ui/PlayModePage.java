package ui;

import domain.level.GameHall;
import ui.Swing.Panels.GamePanel;
import ui.Swing.Panels.HallPanelHolder;

import javax.swing.*;

public class PlayModePage extends Page {

    private HallPanelHolder panelHolder;
    private GameHall[] gameHalls;

    public PlayModePage(GameHall gameHalls[]) {
        super();
        this.gameHalls = gameHalls;
        initUI();
    }

    @Override
    protected void initUI() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        this.panelHolder = new HallPanelHolder(new GamePanel());

        window.add(panelHolder);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        SwingUtilities.invokeLater(panelHolder.getExternalPanel()::requestFocusInWindow);

        panelHolder.getGamePanel().startGame();
    }
}
