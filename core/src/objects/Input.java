package objects;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;

public class Input extends Thread{

    private LinkedList<Snake> snake;

    public Input(LinkedList<Snake> snake){
        this.snake = snake;
    }
    public void processInput(){
        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)){
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
        // Start Delayed Snake Movement
        DelayedMovement timer = new DelayedMovement(200);
        timer.start();
        while(true){
            // Always Check For Input
            processInput();
        }
    }
    public class DelayedMovement extends Thread{

        private long delay;
        private int size;

        public DelayedMovement(long delay){
            this.delay = delay;
            size = snake.size();
        }
        public void decreaseDelay(){
            // After Each Eaten Apple, Decrease Movement Delay
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
            // Continue to Process Snake Movement
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
