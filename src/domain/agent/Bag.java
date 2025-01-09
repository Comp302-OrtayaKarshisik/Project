package domain.agent;

import domain.collectables.Enchantment;
import domain.collectables.EnchantmentType;

import java.util.HashMap;

public class Bag {

    private HashMap<EnchantmentType,Integer> enchantmentCounter;

    public Bag() {
        this.enchantmentCounter = new HashMap<>();
        enchantmentCounter.put(EnchantmentType.Reveal, 0);
        enchantmentCounter.put(EnchantmentType.Luring, 0);
        enchantmentCounter.put(EnchantmentType.Cloak, 0);

    }

    public boolean containsEnchantment(EnchantmentType enchantment) {
        return enchantmentCounter.get(enchantment) != 0;
    }

    public void removeEnchantment(EnchantmentType enchantment) {
        enchantmentCounter.put(enchantment, enchantmentCounter.get(enchantment)-1);
    }
    public void addEnchantment(EnchantmentType enchantment) {
        enchantmentCounter.put(enchantment, enchantmentCounter.get(enchantment)+1);
    }

    public HashMap<EnchantmentType, Integer> getEnchantmentCounter() {
        return enchantmentCounter;
    }
    public void setEnchantmentCounter(HashMap<EnchantmentType, Integer> enchantmentCounter) {
        this.enchantmentCounter = enchantmentCounter;
    }
}
