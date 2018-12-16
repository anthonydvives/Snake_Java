package objects;


import com.badlogic.gdx.math.Rectangle;

import java.util.LinkedList;

public class Snake{

    private int xPos;
    private int yPos;
    private int delay = 250;
    private Direction currentDirection;

    public Snake() {
        currentDirection = Direction.INITIAL;
    }
    public void setXPos(int xPos){
        this.xPos = xPos;
    }
    public void setyPos(int yPos){
        this.yPos = yPos;
    }
    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }
    public void setDirection(Direction direction){
        if(direction == Direction.INITIAL){
            return;
        }
        else if(direction == Direction.UP){
            currentDirection = Direction.UP;
        }
        else if(direction == Direction.DOWN){
            currentDirection = Direction.DOWN;
        }
        else if(direction == Direction.LEFT){
            currentDirection = Direction.LEFT;
        }
        else if(direction == Direction.RIGHT){
            currentDirection = Direction.RIGHT;
        }
    }
    public void moveXPos(){
        if(currentDirection == Direction.INITIAL){
            return;
        }
        else if(currentDirection == Direction.RIGHT){
            xPos = xPos + 10;
        }
        else if(currentDirection == Direction.LEFT){
            xPos = xPos - 10;
        }
    }
    public void moveYPos(){
        if(currentDirection == Direction.INITIAL){
            return;
        }
        else if(currentDirection == Direction.UP){
            yPos = yPos + 10;
        }
        else if(currentDirection == Direction.DOWN){
            yPos = yPos - 10;
        }
    }

}
