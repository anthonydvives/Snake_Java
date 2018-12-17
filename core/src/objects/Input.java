package objects;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.LinkedList;

public class Input extends Thread{

    private LinkedList<Snake> snake;
    private ArrayList<Direction> directionList;
    private boolean isPaused;

    public Input(LinkedList<Snake> snake){
        this.snake = snake;
        isPaused = false;
        directionList = new ArrayList<Direction>();
    }
    public void processInput(){

        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.P)){
            System.out.println("PRESSED");
            while(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.P)){
                //System.out.println("Release the fucking key");
            }
            if(isPaused == true){
                System.out.println("UNPAUSED");
                for(int i = 0; i < snake.size(); i++){
                    snake.get(i).setDirection(directionList.get(i));
                    System.out.println(snake.get(i).getDirection());
                }
                isPaused = false;
            }
            else if(isPaused == false){
                System.out.println("PAUSED");
                for(int i = 0; i < snake.size(); i++){
                    directionList.add(snake.get(i).getDirection());
                    System.out.println(snake.get(i).getDirection());
                }
                for(Snake s: snake){
                    s.setDirection(Direction.INITIAL);
                }
                isPaused = true;
            }

        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)){
            if(snake.getFirst().getDirection() == Direction.DOWN){
                return;
            }
            else {
                snake.getFirst().setDirection(Direction.UP);
            }
        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)){
            if(snake.getFirst().getDirection() == Direction.UP){
                return;
            }
            else {
                snake.getFirst().setDirection(Direction.DOWN);
            }
        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)){
            if(snake.getFirst().getDirection() == Direction.RIGHT){
                return;
            }
            else {
                snake.getFirst().setDirection(Direction.LEFT);
            }
        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)){
            if(snake.getFirst().getDirection() == Direction.LEFT){
                return;
            }
            else {
                snake.getFirst().setDirection(Direction.RIGHT);
            }
        }
    }
    public void setSnake(LinkedList<Snake> snake){
        this.snake = snake;
    }

    @Override
    public void run() {
        Timer timer = new Timer(300);
        timer.start();
        while(true){
            processInput();
        }
    }
    public class Timer extends Thread{

        private long delay;
        private int size;

        public Timer(long delay){
            this.delay = delay;
            size = snake.size();
        }
        public void decreaseDelay(){
            if(size < snake.size()){
                size = snake.size();
                if(delay <= 30){
                }
                else{
                    delay -= 10;
                }
                System.out.println(delay);
            }
        }
        public void processMovement(){
            decreaseDelay();
            for(int i = 0; i < snake.size(); i++){
                if(snake.get(i).getDirection() == Direction.INITIAL){
                    break;
                }
                // Store Original Position Coordinates
                int tempX = snake.get(i).getXPos();
                int tempY = snake.get(i).getYPos();
                // Move Snake Body
                if(snake.get(i).getDirection() == Direction.UP){
                    snake.get(i).setYPos(tempY + 10);
                    if(i > 0 && (snake.get(i - 1).getXPos() > snake.get(i).getXPos())){
                        snake.get(i).setDirection(Direction.RIGHT);
                    }
                    else if(i > 0 && (snake.get(i - 1).getXPos() < snake.get(i).getXPos())){
                        snake.get(i).setDirection(Direction.LEFT);
                    }
                }
                else if(snake.get(i).getDirection() == Direction.DOWN){
                    snake.get(i).setYPos(tempY - 10);
                    if(i > 0 && (snake.get(i - 1).getXPos() > snake.get(i).getXPos())){
                        snake.get(i).setDirection(Direction.RIGHT);
                    }
                    else if(i > 0 && (snake.get(i - 1).getXPos() < snake.get(i).getXPos())){
                        snake.get(i).setDirection(Direction.LEFT);
                    }
                }
                else if(snake.get(i).getDirection() == Direction.LEFT){
                    snake.get(i).setXPos(tempX - 10);
                    if(i > 0 && (snake.get(i - 1).getYPos() > snake.get(i).getYPos())){
                        snake.get(i).setDirection(Direction.UP);
                    }
                    else if(i > 0 && (snake.get(i - 1).getYPos() < snake.get(i).getYPos())){
                        snake.get(i).setDirection(Direction.DOWN);
                    }
                }
                else if(snake.get(i).getDirection() == Direction.RIGHT){
                    snake.get(i).setXPos(tempX + 10);
                    if(i > 0 && (snake.get(i - 1).getYPos() > snake.get(i).getYPos())){
                        snake.get(i).setDirection(Direction.UP);
                    }
                    else if(i > 0 && (snake.get(i - 1).getYPos() < snake.get(i).getYPos())){
                        snake.get(i).setDirection(Direction.DOWN);
                    }
                }
            }
        }
        public void run(){
            try{
                while(true){
                    processMovement();
                    Thread.sleep(delay);
                }
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
