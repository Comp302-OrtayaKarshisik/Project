package ui.Listeners;

import domain.agent.monster.Monster;
import domain.Factories.MonsterFactory;

public interface MonsterFactoryListener {

    public abstract void onCreationEvent(MonsterFactory factory, Monster monster);

}
