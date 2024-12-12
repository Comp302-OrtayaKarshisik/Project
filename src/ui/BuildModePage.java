package ui;

import javax.swing.*;

import controllers.BuildingModeHandler;
import domain.Coordinate;
import domain.Textures;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import java.awt.*;



public class BuildModePage extends Page implements ActionListener {
	
	
	private BuildingModeHandler buildingModeHandler; 
		
    private HallPanel hallPanel;
    
    private JPanel objectChooserPanel;
    
    private JPanel buttonPanel;
    
    private JButton exitButton;
    
    private JButton contButton;
    
    private JButton[] buttons;
    
    private int pageNum;
        
    private JLabel titleLabel = new JLabel("BUILD MODE");
    
    private JTextArea infoTextArea;
    
    private JButton btnNewButton_1;
    private JButton btnNewButton_2;

    public BuildModePage() {
    	super();
    	this.buildingModeHandler = new BuildingModeHandler();
    	initUI();
    }
    
    protected void initUI() {
    	
    	exitButton = new JButton("Exit");
        contButton = new JButton("Continue");
    	
        this.setLayout(new BorderLayout(0, 0));
        
        ImageIcon hallIcon = new ImageIcon("src/assets/hall.png");
        
        //hall panel
        this.hallPanel = new HallPanel();
        this.hallPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        this.hallPanel.setBackground(Color.LIGHT_GRAY);
        this.add(hallPanel, BorderLayout.CENTER);

        
        //Object chooser panel
        this.objectChooserPanel = new JPanel();
        this.objectChooserPanel.setPreferredSize(new Dimension(150, 600));
        this.objectChooserPanel.setLayout(new BoxLayout(objectChooserPanel, BoxLayout.Y_AXIS));
        this.objectChooserPanel.setBackground(Color.GRAY);
        this.add(objectChooserPanel, BorderLayout.EAST);
        
        
        
        this.objectChooserPanel.add(titleLabel);
        titleLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        initializeChoiceListeners();
        
        
        btnNewButton_1 = new JButton();
        btnNewButton_1.setBackground(Color.GRAY);
        btnNewButton_1.setIcon(new ImageIcon(BuildModePage.class.getResource("/assets/chest_closed.png")));
        btnNewButton_1.setAlignmentY(Component.TOP_ALIGNMENT);
        btnNewButton_1.setForeground(Color.LIGHT_GRAY);
        btnNewButton_1.setContentAreaFilled(false); // Remove background color
        btnNewButton_1.setBorderPainted(false);    // Remove the border
        
        objectChooserPanel.add(btnNewButton_1);
        
        
        btnNewButton_2 = new JButton();
        btnNewButton_2.setBackground(Color.LIGHT_GRAY);
        btnNewButton_2.setIcon(new ImageIcon(BuildModePage.class.getResource("/assets/column.png")));
        btnNewButton_2.setForeground(Color.GRAY);
        btnNewButton_2.setContentAreaFilled(false); // Remove background color
        btnNewButton_2.setBorderPainted(false);    // Remove the border
        objectChooserPanel.add(btnNewButton_2);
        
        buttonPanel = new JPanel();
        this.objectChooserPanel.add(buttonPanel);
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.add(contButton);
        buttonPanel.add(exitButton);
        
        // Info text area
        
        infoTextArea = new JTextArea(5, 20);
        infoTextArea.setBackground(Color.GRAY);
        infoTextArea.setEditable(false);
        infoTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        scrollPane.setViewportView(infoTextArea);
        objectChooserPanel.add(scrollPane, BorderLayout.SOUTH);
        infoTextArea.append("Hall 1\n");
        
    }
    
    private void drawObjectImage() {
    	//buildingModeHandler.placeObject();
    }
    
    public void regenerate() {
    	
    }
    
    public void highlightTile(Coordinate c) {
    	
    }
    
    
    

    
    
    private void initializeChoiceListeners(){
    
      }
    

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if(e.getSource()==exitButton){
    		//çalışmıyor niye?
    		//this.setVisible(false);
    		//mm.setVisible(true);
        }
    	else if(e.getSource()==contButton){
    		//play view
        }
    
    }
    
    
    
    
 }
    
	class HallPanel extends JPanel implements MouseListener {
		
	
	private BufferedImage backgroundImage;
	
	public Graphics g;

	
	public final int scale = 3;
	public final int tileSize = 48;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 16;
	
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	
	public HallPanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		
	}	
	
	
	public void drawEmptyHall(Graphics g) {
		
		BufferedImage sprite = Textures.getSprite("floor_plain");
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while (x < maxScreenCol && y < maxScreenRow) {
			
			g.drawImage(sprite, x, y, tileSize, tileSize, null);
			
			col++;
			x += tileSize;
			
			if (col == maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += tileSize;
			}
		}
		
		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensures the panel is properly rendered
        Graphics2D g2 = (Graphics2D) g;
        drawEmptyHall(g2);
    }
 	


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}
}
	
    	
    
    
    
    


