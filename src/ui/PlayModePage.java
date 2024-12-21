package ui;

import domain.level.GridDesign;
import ui.Swing.Panels.GamePanel;
import ui.Swing.Panels.HallPanelHolder;

import javax.swing.*;
import java.awt.*;

public class PlayModePage extends Page {

    private HallPanelHolder panelHolder;

    private GridDesign[] gridDesigns;

    private JPanel objectChooserPanel;

    private JPanel buttonPanel;

    private ImageIcon resizedHeartImage;

    public PlayModePage(GridDesign gridDesigns[]) {
        super();
        this.gridDesigns = gridDesigns;
        initUI();
    }

    @Override
    protected void initUI() {

        this.setBackground(new Color(66, 40, 53));

        this.panelHolder = new HallPanelHolder(new GamePanel(gridDesigns, this));
        this.add(panelHolder);

        this.objectChooserPanel = new JPanel();
        this.objectChooserPanel.setPreferredSize(new Dimension(250, 750));
        objectChooserPanel.setLayout(new BorderLayout());
        this.add(objectChooserPanel, BorderLayout.EAST);

        JPanel wrapperPanel = new JPanel(new GridBagLayout()); // Centers its child
        wrapperPanel.setBackground(this.objectChooserPanel.getBackground()); // Match background
        this.objectChooserPanel.add(wrapperPanel, BorderLayout.CENTER);

        this.buttonPanel = new JPanel();
        this.buttonPanel.setPreferredSize(new Dimension(200, 750));
        this.buttonPanel.setBackground(new Color(107, 85, 87));
        this.buttonPanel.setLayout(new GridLayout(0, 1));

        wrapperPanel.add(buttonPanel);

        wrapperPanel.setBackground(new Color(66, 40, 53));

        //buttonPanel.setBorder(new RoundedBorder(20));

        //Add Heart Image
        ImageIcon heartImage = new ImageIcon("src/assets/heart.png");
        Image image = heartImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        this.resizedHeartImage = new ImageIcon(image);


        this.buttonPanel.setLayout(null);

        this.addPauseResumeButton();

        SwingUtilities.invokeLater(panelHolder.getExternalPanel()::requestFocusInWindow);

        panelHolder.getGamePanel().startGame();
    }

    private void addPauseResumeButton() {
        ImageIcon pauseImage = new ImageIcon("src/assets/pause.png");
        Image image1 = pauseImage.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedPauseImage = new ImageIcon(image1);

        ImageIcon exitImage = new ImageIcon("src/assets/exit.png");
        Image image2 = exitImage.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedExitImage = new ImageIcon(image2);

        JLabel label1 = new JLabel(resizedPauseImage);
        label1.setBounds(40, 150, 32, 32);
        this.buttonPanel.add(label1);

        JLabel label2= new JLabel(resizedExitImage);
        label2.setBounds(85, 150, 32, 32);
        this.buttonPanel.add(label2);
    }

    public void displayLives(int lives) {
        this.buttonPanel.removeAll();

        for(int i = 0; i < lives; i++) {
            JLabel imageLabel = new JLabel(resizedHeartImage);

            // Resizing Image
            imageLabel.setBounds(40 + i*45, 300, 32, 32);
            this.buttonPanel.add(imageLabel);
        }

        addPauseResumeButton();

        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

}
