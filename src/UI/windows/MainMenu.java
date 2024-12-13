package UI.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainMenu extends JFrame implements ActionListener{
	
    private JButton startButton;
    private JButton helpButton;
    private static MainMenu instance;

    private MainMenu() {
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
        startButton.setForeground(Color.BLACK);
        helpButton.setForeground(Color.black);
        buttonPanel.add(startButton);
        buttonPanel.add(helpButton);
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setOpaque(true);
        // Create the main frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800); // Adjust size as needed
        this.setLayout(new BorderLayout());
        this.add(backgroundLabel, BorderLayout.NORTH); // Add image at the top
        this.add(buttonPanel, BorderLayout.CENTER); // Add buttons at the bottom

        startButton.addActionListener(this);
        helpButton.addActionListener(this);

        this.setVisible(true);

    }

    public static MainMenu getInstance() {
        if (instance == null) {
            // If no instance exists, create one
            instance = new MainMenu();
        }
        // Return the existing instance
        return instance;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startButton) {
            //open build mode
            HallDesigner halldesigner = new HallDesigner();
            halldesigner.setVisible(true);
            this.setVisible(false);
        }

        if(e.getSource()==helpButton){
            //open view help
        }
    }
}

