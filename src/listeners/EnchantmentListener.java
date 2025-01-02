package listeners;

import domain.collectables.Enchantment;

public interface EnchantmentListener {
    public abstract void onCreationEvent(Enchantment enchantment);

    public abstract void onRemovalEvent(Enchantment enchantment);

    public abstract void onClearEvent();
}
