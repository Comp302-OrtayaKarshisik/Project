package domain;

import domain.agent.Player;
import domain.agent.monster.Archer;
import domain.agent.monster.Fighter;
import domain.agent.monster.Monster;
import domain.agent.monster.Wizard;
import domain.entities.Projectile;
import domain.factories.MonsterFactory;
import domain.level.Hall;
import domain.level.Tile;
import ui.Graphics.AgentGrapichs.MobilMonsterGraphics;
import ui.Graphics.ArrowGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.List;

//I guess we need to resubscribe listeners after loading game state. !!!!!!1
//Hashmap.keyset() , Hashmap.values() kullanılabilir.

public class FileSaveLoadAdapter {


    public static void saveGame() {
        String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        Game game = Game.getInstance();

        File saveDirectory = new File("gameSaves");
        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs(); // Create the directory if it doesn't exist
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(("gameSaves/gameSave" + date +".dat")));

            oos.writeObject(game);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public static void loadGame (Game newgame) {
            try {
                Game game = Game.getInstance();
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));

                //Read the DataStorage object
                Game oldgame = (Game) ois.readObject();

                //OBJECTS(+ rune) ON MAP: Type, Count, Position
                Hall[] halls = oldgame.getDungeon().getHalls();
                int currenthallindex = oldgame.getDungeon().getCurrentHallIndex();

                for (int i = currenthallindex; i < halls.length; i++) {


                    //PLAYER STATS: Position, Time remaining(single countdown timer), Number of lives remaining

                    //newgame.getPlayer().setHealth(oldgame.getPlayer().getHealth());
                    newgame.getPlayer().setHasRune(oldgame.getPlayer().isHasRune());
                    newgame.getPlayer().setLocation(oldgame.getPlayer().getLocation());
                    //newgame.getPlayer().setInvisible(oldgame.getPlayer().isInvisible());


                    newgame.getDungeon().getCurrentHall().getTimer().getCurrentTimeRemaining();

                    //HERO BAG(COLLECTED ENCHANTMENTS): Type, Quantity, Slot(will be determined by index)
                    newgame.getPlayer().getBag().getEnchantmentCounter();

                    //MONSTER STATS: Type, Count, Position
                    //ArrayList<Monster> currentMonsters = MobilMonsterGraphics.getInstance(48).getMonsters();


                    /*ds.playerLocX = game.getPlayer().getLocation().getX();
                    ds.playerLocY = game.getPlayer().getLocation().getY();
                    ds.timeRemaining = game.getDungeon().getCurrentHall().getTimer().getTimeRemaining();
                    ds.health = game.getPlayer().getHealth();
                    ds.invisible = game.getPlayer().isInvisible();
                    ds.hasRune = game.getPlayer().isHasRune();*/

                    //ArrayList<Archer> archers;
                    //ArrayList<Projectile> arrows; arrow archer yaratılınca otomatik halledilir bence
                    //HashMap<Fighter, Boolean> fighters;
                    //ArrayList<Wizard> wizards;


                    game.getDungeon().getCurrentHall().getRuneLocation().getX();
                    game.getDungeon().getCurrentHall().getRuneLocation().getY();
                }


                //get data


            } catch (Exception e) {
                System.out.println("Load Exception!");
            }
        }
}

//-------------------------------------------------------------------

/*// for drawing hall object from build mode
private void drawObjects(Graphics g) {
    Tile[][] grid = game.getDungeon().getCurrentHall().getGrid();
    for (int row = 0; row < grid.length; row++) {
        // grid size needs to change
        int verticalSize = 16;
        for (int col = 0; col < verticalSize; col++) {
            Tile gridObject = grid[row][col];
            if (gridObject != null && (gridObject.getName() == "COLUMN" || gridObject.getName() == "CHEST_FULL" || gridObject.getName() == "CHEST_FULL_GOLD" || gridObject.getName() == "CHEST_CLOSED")) {
                String objName = grid[row][col].getName().toLowerCase();
                BufferedImage objectSprite = Textures.getSprite(objName);
                int h = 32;
                int w = 32;

                // different size for column
                if(objName.equals("column")) {
                    h = 56;
                    w = 28;
                }
                //int scaledWidth = (int) (w * scale);
                //int scaledHeight = (int) (h * scale);


                int offsetX = (baseTileSize - w) / 2;
                int offsetY = (baseTileSize - h) / 2;
                g.drawImage(objectSprite, row * baseTileSize + offsetX, (verticalSize - col - 1) * baseTileSize + offsetY, w, h, null);
            }
        }
    }
}

for (int i = currenthallindex; i < halls.length; i++) {

                    ds.hallObjectCount.put(i, halls[i].);

                    Tile[][] grid = halls[i].getGrid();

                    for (int row = 0; row < grid.length; row++) {
                        // grid size needs to change
                        int verticalSize = 16;
                        for (int col = 0; col < verticalSize; col++) {
                            Tile gridObject = grid[row][col];
                            gridObject.getName();
                        }

                    }
                }

*/



