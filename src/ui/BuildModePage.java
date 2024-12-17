package ui;

import javax.swing.*;

import controllers.BuildingModeHandler;
import domain.level.GameHall;
import domain.objects.ObjectType;
import domain.util.Coordinate;
import domain.Textures;
import ui.Graphics.TileSetImageGetter;
import ui.Swing.Panels.HallPanelHolder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;


public class BuildModePage extends Page implements ActionListener {
	
	
	private BuildingModeHandler buildingModeHandler; 

    private HallPanelHolder hallPanelHolder;

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

    public  BuildingModeHandler getBuildingModeHandler() {
        return buildingModeHandler;
    }
    
    protected void initUI() {
    	
    	exitButton = new JButton("Exit");
        contButton = new JButton("Continue");
    	
        this.setLayout(new BorderLayout(0, 0));
        
        ImageIcon hallIcon = new ImageIcon("src/assets/hall.png");
        
        //hall panel
        this.hallPanel = new HallPanel(this.buildingModeHandler);
        this.hallPanel.setBorder(BorderFactory.createLineBorder(new Color(40, 20, 30), 3));
        this.hallPanel.setBackground(Color.LIGHT_GRAY);

        //hall panel Holder
        this.hallPanelHolder = new HallPanelHolder(hallPanel);
        this.add(hallPanelHolder, BorderLayout.CENTER);

        
        //Object chooser panel
        this.objectChooserPanel = new JPanel();
        this.objectChooserPanel.setPreferredSize(new Dimension(150, 600));
        this.objectChooserPanel.setLayout(new BoxLayout(objectChooserPanel, BoxLayout.Y_AXIS));
        this.objectChooserPanel.setBackground(Color.GRAY);
        this.add(objectChooserPanel, BorderLayout.EAST);
        
        
        
        this.objectChooserPanel.add(titleLabel);
        titleLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        
        btnNewButton_1 = new JButton();
        btnNewButton_1.setBackground(Color.GRAY);
        btnNewButton_1.setIcon(new ImageIcon("src/assets/chest_closed.png"));
        btnNewButton_1.setAlignmentY(Component.TOP_ALIGNMENT);
        btnNewButton_1.setForeground(Color.LIGHT_GRAY);
        btnNewButton_1.setContentAreaFilled(false); // Remove background color
        btnNewButton_1.setBorderPainted(false);    // Remove the border
        
        objectChooserPanel.add(btnNewButton_1);
        
        
        btnNewButton_2 = new JButton();
        btnNewButton_2.setBackground(Color.LIGHT_GRAY);
        btnNewButton_2.setIcon(new ImageIcon("src/assets/column.png"));
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

        initializeChoiceListeners();
        
    }
    
    private void drawObjectImage() {
    	//buildingModeHandler.placeObject();
    }
    
    public void regenerate() {
    	
    }
    
    public void highlightTile(Coordinate c) {
    	
    }
    
    
    

    
    
    private void initializeChoiceListeners(){
        btnNewButton_2.addActionListener(this);
        btnNewButton_1.addActionListener(this);
        exitButton.addActionListener(this);
        contButton.addActionListener(this);
      }
    

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if(e.getSource()==exitButton){
            System.out.println("Exit button clicked.");
            PageManager.getInstance().showMainMenuPage();
        }
    	else if(e.getSource()==contButton){
            System.out.println("Thank you next.");
            boolean isLastHall = buildingModeHandler.goNextHall();
            if(isLastHall) {
                contButton.setText("Start Game");
            }
            int currentHall = buildingModeHandler.getCurrentGameHall() + 1;
            infoTextArea.setText("Hall " +currentHall);
            hallPanel.repaint();
        }
        else if (e.getSource() == btnNewButton_1) {
            System.out.println("Chest selected.");
            buildingModeHandler.setSelectedObject(ObjectType.CHEST_CLOSED);
        }
        else if (e.getSource() == btnNewButton_2) {
            System.out.println("Column selected.");
            buildingModeHandler.setSelectedObject(ObjectType.COLUMN);
        }

    }
    
    
    
    
 }
    
	class HallPanel extends JPanel implements MouseListener {


	public final int tileSize = 48;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 16;
	
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;

    private final  BuildingModeHandler buildingModeHandler;


	public HallPanel(BuildingModeHandler buildingModeHandler) {
        this.buildingModeHandler = buildingModeHandler;
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		addMouseListener(this);
	}	
	
	
	public void drawEmptyHall(Graphics g) {
        BufferedImage floor = TileSetImageGetter.getInstance().getFloorImage();
        g.drawImage(floor,0, 0,maxScreenRow*tileSize, maxScreenCol*tileSize, null);
        // Draw walls
	}


	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensures the panel is properly rendered
        drawEmptyHall(g);
        GameHall currentHall = buildingModeHandler.getCurrentHall();
        if(currentHall != null) {
            drawPlacedObjects(g,currentHall);
        }
    }

    private void drawPlacedObjects(Graphics g, GameHall hall) {
        System.out.println("Drawing objects");
        ObjectType[][] grid = hall.getGrid();
        for(int row = 0; row < grid.length;row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] != null) {
                    BufferedImage objectSprite = Textures.getSprite(grid[row][col].toString().toLowerCase());
                    g.drawImage(objectSprite, col*tileSize,row*tileSize,tileSize,tileSize,null);
                }
            }
        }
    }



	@Override
	public void mouseClicked(MouseEvent e) {
        int col = e.getX() / tileSize;
        int row = e.getY() / tileSize;
        System.out.println ("Clicked at" + col + ", " + row);

        if(SwingUtilities.isRightMouseButton(e)) {
            if(buildingModeHandler.isObjectPresent(row,col)) {
                JPopupMenu menu = new JPopupMenu();
                JMenuItem removeItem = new JMenuItem("Remove Object");
                removeItem.addActionListener(e1 -> {
                    boolean removed = buildingModeHandler.removeObjectAt(row, col);
                    if (removed) {
                        System.out.println("Object removed.");
                        repaint();
                    }
                });
                menu.add(removeItem);
                menu.show(e.getComponent(), e.getX(), e.getY());  // Show the menu at the mouse position
            }
            repaint();
            return;
        }

        boolean placed = buildingModeHandler.placeObjectAt(row,col);
        if(placed) {
            repaint();
        }

		
		
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
	
    	
    
    
    
    


