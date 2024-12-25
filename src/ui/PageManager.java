package ui;

import domain.level.GridDesign;

import javax.swing.JFrame;

import controllers.BuildingModeHandler;

//creates buildingmodehandler
//creates pages

public class PageManager {
	
	private static PageManager instance;
	private JFrame mainFrame;
	
	public PageManager() {
		
		mainFrame = new JFrame("Rokue-like Game");
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        
        
        }
	
	public static PageManager getInstance() {
		if (instance == null) {
            instance = new PageManager();
        }
        return instance;
	}
 	
	public void showPage(Page page) {
		
		mainFrame.setContentPane(page);
		mainFrame.pack();
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public void showMainMenuPage() {showPage(new MainMenuPage());}
	
    public void showBuildingModePage() { 
    	
    	BuildingModeHandler bmh = BuildingModeHandler.getInstance();
    	showPage(new BuildModePage(bmh)); }

	public void showPlayModePage(GridDesign[] gridDesigns) { showPage(new PlayModePage(gridDesigns)); }

    public void showFrame() {
        mainFrame.setVisible(true);
    }

 	

}
