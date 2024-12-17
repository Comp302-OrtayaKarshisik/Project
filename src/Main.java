
import javax.swing.SwingUtilities;

import domain.Textures;
import ui.MainMenuPage;
import ui.PageManager;
import ui.PlayModePage;
import ui.Swing.Panels.GamePanel;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Textures.createSprites();
            new PlayModePage(null);

        });
    }
}
