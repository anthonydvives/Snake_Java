package com.snake.java.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedList;
import java.util.Random;

import objects.Apple;
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
    private Apple apple;
    private Input input;

    private int debug = 0;
    // Implement pause to main menu
    private MainMenuScreen mainmenu;

    // Implement pause/resume
    private State state = State.RUN;

    // Game Sound & Graphics
    private OrthographicCamera camera;
    private Texture grid;
    private ShapeRenderer shapeRenderer;
    private BitmapFont bfont;
    private Sound deathSound;
    private Sound appleSpawnSound;
    private Music bgm;

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
        // Initialize Graphics and Audio
        shapeRenderer = new ShapeRenderer();
        appleSpawnSound = Gdx.audio.newSound(Gdx.files.internal("apple_spawn_sound.wav"));
        deathSound = Gdx.audio.newSound(Gdx.files.internal("death_sound.wav"));
        bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm.mp3"));
        bgm.setLooping(true);
        // Initialize Snake & Apple
        grid = new Texture(Gdx.files.internal("game_area.png"));
        snake = new LinkedList<Snake>();
        Snake head = new Snake((mainmenu.width / 2) - (sWidth / 2), (mainmenu.height / 2) - (sHeight / 2));
        snake.add(head);
        spawnApple();
        bgm.play();
        // Initialize Input Controller
        input = new Input(snake);
        input.start();
    }
    public void spawnApple(){
        Random random = new Random();
        //System.out.println((eastBound - westBound + 1) + " " + westBound);
        int appleX;
        int appleY;
        while(true){
            appleX = random.nextInt(eastBound - westBound + 1) + westBound - 10;
            appleY = random.nextInt(northBound - southBound + 1) + southBound - 10;
            appleX = (((appleX + 5) / 10) * 10) + 5;
            appleY = (((appleY + 5) / 10) * 10) + 5;
            if(validApple(appleX,appleY)){
                break;
            }
        }
        appleSpawnSound.play();
        apple = new Apple(appleX, appleY);
    }
    public boolean validApple(int xPos, int yPos){
        boolean valid = false;
        for(int i = 0; i < snake.size(); i++){
            if(xPos == snake.get(i).getXPos() && yPos == snake.get(i).getYPos()){
                valid = false;
            }
            else{
                valid = true;
            }
        }
        return valid;
    }
    public void detectCollision(){
        // Check Snake X Coordinates Versus X Axis Borders
        if(snake.getFirst().getXPos() > eastBound || snake.getFirst().getXPos() < westBound){
            bgm.stop();
            deathSound.play();
            GameOverScreen gameover = new GameOverScreen(game, mainmenu);
            game.setScreen(gameover);
        }
        // Check Snake Y Coordinates Versus Y Axis Borders
        else if(snake.getFirst().getYPos() > northBound || snake.getFirst().getYPos() < southBound){
            bgm.stop();
            deathSound.play();
            GameOverScreen gameover = new GameOverScreen(game, mainmenu);
            game.setScreen(gameover);
        }
        // Check Snake X & Y Coordinates Versus Apple X & Y Coordinates
        else if(snake.getFirst().getXPos() == apple.getXPos() && snake.getFirst().getYPos() == apple.getYPos()){
            Snake body;
            if(snake.getFirst().getDirection() == Direction.INITIAL){
                body = new Snake(snake.getLast().getXPos(), snake.getLast().getYPos() - 10);
                body.setDirection(snake.getLast().getDirection());
                snake.add(body);
            }
            else if (snake.getFirst().getDirection() == Direction.UP) {
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
            // Spawn New Apple if Snake Head Eats Apple
            spawnApple();
            appleSpawnSound.play();
        }
        // Check Snake X & Y Coordinates Versus Snake Body X & Y Corrdinates
        for(int i = 0; i < snake.size(); i++){
            if(i > 0 && (snake.getFirst().getXPos() == snake.get(i).getXPos() && snake.getFirst().getYPos() == snake.get(i).getYPos())){
                deathSound.play();
                bgm.stop();
                GameOverScreen gameover = new GameOverScreen(game, mainmenu);
                game.setScreen(gameover);
            }
        }
    }
    @Override
    public void render(float delta) {
        // Set Background & Camera
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        // Initialize BitmapFont
        bfont = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        // Spritebatch
        game.batch.begin();
        game.batch.draw(grid, (mainmenu.width / 2) - (550 / 2), (mainmenu.height / 2) - (410 / 2));
        // Draw Score
        bfont.draw(game.batch, "Score: " + Integer.toString(snake.size() - 1), 20 , 460);
        game.batch.end();
        //ShapeRenderer Draws Snake & Apple
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(apple.getXPos(), apple.getYPos(), sWidth, sHeight);
        shapeRenderer.setColor(Color.BLACK);
        // For Each Snake in LinkedList Render a Snake
        for(Snake s: snake){
            shapeRenderer.rect(s.getXPos(), s.getYPos(), sWidth, sHeight);
        }
        shapeRenderer.end();
        // Check Snake for Collision
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
        grid.dispose();
        appleSpawnSound.dispose();
        deathSound.dispose();
    }
    // Implement pause/resume
    public void setGameState(State s) {
        this.state = s;
    }
}
