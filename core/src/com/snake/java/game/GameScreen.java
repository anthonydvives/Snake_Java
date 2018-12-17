package com.snake.java.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Rectangle;
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
    private int westBound;
    private int southBound;
    private int northBound;
    private int eastBound;
    private LinkedList<Snake> snake;
    private Input input;

    private int debug = 0;
    // Implement pause to main menu
    private MainMenuScreen mainmenu;

    // Implement pause/resume
    State state = State.RUN;

    // Game Sound & Graphics
    private OrthographicCamera camera;
    Texture grid;
    ShapeRenderer shapeRenderer;

    // Implement pause to main menu: constructor requires the MainMenuScreen object as a parameter.
    public GameScreen(final SnakeGame game, MainMenuScreen mainmenu) {
        this.game = game;
        // Implement pause to main menu
        this.mainmenu = mainmenu;
        // Snake Variables
        sWidth = 10;
        sHeight = 10;
        westBound = (mainmenu.width - 550) / 2;
        southBound = (mainmenu.height - 410) / 2;
        northBound = mainmenu.height - ((mainmenu.height - 410) / 2) - 10;
        eastBound = mainmenu.width - ((mainmenu.width - 550) / 2) - 10;
        // Create Camera
        camera = new OrthographicCamera(mainmenu.width, mainmenu.height);
        camera.position.set(mainmenu.width / 2, mainmenu.height / 2, 0);
        // Initialize Snake and Assets
        shapeRenderer = new ShapeRenderer();
        grid = new Texture(Gdx.files.internal("game_area.png"));
        snake = new LinkedList<Snake>();
        Snake head = new Snake((mainmenu.width / 2) - (sWidth / 2), (mainmenu.height / 2) - (sHeight / 2));
        snake.add(head);
        // Initialize Input Controller
        input = new Input(snake);
        input.start();
    }
    public void detectCollision(){
        // Check if Snake goes past play area
        if(snake.getFirst().getXPos() > eastBound || snake.getFirst().getXPos() < westBound){
            GameOverScreen gameover = new GameOverScreen(game, mainmenu, snake.size() - 1);
            game.setScreen(gameover);
        }
        else if(snake.getFirst().getYPos() > northBound || snake.getFirst().getYPos() < southBound){
            GameOverScreen gameover = new GameOverScreen(game, mainmenu, snake.size() - 1);
            game.setScreen(gameover);
        }
        for(int i = 0; i < snake.size(); i++){
            if(i > 0 && (snake.getFirst().getXPos() == snake.get(i).getXPos() && snake.getFirst().getYPos() == snake.get(i).getYPos())){
                GameOverScreen gameover = new GameOverScreen(game, mainmenu, snake.size() - 1);
                game.setScreen(gameover);
            }
        }
    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(grid, (mainmenu.width / 2) - (550 / 2), (mainmenu.height / 2) - (410 / 2));
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

        for(Snake s: snake){
            shapeRenderer.rect(s.getXPos(), s.getYPos(), sWidth, sHeight);
        }
        shapeRenderer.end();
        detectCollision();
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
        setGameState(State.PAUSE);
    }
    @Override
    public void resume() {
        setGameState(State.RUN);
    }
    @Override
    public void dispose() {

    }
    // Implement pause/resume
    public void setGameState(State s) {
        this.state = s;
    }
}
