package ui;

import domain.agent.Player;
import domain.level.GridDesign;
import listeners.PlayerListener;
import ui.Swing.Panels.GamePanel;
import ui.Swing.Panels.HallPanelHolder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayModePage extends Page implements PlayerListener {

    private HallPanelHolder panelHolder;

    private GridDesign[] gridDesigns;

    private JPanel objectChooserPanel;

    private JPanel buttonPanel;

    private ImageIcon resizedHeartImage;

    private ArrayList<JLabel> livesIndicators = new ArrayList<JLabel>();

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

        JLabel pauseBtn = new JLabel(resizedPauseImage);
        pauseBtn.setBounds(40, 150, 32, 32);
        this.buttonPanel.add(pauseBtn);

        JLabel exitBtn = new JLabel(resizedExitImage);
        exitBtn.setBounds(85, 150, 32, 32);
        this.buttonPanel.add(exitBtn);
    }

    public void displayLives(int lives) {

        for(int i = livesIndicators.size(); i < lives; i++) {
            JLabel imageLabel = new JLabel(resizedHeartImage);

            // Resizing Image
            imageLabel.setBounds(40 + i*45, 300, 32, 32);
        }

        //this.buttonPanel.add(imageLabel);

        this.addPauseResumeButton();

        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

    public void displayRune() {
        // Rune Image
        ImageIcon runeImage = new ImageIcon("src/assets/rune.png");
        Image image1 = runeImage.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedRuneImage = new ImageIcon(image1);
        JLabel runeLabel = new JLabel(resizedRuneImage);
        runeLabel.setBounds(85, 480, 32, 32);
        this.buttonPanel.add(runeLabel);

        // Rune Collected Text
        JLabel textLabel = new JLabel("Rune Collected!!");
        textLabel.setBounds(25, 430, 200, 20);
        textLabel.setFont(new Font("Serif", Font.BOLD, 22));
        textLabel.setForeground(new Color(40, 20, 20));
        this.buttonPanel.add(textLabel);
    }

    public void subscribe (Player p) {
        p.addListener(this);
    }

    @Override
    public void onHealthEvent(int num) {
        displayLives(num);
    }

    @Override
    public void onRuneEvent() {
        displayRune();
    }
}
