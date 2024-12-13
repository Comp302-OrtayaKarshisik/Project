package domain.Agent;

import domain.Collectables.Enchantment;

import java.util.HashMap;

public class Bag {

    private HashMap<String,Integer> enchCounter = new HashMap<>();

    public void removeEnchantment(Enchantment ench) {}
    public void addEnchantment(Enchantment ench) {}

    public HashMap<String, Integer> getEnchCounter() {
        return enchCounter;
    }

    public void setEnchCounter(HashMap<String, Integer> enchCounter) {
        this.enchCounter = enchCounter;
    }
}
