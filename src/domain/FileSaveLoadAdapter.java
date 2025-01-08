package domain;

import java.io.*;

//WRITE/GET to/from DataStorage obj  > NOT FINISHED
public class FileSaveLoadAdapter implements ISaveLoadAdapter {
    Game game;

    public FileSaveLoadAdapter() { // CALL THIS CONSTRUCTOR SOMEWHERE
        this.game = Game.getInstance();
    }

    @Override
    public void saveGame() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));

            DataStorage ds = new DataStorage();

            //WRITE to DataStorage obj here > NOT FINISHED

            //Write the DataStorage object
            oos.writeObject(ds);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void loadGame() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));

            //Read the DataStorage object
            DataStorage ds =  (DataStorage)ois.readObject();


            //get data


        } catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}

