package domain.Agent.Monster;

public abstract class Monster {

    protected int speed;
    protected int[][] location = new int[2][1];
    // Still x and y fan
    protected int xCord;
    protected int yCord;

    public Monster(int speed, int xCord, int yCord) {
        this.speed = speed;
        this.xCord = xCord;
        this.yCord = yCord;
        location[1][0] = xCord;
        location[2][0] = yCord;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int[][] getLocation() {
        return location;
    }

    public void setLocation(int[][] location) {
        this.location = location;
    }

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }
}
