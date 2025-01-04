package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WinGamePage extends Page implements ActionListener {

    private JButton playAgainButton;
    private JButton exitButton;
    private ImageIcon icon;

    WinGamePage() {
        super();
        initUI();
    }

    protected void initUI() {
        icon = new ImageIcon("src/assets/LOGO.png");
        JLabel backgroundLabel = new JLabel(icon);
        backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
        backgroundLabel.setVerticalAlignment(JLabel.TOP);
        backgroundLabel.setBackground(Color.black);
        backgroundLabel.setOpaque(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Gongster", Font.BOLD, 25));
        exitButton = new JButton("Main Menu");
        exitButton.setFont(new Font("Gongster", Font.BOLD, 25));
        playAgainButton.setBackground(new Color(101, 67, 33));
        exitButton.setBackground(new Color(101, 67, 33));
        playAgainButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setOpaque(true);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1000, 800));
        this.add(backgroundLabel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);

        playAgainButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playAgainButton) {
            PageManager.getInstance().showNewBuildingModePage();
        }

        if (e.getSource() == exitButton) {
            PageManager.getInstance().showMainMenuPage();
        }
    }
}
