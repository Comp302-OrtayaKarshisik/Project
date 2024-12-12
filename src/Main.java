
import javax.swing.SwingUtilities;

import domain.Game;
import ui.MainMenuPage;
import ui.PageManager;

public class Main {
    public static void main(String[] args) {

    	SwingUtilities.invokeLater(() -> {
            PageManager navigator = PageManager.getInstance();
            navigator.showMainMenuPage();
            navigator.showFrame();

        });

    }
}
