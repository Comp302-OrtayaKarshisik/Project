package listeners;

public interface PlayerListener {

    public abstract void onHealthEvent(int num);

    public abstract void onRuneEvent(boolean hasRune);

}
