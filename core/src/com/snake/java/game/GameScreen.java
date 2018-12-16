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

public class GameScreen implements Screen {

    final SnakeGame game;
    private int width = Gdx.graphics.getWidth();
    private int height = Gdx.graphics.getHeight();
    // Game variables
    private int sWidth = 10;
    private int sHeight = 10;
    private int gWidth = 550;
    private int gHeight = 410;
    private int westBound = (width - gWidth) / 2;
    private int southBound = (height - gHeight) / 2;
    private int northBound = height - ((height - gHeight) / 2) - 10;
    private int eastBound = width - ((width - gWidth) / 2) - 10;
    private LinkedList<Snake> snake;
    private long moveDelay;
    private Direction currentState = Direction.INITIAL;
    private Input input;
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

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera(width, height);
        camera.position.set(width / 2, height / 2, 0);
        // Initialize Graphics & Sound
        shapeRenderer = new ShapeRenderer();
        grid = new Texture(Gdx.files.internal("game_area.png"));
        snake = new LinkedList<Snake>();
        input = new Input(snake);
        input.start();
        Snake head = new Snake();
        head.setXPos((width / 2) - (sWidth / 2));
        head.setyPos((height / 2) - (sHeight / 2));
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
        game.batch.draw(grid, (width / 2) - (gWidth / 2), (height / 2) - (gHeight / 2));
        game.batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        //moveDelay = TimeUtils.nanoTime();
        for(Snake s : snake){
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
