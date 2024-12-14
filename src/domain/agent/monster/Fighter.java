package domain.agent.monster;

import domain.agent.Player;

public class Fighter {

    private boolean lured;
    private int[][] lureLoc;



    public Fighter() {
        super();
        this.lured = false;
        this.lureLoc = new int[2][1];
    }


    public void move(int[][] location) {}
    public void hitPlayer(Player player) {}
    public boolean checkPlayerAdj(Player player) {return true;}
    public void lureLoc(int [][] location) {}

    public boolean isLured() {
        return lured;
    }

    public void setLured(boolean lured) {
        this.lured = lured;
    }

    public int[][] getLureLoc() {
        return lureLoc;
    }

    public void setLureLoc(int[][] lureLoc) {
        this.lureLoc = lureLoc;
    }
}
