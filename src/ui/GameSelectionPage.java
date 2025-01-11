package ui;

import domain.FileSaveLoadAdapter;
import domain.ISaveLoadAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GameSelectionPage extends Page {
    private BufferedImage backgroundImage;

    private ArrayList<String> savedGameTimes;

    private ISaveLoadAdapter saveLoad;

    public GameSelectionPage() {
        setLayout(new BorderLayout());
        this.setOpaque(false);
        this.savedGameTimes = new ArrayList<>();
        this.saveLoad = new FileSaveLoadAdapter();
        initUI();
    }

    @Override
    protected void initUI() {


        //buttonları name vermemiz lazım identifier atayarak

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        Dimension buttonSize = new Dimension(200, 40);

        /*for (String identifier : savedGameIdentifiers) {
            JButton button = new JButton(identifier);
            button.setPreferredSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setForeground(Color.WHITE); // White text color
            button.setFocusPainted(true); // Remove focus border
            button.setBorderPainted(true); // Remove border
            button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
            button.setBorder(new EmptyBorder(10, 10, 10, 10));
            button.setFont(button.getFont().deriveFont(Font.BOLD, 18f)); // Set text to bold

            button.addActionListener(e -> {

                saveLoad.loadGame();

            });


            buttonPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add some space between buttons
            buttonPanel.add(button);
        }*/


        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Always show vertical scroll bar
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);


        this.add(scrollPane, BorderLayout.CENTER);
    }

}
