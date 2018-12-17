package com.snake.java.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedList;

import objects.Direction;
import objects.Input;
import objects.Snake;
import objects.State;

public class GameScreen implements Screen {

    final SnakeGame game;

    // Game variables
    private int sWidth;
    private int sHeight;
    private int gWidth;
    private int gHeight;
    private int westBound;
    private int southBound;
    private int northBound;
    private int eastBound;
    private LinkedList<Snake> snake;
    private long moveDelay;
    private Direction currentState = Direction.INITIAL;
    private Input input;

    private int debug = 0;
    // Implement pause to main menu
    MainMenuScreen mainmenu;

    // Implement pause/resume
    State state = State.RUN;

    // Game Sound & Graphics
    OrthographicCamera camera;
    Texture grid;
    ShapeRenderer shapeRenderer;

    // Implement pause to main menu: constructor requires the MainMenuScreen object as a parameter.
    public GameScreen(final SnakeGame game, MainMenuScreen mainmenu) {
        this.game = game;
        // Implement pause to main menu
        this.mainmenu = mainmenu;
        // Game Variables
        sWidth = 10;
        sHeight = 10;
        gWidth = 550;
        gHeight = 410;
        westBound = (mainmenu.width - gWidth) / 2;
        southBound = (mainmenu.height - gHeight) / 2;
        northBound = mainmenu.height - ((mainmenu.height - gHeight) / 2) - 10;
        eastBound = mainmenu.width - ((mainmenu.width - gWidth) / 2) - 10;
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera(mainmenu.width, mainmenu.height);
        camera.position.set(mainmenu.width / 2, mainmenu.height / 2, 0);
        // Initialize Graphics & Sound
        shapeRenderer = new ShapeRenderer();
        grid = new Texture(Gdx.files.internal("game_area.png"));
        snake = new LinkedList<Snake>();
        input = new Input(snake);
        input.start();
        Snake head = new Snake((mainmenu.width / 2) - (sWidth / 2), (mainmenu.height / 2) - (sHeight / 2));
        System.out.println(((mainmenu.width / 2) - (sWidth / 2)) + " " + ((mainmenu.height / 2) - (sHeight / 2)));
        snake.add(head);
    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        // Implement pause/resume
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            if (state == State.RUN) {
                setGameState(State.PAUSE);
                game.setScreen(mainmenu);
            }
        }

        game.batch.begin();
        game.batch.draw(grid, (mainmenu.width / 2) - (gWidth / 2), (mainmenu.height / 2) - (gHeight / 2));
        game.batch.end();

        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.V)) {
            Snake body;
            debug++;
            if(snake.getFirst().getDirection() == Direction.INITIAL){
                body = new Snake(snake.getLast().getXPos(), snake.getLast().getYPos() - 10);
                body.setDirection(snake.getLast().getDirection());
                snake.add(body);
            }
            else if (snake.getFirst().getDirection() == Direction.UP) {
                //System.out.println("Up");
                body = new Snake(snake.getLast().getXPos(), snake.getLast().getYPos() - 10);
                body.setDirection(snake.getLast().getDirection());
                snake.add(body);
            }
            else if (snake.getFirst().getDirection() == Direction.DOWN) {
                body = new Snake(snake.getLast().getXPos(), snake.getLast().getYPos() + 10);
                body.setDirection(snake.getLast().getDirection());
                snake.add(body);
            }
            else if (snake.getFirst().getDirection() == Direction.LEFT) {
                body = new Snake(snake.getLast().getXPos() + 10, snake.getLast().getYPos());
                body.setDirection(snake.getLast().getDirection());
                snake.add(body);
            }
            else if (snake.getFirst().getDirection() == Direction.RIGHT) {
                body = new Snake(snake.getLast().getXPos() - 10, snake.getLast().getYPos());
                body.setDirection(snake.getLast().getDirection());
                snake.add(body);
            }
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        //shapeRenderer.rect(tempX, tempY, sWidth, sHeight);

        for(Snake s: snake){
            //System.out.println(s.getXPos() + " " + s.getYPos() + "\n");
            shapeRenderer.rect(s.getXPos(), s.getYPos(), sWidth, sHeight);
        }



        //System.out.println("PROCESSING INPUT");

        //shapeRenderer.rect(westBound,southBound, sWidth, sHeight);
        shapeRenderer.end();

        //System.out.println(width + " " + height);

    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
    }
    @Override
    public void hide() {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {

    }
    // Implement pause/resume
    public void setGameState(State s) {
        this.state = s;
    }
}
