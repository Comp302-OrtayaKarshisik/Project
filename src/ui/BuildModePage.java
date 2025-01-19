package ui;

import javax.swing.*;

import controllers.BuildingModeHandler;
import domain.level.GridDesign;
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
import java.io.File;


public class BuildModePage extends Page implements ActionListener {

	private BuildingModeHandler buildingModeHandler;

    private JPanel buttonPanel;

    private JButton exitButton;

    private JButton contButton;
    
    private JButton[] buttons;

    private double scaleFactor = 0.86;

    private JPanel[] hallPanels;

    private JPanel objectChooserPanel;

    public BuildModePage(BuildingModeHandler buildingModeHandler) {
    	super();

        this.hallPanels = new JPanel[4];
        this.buildingModeHandler = buildingModeHandler;

    	initUI();
    }
    
    protected void initUI() {

        this.setPreferredSize(new Dimension((int)(1115*scaleFactor), (int)(931*scaleFactor)));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);


        this.objectChooserPanel = new JPanel();
        this.objectChooserPanel.setBounds(746, 188, 123, 470);
        this.objectChooserPanel.setLayout(new BoxLayout(objectChooserPanel, BoxLayout.Y_AXIS));
        this.objectChooserPanel.setOpaque(false);
        this.add(objectChooserPanel);

    	exitButton = new JButton("Exit");
        contButton = new JButton("Continue");


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));

        //dynamically create object buttons
        createObjectButtons("src/assets/build_mode_assets");


        for(int i = 0; i < 4; i++) {
            this.hallPanels[i] = new HallPanel(this.buildingModeHandler, i);
            this.hallPanels[i].setFocusable(true);
            this.hallPanels[i].requestFocusInWindow();
            this.hallPanels[i].setOpaque(false);
            this.hallPanels[i].setSize(hallPanels[i].getPreferredSize());
            this.add(hallPanels[i]);
        }

        hallPanels[0].setLocation(421, 97);
        hallPanels[1].setLocation(91, 97);
        hallPanels[2].setLocation(91, 451);
        hallPanels[3].setLocation(421, 451);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage floor = TileSetImageGetter.getInstance().getFloorImage();
        g.drawImage(floor,0, 0,(int)(1115*scaleFactor), (int)(931*scaleFactor), null);
        BufferedImage buildM = Textures.getSprite("buildmode");
        g.drawImage(buildM,0, 0,(int)(1115*scaleFactor), (int)(931*scaleFactor), null);
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
            objButton.setAlignmentY(Component.TOP_ALIGNMENT);
            objButton.setIcon(new ImageIcon(file.getAbsolutePath()));
            objButton.setContentAreaFilled(false); // Remove background color
            objButton.setBorderPainted(false);    // Remove the border

            // Set the action command to the enum name so we know which object was selected
            objButton.setActionCommand(enumName);
            // Add the same action listener to all object buttons
            objButton.addActionListener(this);

            this.objectChooserPanel.add(objButton);
        }
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if(e.getSource()==exitButton){
            System.out.println("Exit button clicked.");
            PageManager.getInstance().showMainMenuPage();
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

    public final int tileSizeX = 18;
    public final int tileSizeY = 15;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 16;

    final int screenWidth = tileSizeX * maxScreenCol;
    final int screenHeight = tileSizeY * maxScreenRow;
    
    //private final double scale = 1.3;
   
    private final  BuildingModeHandler buildingModeHandler;

    //the coordinates of the tiles that the mouse is hovered on (these coordinates will be highlighted for ease of use, in build mode.)
    private int hoveredRow = -1;
    private int hoveredCol = -1;

    private int hallNumber;

    public HallPanel(BuildingModeHandler buildingModeHandler, int hallNumber) {
        this.buildingModeHandler = buildingModeHandler;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        addMouseListener(this);

        this.hallNumber = hallNumber;

        //highlighting hovered tiles.
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int newHoveredCol = x / tileSizeX;
                int newHoveredRow = y / tileSizeY;

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


	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensures the panel is properly rendered
        GridDesign currentHall = buildingModeHandler.getHall(hallNumber);
        if(currentHall != null) {
            drawPlacedObjects(g,currentHall);
        }
        if (hoveredRow >= 0 && hoveredCol >= 0 && hoveredRow < maxScreenRow && hoveredCol < maxScreenCol) {
            Color prevColor = g.getColor();
            g.setColor(new Color(255, 255, 255, 64)); //transparent white (highlight color.)
            g.fillRect(hoveredCol * tileSizeX, hoveredRow * tileSizeY, tileSizeX, tileSizeY);
            g.setColor(prevColor); //back to original color.
        }

    }

    private void drawPlacedObjects(Graphics g, GridDesign hall) {
        ObjectType[][] grid = hall.getGrid();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] != null) {

                    String objName = grid[row][col].toString().toLowerCase();
                    BufferedImage objectSprite = Textures.getSprite(objName);

                    int h = 20;
                    int w = 20;

                    // different size for column
                    if(objName.equals("column")) {
                        h = 28;
                        w = 14;
                    }
                    else if(objName.equals("skull") || objName.equals("pot")) {
                        h = 12;
                        w = 12;
                    }

                    //int scaledWidth = (int) (w * scale);
                    //int scaledHeight = (int) (h * scale);
                    
                    int offsetX = (tileSizeX - w) / 2;
                    int offsetY = (tileSizeY- h) / 2;
                    g. drawImage(objectSprite, row*tileSizeX+offsetX, col*tileSizeY+offsetY, w, h, null);
                }
            }
        }
    }

	@Override
	public void mousePressed(MouseEvent e) {
        int row = e.getX() / tileSizeX;
        int col = e.getY() / tileSizeY;
        System.out.println ("Clicked at" + col + ", " + row);

        if(SwingUtilities.isRightMouseButton(e)) {
            if(buildingModeHandler.isObjectPresent(row,col, hallNumber)) {
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

        boolean placed = buildingModeHandler.placeObjectAt(row,col, hallNumber);
        if(placed) {
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {
        hoveredRow = -1;
        hoveredCol = -1;
        repaint(); // ensure the highlight is cleared
    }
}

}
	
    	
    
    
    
    


