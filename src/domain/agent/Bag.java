package domain.agent;

import domain.collectables.Enchantment;
import domain.collectables.EnchantmentType;

import java.io.Serializable;
import java.util.HashMap;

public class Bag implements Serializable {

    private HashMap<EnchantmentType,Integer> enchantmentCounter;

    public Bag() {
        this.enchantmentCounter = new HashMap<>();
        enchantmentCounter.put(EnchantmentType.Reveal, 0);
        enchantmentCounter.put(EnchantmentType.Luring, 0);
        enchantmentCounter.put(EnchantmentType.Cloak, 0);

    }

    public boolean containsEnchantment(Enchantment enchantment) {
        return enchantmentCounter.get(enchantment.getType()) != 0;
    }

    public void removeEnchantment(Enchantment enchantment) {
        enchantmentCounter.put(enchantment.getType(), enchantmentCounter.get(enchantment.getType())-1);
    }
    public void addEnchantment(Enchantment enchantment) {
        enchantmentCounter.put(enchantment.getType(), enchantmentCounter.get(enchantment.getType())+1);
    }

    public HashMap<EnchantmentType, Integer> getEnchantmentCounter() {
        return enchantmentCounter;
    }
    public void setEnchantmentCounter(HashMap<EnchantmentType, Integer> enchantmentCounter) {
        this.enchantmentCounter = enchantmentCounter;
    }
}
