
import javax.swing.SwingUtilities;

import ui.MainMenuPage;
import ui.PageManager;
import ui.Swing.Panels.GamePanel;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PageManager navigator = PageManager.getInstance();
            navigator.showMainMenuPage();
            navigator.showFrame();

        });
    }
}
