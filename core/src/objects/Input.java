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
            System.out.println("DIRECTION UP");
            snake.getFirst().setDirection(Direction.UP);
        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)){
            System.out.println("DIRECTION DOWN");
            snake.getFirst().setDirection(Direction.DOWN);
        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)){
            System.out.println("DIRECTION LEFT");
            snake.getFirst().setDirection(Direction.LEFT);
        }
        else if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)){
            System.out.println("DIRECTION RIGHT");
            snake.getFirst().setDirection(Direction.RIGHT);
        }
    }
    public void setSnake(LinkedList<Snake> snake){
        this.snake = snake;
    }

    @Override
    public void run() {
        Timer timer = new Timer(500);
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
        public void processMovement(){
            snake.getFirst().moveXPos();
            snake.getFirst().moveYPos();
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
