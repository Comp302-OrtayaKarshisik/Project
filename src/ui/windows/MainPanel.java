
package ui.windows;
import ui.windows.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainPanel extends JPanel implements ActionListener{
    JButton startButton;
    JButton helpButton;
    MainPanel() {
        // Add the background image
        ImageIcon Icon = new ImageIcon("src/assets/LOGO.png");
        JLabel backgroundLabel = new JLabel(Icon);
        backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
        backgroundLabel.setVerticalAlignment(JLabel.TOP);
        backgroundLabel.setBackground(Color.black);
        backgroundLabel.setOpaque(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20)); // Buttons centered with some space
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Gongster",Font.BOLD,25));
        helpButton = new JButton("Help");
        helpButton.setFont(new Font("Gongster",Font.BOLD,25));
        startButton.setBackground(new Color(101, 67, 33));
        helpButton.setBackground(new Color(101, 67, 33));
        startButton.setForeground(Color.WHITE);
        helpButton.setForeground(Color.WHITE);
        buttonPanel.add(startButton);
        buttonPanel.add(helpButton);
        buttonPanel.setBackground(Color.black);
        buttonPanel.setOpaque(true);
        // Create the main frame
        this.setSize(1000, 800); // Adjust size as needed
        this.setLayout(new BorderLayout());
        this.add(backgroundLabel, BorderLayout.NORTH); // Add image at the top
        this.add(buttonPanel, BorderLayout.CENTER); // Add buttons at the bottom

        startButton.addActionListener(this);
        helpButton.addActionListener(this);

        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startButton) {
            //open build mode
            WindowManager.getInstance().openBuildMode();
        }

        if(e.getSource()==helpButton){
            //open view help
        }
    }

}