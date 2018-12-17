package com.snake.java.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.awt.Rectangle;

import objects.State;

public class InstructionScreen implements Screen {

    final SnakeGame game;

    // Implement pause to main menu
    MainMenuScreen mainmenu;

    // Implement pause/resume
    State state = State.RUN;

    // Game Sound & Graphics

    OrthographicCamera camera;


    Texture backgroundImage;
    Sprite backgroundSprite;

    Texture titleImage;
    Sprite titleSprite;
    Rectangle titleRec;

    BitmapFont font;

    // Implement pause to main menu: constructor requires the MainMenuScreen object as a parameter.
    public InstructionScreen(final SnakeGame game, MainMenuScreen mainmenu) {
        this.game = game;
        // Implement pause to main menu
        this.mainmenu = mainmenu;

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        backgroundImage = new Texture(Gdx.files.internal("background.png"));
        backgroundSprite = new Sprite(backgroundImage);


        titleImage = new Texture(Gdx.files.internal("instructionstitle.png"));
        titleSprite = new Sprite(titleImage);
        titleRec = new Rectangle();
        titleRec.height = 64;
        titleRec.width = 512;
        titleRec.x = 150;
        titleRec.y = 400;

        font = new BitmapFont(Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"), false);
        font.setColor(Color.BLACK);
        font.getData().setScale(1.5f,1.5f);
    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // Implement pause/resume
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            if (state == State.RUN) {
                // If you want Escape to completely quit to main menu screen,
                // do this:
                // game.setScreen(new MainMenuScreen(game));
                // dispose();

                // Implement pause to main menu
                setGameState(State.PAUSE); // stops everything except background music
                game.setScreen(mainmenu);
                // dispose(); // Do not do this if you want to be able to resume the game.
            }

            // Implement pause to main menu:
            // Will setGameState(State.RUN) before return from main menu.
            // See MainMenuScreen.java, line 52.
        }

        // begin a new batch and draw the bucket and all drops
        game.batch.begin();

        game.batch.draw(backgroundSprite,0,0);
        game.batch.draw(titleSprite,titleRec.x,titleRec.y,titleRec.width,titleRec.height);
        font.draw(game.batch, "1. Use the arrow keys to move the snake in different directions", 50, 375);
        font.draw(game.batch,"2. Collect items on the floor to gain points",50,325);
        font.draw(game.batch,"3. Each time an item is collected, the snake will increase in size",50,275);
        font.draw(game.batch,"4. One life per attempt",50,225);
        game.batch.end();
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
