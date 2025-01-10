package domain.factories;

import domain.Game;
import domain.collectables.Enchantment;
import domain.collectables.EnchantmentType;
import listeners.EnchantmentListener;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnchantmentFactory {
    private ScheduledExecutorService schedule;
    private static EnchantmentFactory instance;
    private final List<EnchantmentListener> listeners;
    private long lastCreation;
    private long passedTime;
    private EnchantmentCreationTask currentTask;

    public static EnchantmentFactory getInstance() {
        if (instance == null) {
            instance = new EnchantmentFactory();
        }
        return instance;
    }
    private EnchantmentFactory(){
        listeners = new LinkedList<>();
        schedule = Executors.newSingleThreadScheduledExecutor();
        currentTask = new EnchantmentCreationTask();
        schedule.scheduleAtFixedRate(currentTask, 50, 12000, TimeUnit.MILLISECONDS);
    }

    public void pauseCreation() {
        currentTask.cancel();
        schedule.shutdown();
        long stopTime = System.currentTimeMillis();
        passedTime = stopTime - lastCreation;
    }

    public void resumeCreation () {
        long dt = 12000 - passedTime;
        lastCreation = System.currentTimeMillis() - passedTime;
        System.out.println(dt);
        currentTask = new EnchantmentCreationTask();

        schedule = Executors.newSingleThreadScheduledExecutor();
        schedule.scheduleAtFixedRate(currentTask, dt > 0? dt: 50 , 12000, TimeUnit.MILLISECONDS);
    }

    public void addListener(EnchantmentListener efl) {
        listeners.add(efl);
    }

    public void notifyRemoval(Enchantment e) {
        for (EnchantmentListener efl: listeners) {efl.onRemovalEvent(e);}
    }

    public void publishNextHallEvent() {
        for (EnchantmentListener efl: listeners) {
            efl.onClearEvent();
        }
    }

    private class EnchantmentCreationTask extends TimerTask {
        @Override
        public void run() {
            int type = Game.random.nextInt(5);
            Enchantment e = switch (type) {
                case 0 -> new Enchantment(EnchantmentType.Reveal);
                case 1 -> new Enchantment(EnchantmentType.Reveal);
                case 2 -> new Enchantment(EnchantmentType.Reveal);
                case 3 -> new Enchantment(EnchantmentType.Luring);
                case 4 -> new Enchantment(EnchantmentType.Cloak);
                default -> null;
            };
            System.out.println("Enchantment created at x: " + e.getLocation().getX() + " y: " + e.getLocation().getY());

            Game.getInstance().getEnchantments().add(e);
            for (EnchantmentListener efl: listeners) {efl.onCreationEvent(e);}
            lastCreation = System.currentTimeMillis();
        }
    }
}
