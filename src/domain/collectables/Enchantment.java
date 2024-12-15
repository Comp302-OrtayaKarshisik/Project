package domain.collectables;

public class Enchantment {

    private EnchantmentType type;
    private float remainingTime;

    // x y daha iyi
    private int xCord = 0;
    private int yCord = 0;
    private int[][] location = new int[2][1];

    //All ench can determine their own random creation loc
    public Enchantment(EnchantmentType type) {}

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

    public int[][] getLocation() {
        return location;
    }

    public void setLocation(int[][] location) {
        this.location = location;
    }

    public EnchantmentType getType() {
        return type;
    }

    public void setType(EnchantmentType type) {
        this.type = type;
    }

    public float getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(float remainingTime) {
        this.remainingTime = remainingTime;
    }
}
