package ui;

import javax.swing.*;

import controllers.BuildingModeHandler;
import domain.level.GameHall;
import domain.objects.ObjectType;
import domain.util.Coordinate;
import domain.Textures;
import ui.Graphics.TileSetImageGetter;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;


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
    //private JButton btnNewButton_1;
    //private JButton btnNewButton_2;

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

        buttonPanel = new JPanel();
        this.objectChooserPanel.add(buttonPanel);
        buttonPanel.setLayout(new GridLayout(0, 1));

        //dynamically create object buttons
        createObjectButtons("src/assets/build_mode_assets");

        deleteButton.setIcon(new ImageIcon("src/assets/delete.png"));
        deleteButton.setBackground(Color.LIGHT_GRAY);
        deleteButton.setForeground(Color.GRAY);
        deleteButton.setContentAreaFilled(false); // Remove background color
        deleteButton.setBorderPainted(false);    // Remove the border

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

    private void createObjectButtons(String folderPath){
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        for (File file : files)
        {
            String fileName = file.getName();
            String baseName = fileName.substring(0, fileName.lastIndexOf("."));
            String enumName = baseName.toUpperCase(); // Assuming ObjectType names match file names in uppercase

            ObjectType objectType = null;
            try {
                objectType = ObjectType.valueOf(enumName);
            } catch (IllegalArgumentException ex) {
                // If there's no matching enum, you may want to skip or log a warning
                System.err.println("No matching ObjectType for file: " + fileName);
                continue;
            }

            JButton objButton = new JButton();
            objButton.setBackground(Color.LIGHT_GRAY);
            objButton.setAlignmentY(Component.TOP_ALIGNMENT);
            objButton.setIcon(new ImageIcon(file.getAbsolutePath()));
            objButton.setContentAreaFilled(false); // Remove background color
            objButton.setBorderPainted(false);    // Remove the border

            // Set the action command to the enum name so we know which object was selected
            objButton.setActionCommand(enumName);
            // Add the same action listener to all object buttons
            objButton.addActionListener(this);

            objectChooserPanel.add(objButton);
        }



    }
    
    private void drawObjectImage() {
    	//buildingModeHandler.placeObject();
    }
    
    public void regenerate() {
    	
    }
    
    public void highlightTile(Coordinate c) {
    	
    }
    
    
    

    
    
    private void initializeChoiceListeners(){
        exitButton.addActionListener(this);
        contButton.addActionListener(this);
        deleteButton.addActionListener(this);
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
                if (buildingModeHandler.areAllHallsComplete()) {
                    //transition to play mode here.
                    System.out.println("All halls complete, going to play mode.");
                }
                else {
                    contButton.setText("Start Game");
                }
            }
            int currentHall = buildingModeHandler.getCurrentGameHall() + 1;
            infoTextArea.setText("Hall " +currentHall);
            hallPanel.repaint();
        }
        else if (e.getSource() == deleteButton) {
            System.out.println("Trashcan selected.");
            buildingModeHandler.setSelectedObject(null); // Set no object as selected to enable removal
        }
        else
        {
            String command = e.getActionCommand();
            if (command != null) {
                try {
                    ObjectType selectedObject = ObjectType.valueOf(command);
                    buildingModeHandler.setSelectedObject(selectedObject);
                    System.out.println(selectedObject + " selected.");
                } catch (IllegalArgumentException ex) {
                    // If for some reason we fail here, just ignore
                    System.err.println("Unknown object type selected: " + command);
                }
        }

    }


    
    
    
 }

class HallPanel extends JPanel implements MouseListener {

    public final int tileSize = 48;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 16;

    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    private final BuildingModeHandler buildingModeHandler;

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
                } else {
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
        BufferedImage floor = TileSetImageGetter.getInstance().getFloorImage();
        g.drawImage(floor,0, 0,maxScreenRow*tileSize, maxScreenCol*tileSize, null);
        // Draw walls
        BufferedImage horizontalWall = TileSetImageGetter.getInstance().getImage(0, 0);
        BufferedImage verticalWall = Textures.getSprite("verticalWall");
        drawHorizontalWalls(g, horizontalWall);
        drawVerticalWalls(g, verticalWall);
	}

    public void drawHorizontalWalls(Graphics g, BufferedImage horizontalWall) {
        int start = 20;
        for (int row = 0; row < maxScreenRow-1; row++){
            g.drawImage(horizontalWall,row*tileSize + start,10,tileSize,tileSize,null);
            g.drawImage(horizontalWall,row*tileSize + start,700,tileSize,tileSize,null);
        }
    }

    public void drawVerticalWalls(Graphics g, BufferedImage verticalWall) {
        int start = 29;
        for (int row = 0; row < (764/88)-1; row++){
            g.drawImage(verticalWall,21,row*88 + start, 7,88,null);
            g.drawImage(verticalWall,734,row*88 + start, 7,88,null);
        }
        g.drawImage(verticalWall,21,6*88+74 + start, 7,88,null);
        g.drawImage(verticalWall,734,6*88+74 + start, 7,88,null);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensures the panel is properly rendered
        drawEmptyHall(g);
        GameHall currentHall = buildingModeHandler.getCurrentHall();
        if (currentHall != null) {
            drawPlacedObjects(g, currentHall);
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
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] != null) {
                    BufferedImage objectSprite = Textures.getSprite(grid[row][col].toString().toLowerCase());
                    g.drawImage(objectSprite, col * tileSize, row * tileSize, tileSize, tileSize, null);
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
        hoveredRow = -1;
        hoveredCol = -1;
        repaint(); // ensure the highlight is cleared
    }
}

}
	
    	
    
    
    
    


