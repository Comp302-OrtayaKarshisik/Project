package ui;

import javax.swing.*;

import controllers.BuildingModeHandler;
import domain.level.GameHall;
import domain.objects.ObjectType;
import domain.util.Coordinate;
import domain.Textures;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


public class BuildModePage extends Page implements ActionListener {
	
	
	private BuildingModeHandler buildingModeHandler; 
		
    private HallPanel hallPanel;
    
    private JPanel objectChooserPanel;
    
    private JPanel buttonPanel;
    
    private JButton exitButton;
    
    private JButton contButton;

    private JButton deleteButton;
    
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
    	deleteButton = new JButton("Delete");


        this.setLayout(new BorderLayout(0, 0));
        
        ImageIcon hallIcon = new ImageIcon("src/assets/hall.png");
        
        //hall panel
        this.hallPanel = new HallPanel(this.buildingModeHandler);
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
        buttonPanel.add(deleteButton);



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
        deleteButton.addActionListener(this);
      }
    

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if(e.getSource()==exitButton){
    		//çalışmıyor niye?
    		//this.setVisible(false);
    		//mm.setVisible(true);
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
        else if (e.getSource() == deleteButton) {
            System.out.println("Trashcan selected.");
            buildingModeHandler.setSelectedObject(null); // Set no object as selected to enable removal
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

    //the coordinates of the tiles that the mouse is hovered on (these coordinates will be highlighted for ease of use, in build mode.)
    private int hoveredRow = -1;
    private int hoveredCol = -1;

	public HallPanel(BuildingModeHandler buildingModeHandler) {
        this.buildingModeHandler = buildingModeHandler;
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		addMouseListener(this);

        //highlighting hovered tiles.
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int newHoveredCol = x / tileSize;
                int newHoveredRow = y / tileSize;

                //only highlight when the mouse is within hall borders.
                if (x >= 0 && x < screenWidth && y >= 0 && y < screenHeight &&
                        newHoveredRow >= 0 && newHoveredRow < maxScreenRow &&
                        newHoveredCol >= 0 && newHoveredCol < maxScreenCol) {

                    //don't need to repaint when the mouse is on the same tile.
                    if (newHoveredRow != hoveredRow || newHoveredCol != hoveredCol) {
                        hoveredRow = newHoveredRow;
                        hoveredCol = newHoveredCol;
                        repaint(); //repaint for highlighting
                    }
                }
                else{
                    // If outside hall bounds, reset highlight and repaint
                    if (hoveredRow != -1 || hoveredCol != -1) {
                        hoveredRow = -1;
                        hoveredCol = -1;
                        repaint();
                    }
                }
            }
        });
	}	
	
	
	public void drawEmptyHall(Graphics g) {
        BufferedImage floorSprite = Textures.getSprite("floor_plain");
        for (int row = 0; row < maxScreenRow; row++){
            for (int col = 0; col < maxScreenCol; col++){
                g.drawImage(floorSprite,col*tileSize,row*tileSize,tileSize,tileSize,null);
            }
        }
		
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensures the panel is properly rendered
        drawEmptyHall(g);
        GameHall currentHall = buildingModeHandler.getCurrentHall();
        if(currentHall != null) {
            drawPlacedObjects(g,currentHall);
        }
        if (hoveredRow >= 0 && hoveredCol >= 0 && hoveredRow < maxScreenRow && hoveredCol < maxScreenCol) {
            Color prevColor = g.getColor();
            g.setColor(new Color(255, 255, 255, 64)); //transparent white (highlight color.)
            g.fillRect(hoveredCol * tileSize, hoveredRow * tileSize, tileSize, tileSize);
            g.setColor(prevColor); //back to original color.
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

        // If no object is selected, the trashcan mode is active (getSelectedObject == null)
        if (buildingModeHandler.getSelectedObject() == null) {
            boolean removed = buildingModeHandler.getCurrentHall().removeObject(row, col);
            if (removed) {
                repaint();
            }
        } else {
            boolean placed = buildingModeHandler.placeObjectAt(row, col);
            if (placed) {
                repaint();
            }
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
	
    	
    
    
    
    


