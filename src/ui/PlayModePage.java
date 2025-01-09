package ui;

import domain.Game;
import domain.Textures;
import domain.agent.Player;
import domain.collectables.EnchantmentType;
import domain.level.CountDownTimer;
import listeners.GameListener;
import listeners.PlayerListener;
import listeners.TimerListener;
import ui.Swing.Panels.GamePanel;
import ui.Swing.Panels.HallPanelHolder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayModePage extends Page implements PlayerListener, GameListener, TimerListener {

    private HallPanelHolder panelHolder;

    private JPanel objectChooserPanel;

    private JPanel buttonPanel;

    private ImageIcon resizedHeartImage;

    private JLabel pauseResumeBtn;

    private ImageIcon pauseResumeIcon;

    private JLabel runeLabel;

    private JLabel runeText;

    private JLabel timerLabel;

    private JLabel cloakLabel;
    private JLabel cloakCountLabel;
    private int cloakCount = 0;

    private JLabel lureLabel;
    private JLabel lureCountLabel;
    private int lureCount = 0;

    private JLabel revealLabel;
    private JLabel revealCountLabel;
    private int revealCount = 0;

    private ArrayList<JLabel> livesIndicators = new ArrayList<JLabel>();

    private boolean isPaused = false;

    private int collectedRunes = 0;

    private final int totalRunes = 4;

    public PlayModePage() {
        super();
        initUI();

        this.subscribe(Game.getInstance());
        this.subscribe(Game.getInstance().getPlayer());

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


        timerLabel = new JLabel("Seconds: " +
                Game.getInstance().getDungeon().getCurrentHall().getTimer().getInitialTimeRemaining());
        timerLabel.setBounds(40, 400, 200, 20);
        timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        timerLabel.setFont(new Font("Serif", Font.BOLD, 22));
        timerLabel.setForeground(new Color(255, 255, 255));
        this.buttonPanel.add(timerLabel);


        //Add Heart Image
        ImageIcon heartImage = new ImageIcon("src/assets/heart.png");
        Image image = heartImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        this.resizedHeartImage = new ImageIcon(image);

        //Add Ench Labels
        cloakLabel = Textures.createImageLabels("cloak", 0, 570, 32, 32);
        lureLabel = Textures.createImageLabels("lure", 0, 570, 32, 32);
        revealLabel = Textures.createImageLabels("reveal", 0, 570, 32, 32);


        this.buttonPanel.setLayout(null);
        this.addPauseResumeButton();
        this.displayLives(3);
        this.displayInventory();

        //subscribe to all halls timers.
        for (int i = 0; i < 4; i++) {
            CountDownTimer timer = Game.getInstance().getDungeon().getHalls()[i].getTimer();
            this.subscribe(timer);
        }

        SwingUtilities.invokeLater(panelHolder.getExternalPanel()::requestFocusInWindow);
    }

    private void addPauseResumeButton() {
        ImageIcon pauseImage = new ImageIcon("src/assets/pause.png");
        Image image1 = pauseImage.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedPauseImage = new ImageIcon(image1);

        ImageIcon resumeImage = new ImageIcon("src/assets/resume.png");
        Image image2 = resumeImage.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        pauseResumeIcon = new ImageIcon(image2);

        this.pauseResumeBtn = new JLabel(resizedPauseImage);
        this.pauseResumeBtn.setBounds(40, 150, 32, 32);

        pauseResumeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                Game.getInstance().togglePause();
            }
        });

        this.buttonPanel.add(pauseResumeBtn);

        JLabel exitBtn = Textures.createImageLabels("exit",85, 150, 32, 32);
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
        System.out.println("Remaining life: " + lives);

        for(int i = this.livesIndicators.size(); i < lives; i++) {
            JLabel imageLabel = new JLabel(resizedHeartImage);
            imageLabel.setBounds(40 + i*45, 300, 32, 32);

            this.livesIndicators.add(imageLabel);
            this.buttonPanel.add(imageLabel);
        }

        for (int i = this.livesIndicators.size(); i > lives; i--) {
            this.buttonPanel.remove(livesIndicators.get(i-1));
            this.livesIndicators.remove(i-1);
        }

        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

    private void updateTimer(CountDownTimer timer) {
        timerLabel.setText("Seconds: " + timer.getTimeRemaining());
        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

    private void displayRune() {
        // Rune Image
        System.out.println("render rune");
        this.runeLabel = Textures.createImageLabels("rune",85, 480, 32, 32);
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

    private void displayInventory() {
        JLabel inventoryLabel = Textures.createImageLabels("inventory", 20, 480, 160, 234);
        this.buttonPanel.setComponentZOrder(inventoryLabel, 1);
        this.buttonPanel.add(inventoryLabel);
    }

    private void addEnchToBag(EnchantmentType type) {
        int numEnch = cloakCount + lureCount + revealCount;
        int numLabel;
        JLabel enchLabel;

        switch(type) {
            case Cloak:
                enchLabel = cloakLabel;
                numLabel = ++cloakCount;
                break;
            case Luring:
                enchLabel = lureLabel;
                numLabel = ++lureCount;
                break;
            default:
                enchLabel = revealLabel;
                numLabel = ++revealCount;
                break;
        }

        int xPlace = numEnch % 3;
        int xOffset = 52;
        int yOffset = numEnch >= 3 ? 33 : 0;

        if(numLabel == 0) {
            enchLabel.setBounds(xOffset + 33 * xPlace, 570 + yOffset, 32, 32);
            this.buttonPanel.add(enchLabel);
            this.buttonPanel.setComponentZOrder(enchLabel, 1);
        }
        else if(numLabel == 1) {

        }
        else{

        }
        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

    private void removeEnchFromBag(EnchantmentType type) {

        switch(type) {
            case Cloak :
                break;
        }

        this.buttonPanel.remove(runeLabel);

        this.buttonPanel.revalidate();
        this.buttonPanel.repaint();
    }

    public void subscribe (Player p) {
        p.addListener(this);
    }

    private void subscribe(Game game) {
        game.addListener(this);
    }

    private void subscribe(CountDownTimer timer) {
        timer.addListener(this);
    }

    @Override
    public void onHealthEvent(int num) {
        if (num == 0) {
            PageManager.getInstance().showGameOverPage();
        }
        displayLives(num);
    }


    @Override
    public void onTimerEvent(CountDownTimer timer) {
        updateTimer(timer);

        if (timer.getTimeRemaining() <= 0) {
            //PageManager.getInstance().showGameOverPage();
        }
    }



    @Override
    public void onRuneEvent(boolean hasRune) {
        if(hasRune) {
            displayRune();
            collectedRunes++;
            if (collectedRunes == totalRunes) {
                PageManager.getInstance().showWinGamePage();
            }
        }
        else {
            removeRune();
        }
        this.panelHolder.setDoorOpen(hasRune);
    }

    @Override
    public void onCollectEnch(EnchantmentType type) {
        this.addEnchToBag(type);
    }

    @Override
    public void onRemoveEnch(EnchantmentType type) {
        this.removeEnchFromBag(type);
    }

    @Override
    public void onGameEvent(Game game) {
        if(game.isPaused() != isPaused) {
            isPaused = !isPaused;
            this.togglePauseImage();
        }
    }


}
