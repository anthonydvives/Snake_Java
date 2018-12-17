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
            //System.out.println("DIRECTION INITIAL");
            System.out.println("PRESSED");
            if(isPaused == true){
                for(int i = 0; i < snake.size(); i++){
                    snake.get(i).setDirection(directionList.get(i));
                }
                isPaused = false;
            }
            else if(isPaused == false){
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
            //System.out.println("DIRECTION UP");
            if(snake.getFirst().getDirection() == Direction.DOWN){
                return;
            }
            else {
                snake.getFirst().setDirection(Direction.UP);
            }
        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)){
            //System.out.println("DIRECTION DOWN");
            if(snake.getFirst().getDirection() == Direction.UP){
                return;
            }
            else {
                snake.getFirst().setDirection(Direction.DOWN);
            }
        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)){
            //System.out.println("DIRECTION LEFT");
            if(snake.getFirst().getDirection() == Direction.RIGHT){
                return;
            }
            else {
                snake.getFirst().setDirection(Direction.LEFT);
            }
        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)){
            //System.out.println("DIRECTION RIGHT");
            if(snake.getFirst().getDirection() == Direction.LEFT){
                return;
            }
            else {
                snake.getFirst().setDirection(Direction.RIGHT);
            }
        }
        //currentDirection = Direction.INITIAL;
    }
    public void setSnake(LinkedList<Snake> snake){
        this.snake = snake;
    }

    @Override
    public void run() {
        Timer timer = new Timer(20);
        timer.start();
        while(true){
            processInput();
        }
    }
    public class Timer extends Thread{

        private long delay;

        public Timer(long delay){
            this.delay = delay;
        }
        public void decreaseDelay(){
            if(snake.size() > 10 && (delay % 10 == 0)){
                delay = delay - 20;
            }
        }
        public void processMovement(){

            for(int i = 0; i < snake.size(); i++){
                int tempX = snake.get(i).getXPos();
                int tempY = snake.get(i).getYPos();
                //Move body
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
                //System.out.println(i + " " + snake.get(i).getDirection());
                //snake.get(i).moveXPos();
                //snake.get(i).moveYPos();
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
