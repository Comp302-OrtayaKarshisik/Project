package ui.Graphics.AgentGrapichs;

import domain.agent.monster.Monster;
import domain.util.Direction;
import ui.Graphics.EntityGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class MobilMonsterGraphics extends EntityGraphics {


    protected  BufferedImage rightPic, leftPic;
    protected int size;
    protected BufferedImage currentImg;
    private final LinkedList<Monster> monsters;

    public MobilMonsterGraphics(int size) {
        this.size = size;
        monsters = new LinkedList<>();
    }

    public void update() {
        //This update only lets one key movement to be recorded at the same time
        //For diagonal movements switch else ifs to just ifs
       for (Monster monster: monsters) {
           monster.move();
       }
    }

    public void draw(Graphics g) {
        for (Monster monster: monsters) {
            if (monster.getDirection() == Direction.LEFT) {
                currentImg = leftPic;
            } else if (monster.getDirection() == Direction.RIGHT) {
                currentImg = rightPic;
            }
            g.drawImage(currentImg, monster.getLocation().getX()*64,(15 - monster.getLocation().getY())*64 , size, size,null);
        }
    }

    public LinkedList<Monster> getMonsters() {
        return monsters;
    }
}
