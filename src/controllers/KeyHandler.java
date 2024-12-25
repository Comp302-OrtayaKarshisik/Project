package controllers;

import domain.Game;
import domain.util.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


// One change could be the latter pressed button will get the lead
public class KeyHandler implements KeyListener {

    // These are public for now, later we can change them to private or protected
    public boolean goUp, goDown, goLeft, goRight, revealUsed, lureUsed, protectionUsed, options;
    public Direction runeThrowDirection; // U,D,L,R representing up,down,left,right
    public KeyHandler() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //For any key pressed

        int key = e.getKeyCode(); // Keys are actually integer coded

        //This lets multiple keystrokes to be recorded at the same time
        if (key == KeyEvent.VK_UP) goUp = true;
        if (key == KeyEvent.VK_DOWN) goDown = true;
        if (key == KeyEvent.VK_LEFT) goLeft = true;
        if (key == KeyEvent.VK_RIGHT) goRight = true;
        if (key == KeyEvent.VK_ESCAPE) options = true;

        // pause/resume using ESC
        // for handling pause
        if (key == KeyEvent.VK_ESCAPE) {
            Game.getInstance().pauseResumeGame();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //For non action keys (HOME,ESC,arrows esd)

        int key = e.getKeyCode(); // Keys are actually integer coded

        if (key == KeyEvent.VK_R) revealUsed = true;
        if (key == KeyEvent.VK_P) protectionUsed = true;
        if (key == KeyEvent.VK_B) lureUsed = true;

        if (key == KeyEvent.VK_W) runeThrowDirection = Direction.UP;
        if (key == KeyEvent.VK_A) runeThrowDirection = Direction.LEFT;
        if (key == KeyEvent.VK_S) runeThrowDirection = Direction.DOWN;
        if (key == KeyEvent.VK_D) runeThrowDirection = Direction.RIGHT;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        //For any key releasing

        int key = e.getKeyCode(); // Keys are actually integer coded

        if (key == KeyEvent.VK_UP) goUp = false;
        if (key == KeyEvent.VK_DOWN) goDown = false;
        if (key == KeyEvent.VK_LEFT) goLeft = false;
        if (key == KeyEvent.VK_RIGHT) goRight = false;

        if (key == KeyEvent.VK_ESCAPE) options = false;

        if (key == KeyEvent.VK_R) revealUsed = false;
        if (key == KeyEvent.VK_P) protectionUsed = false;

        if (key == KeyEvent.VK_W) {
            runeThrowDirection = null;
            lureUsed = false;
        }

        if (key == KeyEvent.VK_A) {
            runeThrowDirection = null;
            lureUsed = false;
        }

        if (key == KeyEvent.VK_S) {
            runeThrowDirection = null;
            lureUsed = false;
        }

        if (key == KeyEvent.VK_D) {
            runeThrowDirection = null;
            lureUsed = false;
        }
    }
}



