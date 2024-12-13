package UI.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HallDesigner extends JFrame implements ActionListener {
	
    private MainMenu mm = MainMenu.getInstance();
    
    private final JPanel objectChooserPanel = new JPanel();
    private final HallPanel hallPanel = new HallPanel();


    private final JLabel titleLabel = new JLabel("BUILD MODE");
    private final JComboBox objectBox = new JComboBox();
    private final JPanel buttonPanel = new JPanel();
    private final JButton nextHallButton = new JButton("Next");
    private final JButton previousHallButton = new JButton("Back");
    private final JButton exitButton = new JButton("Exit");
    private final JButton playButton = new JButton("Play");
    private final JScrollPane scrollPane = new JScrollPane();
    private final JTextArea infoTextArea = new JTextArea();
    
    
    private int limitcounter;


    public HallDesigner() {
        super("Hall Designer");
        this.setSize(600, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        getContentPane().setLayout(new BorderLayout(0, 0));
        limitcounter = 0;
        
        ImageIcon hallIcon = new ImageIcon("src/assets/hall.png");
        
        
        
        getContentPane().add(objectChooserPanel, BorderLayout.EAST);
        objectChooserPanel.setLayout(new BoxLayout(objectChooserPanel, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentY(Component.TOP_ALIGNMENT);
       
        
        objectChooserPanel.add(titleLabel);
       
        
        objectChooserPanel.add(objectBox);
        
        objectChooserPanel.add(buttonPanel);
        buttonPanel.setLayout(new GridLayout(0, 1));
        
        
        buttonPanel.add(nextHallButton);
        
        buttonPanel.add(previousHallButton);
        buttonPanel.add(exitButton);
        
        buttonPanel.add(playButton);
        
        objectChooserPanel.add(scrollPane);
        
        scrollPane.setViewportView(infoTextArea);
        
        getContentPane().add(hallPanel, BorderLayout.CENTER);

    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource()==nextHallButton) {
            //show next hall
        }

    	else if(e.getSource()==previousHallButton){
    		
        }
    	
    	else if(e.getSource()==exitButton){
    		
        }
    	else if(e.getSource()==playButton){
    		
        }
    
    }
 }
    
    class HallPanel extends JPanel {
    	
    	
    	
    	@Override 
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    	}
    	
    }
    
    
    


