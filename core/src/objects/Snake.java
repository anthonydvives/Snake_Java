package objects;


import com.badlogic.gdx.math.Rectangle;

import java.util.LinkedList;

public class Snake{

    private int xPos;
    private int yPos;
    private Direction currentDirection;

    public Snake(int xPos, int yPos) {
        currentDirection = Direction.INITIAL;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public void setXPos(int xPos){
        this.xPos = xPos;
    }
    public void setYPos(int yPos){
        this.yPos = yPos;
    }
    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }
    public Direction getDirection(){
        return currentDirection;
    }
    public void setDirection(Direction direction){
        currentDirection = direction;
    }
}
