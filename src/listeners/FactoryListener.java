package listeners;

import domain.agent.monster.Monster;

public interface FactoryListener {

    public abstract void onCreationEvent(Monster monster);

    public abstract void onDeletionEvent();

}
