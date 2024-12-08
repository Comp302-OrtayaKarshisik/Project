package ui.windows;

import javax.swing.*;
import java.awt.*;

public class WindowManager {

    private static WindowManager instance;
    private JFrame window;
    private JPanel containerPanel;  // Container to hold panels

    private WindowManager() {
        // Initialize the JFrame and set basic properties
        window = new JFrame("Rokue-like Game");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Initialize the container panel with CardLayout
        containerPanel = new JPanel(new CardLayout());
        window.add(containerPanel);
    }

    public static WindowManager getInstance() {
        if (instance == null) {
            // If no instance exists, create one
            instance = new WindowManager();
        }
        // Return the existing instance
        return instance;
    }

    // Open Main Menu
    public void openMainMenu() {
        MainPanel mp = new MainPanel();
        containerPanel.add(mp, "MainMenu");  // Add panel with a name
        showPanel("MainMenu");
    }

    // Open Build Mode
    public void openBuildMode() {
        BuildPanel bp = new BuildPanel();
        containerPanel.add(bp, "BuildMode");  // Add panel with a name
        showPanel("BuildMode");
    }

    // Switch between panels by their names
    private void showPanel(String panelName) {
        CardLayout layout = (CardLayout) containerPanel.getLayout();
        layout.show(containerPanel, panelName);  // Show the panel based on the name
        window.pack();  // Resize the window to fit the new content
        window.setLocationRelativeTo(null);  // Center the window
        window.setVisible(true);  // Make sure the window is visible
    }
}