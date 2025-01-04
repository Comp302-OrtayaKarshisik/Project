package ui.Graphics.AgentGrapichs;

import domain.agent.monster.Monster;
import domain.factories.MonsterFactory;
import domain.util.Direction;
import listeners.FactoryListener;
import ui.Graphics.EntityGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class MobilMonsterGraphics extends EntityGraphics implements FactoryListener {

    protected  BufferedImage rightPic, leftPic;
    protected int size;
    protected BufferedImage currentImg;
    protected final LinkedList<Monster> monsters;

    public MobilMonsterGraphics(int size) {
        this.size = size;
        this.monsters = new LinkedList<>();
    }

    public void draw(Graphics g) {
        for (Monster monster: monsters) {
            if (monster.getDirection() == Direction.LEFT) {
                currentImg = leftPic;
            } else if (monster.getDirection() == Direction.RIGHT) {
                currentImg = rightPic;
            }
            g.drawImage(currentImg, monster.getLocation().getX()*48,(15 - monster.getLocation().getY())*48 , size, size,null);
        }
    }

    public void onDeletionEvent() {
        this.monsters.clear();
    }

    public void subscribe(MonsterFactory mf) {
        mf.addListener(this);
    }
}
