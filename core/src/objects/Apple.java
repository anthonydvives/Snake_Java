package objects;

public class Apple {
    private int xPos;
    private int yPos;
    public Apple(int xPos, int yPos){
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
}
