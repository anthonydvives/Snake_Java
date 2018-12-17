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
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;

import objects.State;

public class AboutUs implements Screen {

    final SnakeGame game;

    // Implement pause to main menu
    MainMenuScreen mainmenu;

    // Implement pause/resume
    State state = State.RUN;

    // Game Sound & Graphics

    OrthographicCamera camera;




    Texture titleImage;
    Sprite titleSprite;
    Rectangle titleRec;

    BitmapFont font;

    Texture arrowLeftImg;
    Sprite arrowLeftSprite;
    Rectangle arrow;


    // Implement pause to main menu: constructor requires the MainMenuScreen object as a parameter.
    public AboutUs(final SnakeGame game, MainMenuScreen mainmenu) {
        this.game = game;
        // Implement pause to main menu
        this.mainmenu = mainmenu;

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera(this.mainmenu.width, this.mainmenu.height);
        camera.position.set(this.mainmenu.width / 2, this.mainmenu.height / 2, 0);

        titleImage = new Texture(Gdx.files.internal("aboutustitle.png"));
        titleSprite = new Sprite(titleImage);
        titleRec = new Rectangle();
        titleRec.height = 64;
        titleRec.width = 512;
        titleRec.x = (this.mainmenu.width / 2) - (titleRec.width / 2);
        titleRec.y = 400;

        arrowLeftImg = new Texture(Gdx.files.internal("arrowLeft.png"));

        arrowLeftSprite = new Sprite(arrowLeftImg);

        arrow = new Rectangle();
        arrow.height = 32;
        arrow.width = 32;
        arrow.x = 50;
        arrow.y = 50;

        font = new BitmapFont(Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"), false);
        font.setColor(Color.WHITE);
        font.getData().setScale(1.25f,1.25f);
    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        game.batch.draw(titleSprite,titleRec.x,titleRec.y,titleRec.width,titleRec.height);
        font.draw(game.batch, "Created by: ", 245, 375);
        font.draw(game.batch,"Jonathan Dzialo",225,325);
        font.draw(game.batch,"Anthony Vives",225,275);
        font.draw(game.batch,"Jimmy Duong",225,225);

        game.batch.draw(arrowLeftImg,arrow.x,arrow.y,arrow.width,arrow.height);
        game.batch.end();

        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set((float) Gdx.input.getX(), (float) Gdx.input.getY(), 0.0F);
            camera.unproject(touchPos);
            if (arrow.contains(touchPos.x, touchPos.y)){
                game.setScreen(mainmenu);
                dispose();
            }
        }
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
