package ui;

import domain.Game;
import domain.agent.Player;
import listeners.GameListener;
import listeners.PlayerListener;
import ui.Swing.Panels.GamePanel;
import ui.Swing.Panels.HallPanelHolder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayModePage extends Page implements PlayerListener, GameListener {

    private HallPanelHolder panelHolder;

    private JPanel objectChooserPanel;

    private JPanel buttonPanel;

    private ImageIcon resizedHeartImage;

    private JLabel pauseResumeBtn;

    private ImageIcon pauseResumeIcon;

    private JLabel runeLabel;

    private JLabel runeText;

    private ArrayList<JLabel> livesIndicators = new ArrayList<JLabel>();

    private boolean isPaused = false;

    public PlayModePage() {
        super();
        initUI();
        Game.getInstance().startGame();
    }

    @Override
    protected void initUI() {

        this.setBackground(new Color(66, 40, 53));

        this.panelHolder = new HallPanelHolder(new GamePanel());
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

        this.displayLives(3);

        this.subscribe(Game.getInstance());
        this.subscribe(Game.getInstance().getPlayer());

        SwingUtilities.invokeLater(panelHolder.getExternalPanel()::requestFocusInWindow);
    }

    private void addPauseResumeButton() {
        ImageIcon pauseImage = new ImageIcon("src/assets/pause.png");
        Image image1 = pauseImage.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedPauseImage = new ImageIcon(image1);

        ImageIcon resumeImage = new ImageIcon("src/assets/resume.png");
        Image image2 = resumeImage.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        pauseResumeIcon = new ImageIcon(image2);

        ImageIcon exitImage = new ImageIcon("src/assets/exit.png");
        Image image3 = exitImage.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedExitImage = new ImageIcon(image3);


        this.pauseResumeBtn = new JLabel(resizedPauseImage);
        this.pauseResumeBtn.setBounds(40, 150, 32, 32);

        pauseResumeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                Game.getInstance().togglePause();
            }
        });

        this.buttonPanel.add(pauseResumeBtn);

        JLabel exitBtn = new JLabel(resizedExitImage);
        exitBtn.setBounds(85, 150, 32, 32);
        this.buttonPanel.add(exitBtn);
    }

    private void togglePauseImage() {
        ImageIcon temp = (ImageIcon) this.pauseResumeBtn.getIcon();
        pauseResumeBtn.setIcon(pauseResumeIcon);
        pauseResumeIcon = temp;

        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

    public void displayLives(int lives) {
        if(lives < 0) {
            return;
        }
        System.out.println("Remaining life: " + lives);

        for(int i = this.livesIndicators.size(); i < lives; i++) {
            JLabel imageLabel = new JLabel(resizedHeartImage);

            // Resizing Image
            imageLabel.setBounds(40 + i*45, 300, 32, 32);

            this.livesIndicators.add(imageLabel);
            this.buttonPanel.add(imageLabel);
        }


        for (int i = this.livesIndicators.size()-1; i >= lives; i--) {
            this.buttonPanel.remove(livesIndicators.get(i));
        }


        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

    private void displayRune() {
        // Rune Image
        System.out.println("render rune");
        ImageIcon runeImage = new ImageIcon("src/assets/rune.png");
        Image image1 = runeImage.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedRuneImage = new ImageIcon(image1);

        this.runeLabel = new JLabel(resizedRuneImage);
        runeLabel.setBounds(85, 480, 32, 32);
        this.buttonPanel.add(runeLabel);

        // Rune Collected Text
        this.runeText = new JLabel("Rune Collected!!");
        runeText.setBounds(25, 430, 200, 20);
        runeText.setFont(new Font("Serif", Font.BOLD, 22));
        runeText.setForeground(new Color(40, 20, 20));
        this.buttonPanel.add(runeText);

        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

    private void removeRune() {
        this.buttonPanel.remove(this.runeLabel);
        this.buttonPanel.remove(this.runeText);

        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

    public void subscribe (Player p) {
        p.addListener(this);
    }

    private void subscribe(Game game) {
        game.addListener(this);
    }

    @Override
    public void onHealthEvent(int num) {
        if (num == 0) {
            PageManager.getInstance().showGameOverPage();
        }
        displayLives(num);
    }

    @Override
    public void onRuneEvent(boolean hasRune) {
        if(hasRune) {
            displayRune();
        }
        else {
            removeRune();
        }
        this.panelHolder.setDoorOpen(hasRune);
    }

    @Override
    public void onGameEvent(Game game) {
        if(game.isPaused() != isPaused) {
            isPaused = !isPaused;
            this.togglePauseImage();
        }
    }
}
