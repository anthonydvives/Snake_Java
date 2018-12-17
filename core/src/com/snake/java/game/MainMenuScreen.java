package com.snake.java.game;

import com.badlogic.gdx.Gdx;
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

public class MainMenuScreen implements Screen{

    final SnakeGame game;

    // Implement pause to main menu
    GameScreen gamescreen;
    AboutUs aboutUsScreen;
    InstructionScreen instructionScreen;
    OrthographicCamera camera;


    BitmapFont font;
    Texture startImg;
    Sprite startSprite;
    Rectangle startSize;

    Texture titleImage;
    Sprite titleSprite;
    Rectangle titleSize;

    Texture instructionsImg;
    Sprite instructionsSprite;
    Rectangle instructionsSize;

    Texture aboutImg;
    Sprite aboutSprite;
    Rectangle aboutSize;

    /*
    Texture arrowLeftImg;
    Sprite arrowLeftSprite;
    Texture arrowRightImg;
    Sprite arrowRightSprite;
    Rectangle arrow;
*/
    public static int width = Gdx.graphics.getWidth();
    public static int height = Gdx.graphics.getHeight();
    public MainMenuScreen(final SnakeGame game) {
        this.game = game;

        camera = new OrthographicCamera(width, height);
        camera.position.set(width / 2, height / 2, 0);


        titleImage = new Texture(Gdx.files.internal("title.png"));
        titleSprite = new Sprite(titleImage);
        titleSize = new Rectangle();
        titleSize.height = 64;
        titleSize.width = 256;
        titleSize.x = (width / 2) - (titleSize.width / 2);;
        titleSize.y = 275;

        startImg = new Texture(Gdx.files.internal("start.png"));
        startSprite = new Sprite(startImg);
        startSize = new Rectangle();
        startSize.height = 32;
        startSize.width = 128;
        startSize.x = (width / 2) - (startSize.width / 2);
        startSize.y = (height/2);

        instructionsImg = new Texture(Gdx.files.internal("instructionsScreen.png"));
        instructionsSprite = new Sprite(instructionsImg);
        instructionsSize =  new Rectangle();
        instructionsSize.height = 32;
        instructionsSize.width = 256;
        instructionsSize.x = (width / 2) - (instructionsSize.width / 2);
        instructionsSize.y = 200;

        aboutImg = new Texture(Gdx.files.internal("aboutUsScreen.png"));
        aboutSprite = new Sprite(aboutImg);
        aboutSize =  new Rectangle();
        aboutSize.height = 32;
        aboutSize.width = 256;
        aboutSize.x = (width / 2) - (aboutSize.width / 2);
        aboutSize.y = 150;
/*
        arrowLeftImg = new Texture(Gdx.files.internal("arrowLeft.png"));
        arrowRightImg = new Texture(Gdx.files.internal("arrowRight.png"));

        arrowLeftSprite = new Sprite(arrowLeftImg);
        arrowRightSprite = new Sprite(arrowRightImg);

        arrow = new Rectangle();
        arrow.height = 32;
        arrow.width = 32;
*/

        font = new BitmapFont(Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"), false);
        font.setColor(Color.BLACK);
        font.getData().setScale(2f,2f);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        //font.draw(game.batch, "Snake Java Edition", 250, 350);
        game.batch.draw(startSprite,startSize.x,startSize.y,startSize.width,startSize.height);
        game.batch.draw(titleSprite,titleSize.x,titleSize.y,titleSize.width,titleSize.height);
        game.batch.draw(instructionsSprite,instructionsSize.x,instructionsSize.y,instructionsSize.width,instructionsSize.height);
        game.batch.draw(aboutSprite,aboutSize.x,aboutSize.y,aboutSize.width,aboutSize.height);
/*
        game.batch.draw(arrowLeftImg,275,175,arrow.width,arrow.height);
        game.batch.draw(arrowRightImg,475,175,arrow.width,arrow.height);

        if( Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            font.draw(game.batch, "Easy", 350, 200);
        }
*/
        // font.draw(game.batch, "Normal", 250, 210);




        game.batch.end();


        if(Gdx.input.isTouched())
        {
            Vector3 touchPos = new Vector3();
            touchPos.set((float)Gdx.input.getX(),(float)Gdx.input.getY(),0.0F);
            camera.unproject(touchPos);
            if(startSize.contains(touchPos.x,touchPos.y)){
                gamescreen = new GameScreen(game, this);
                gamescreen.setGameState(State.RUN);
                game.setScreen(gamescreen);
                dispose();
            }else if(aboutSize.contains(touchPos.x,touchPos.y)){
                aboutUsScreen = new AboutUs(game,this);
                game.setScreen(aboutUsScreen);
                dispose();
            }else if(instructionsSize.contains(touchPos.x,touchPos.y)){
                instructionScreen = new InstructionScreen(game,this);
                game.setScreen(instructionScreen);
                dispose();
            }
            //gamescreen.setGameState(State.RUN);
            //game.setScreen(gamescreen);
            //dispose();
        }
        /*
        if (Gdx.input.isTouched()) {
            gamescreen.setGameState(State.RUN);
            game.setScreen(gamescreen);
            dispose();
        }*/
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
}