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
    public static int width = Gdx.graphics.getWidth();
    public static int height = Gdx.graphics.getHeight();
    // Implement pause to main menu
    GameScreen gamescreen;
    AboutUs aboutUsScreen;
    InstructionScreen instructionScreen;
    ScoreBoardScreen scoreScreen;
    OrthographicCamera camera;


    BitmapFont font;
    Texture normalImg;
    Texture hardImg;
    Sprite normalSprite;
    Sprite hardSprite;
    Rectangle normalSize;
    Rectangle hardSize;

    Texture backgroundImage;
    Sprite backgroundSprite;

    Texture titleImage;
    Sprite titleSprite;
    Rectangle titleSize;

    Texture instructionsImg;
    Sprite instructionsSprite;
    Rectangle instructionsSize;

    Texture aboutImg;
    Sprite aboutSprite;
    Rectangle aboutSize;

    Texture scoreImg;
    Sprite scoreSprite;
    Rectangle scoreSize;
    /*
    Texture arrowLeftImg;
    Sprite arrowLeftSprite;
    Texture arrowRightImg;
    Sprite arrowRightSprite;
    Rectangle arrow;
*/
    public MainMenuScreen(final SnakeGame game) {
        this.game = game;
        gamescreen = new GameScreen(game, this);
        aboutUsScreen = new AboutUs(game,this);
        instructionScreen = new InstructionScreen(game,this);
        scoreScreen = new ScoreBoardScreen(game,this);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        backgroundImage = new Texture(Gdx.files.internal("background.png"));
        backgroundSprite = new Sprite(backgroundImage);

        titleImage = new Texture(Gdx.files.internal("title.png"));
        titleSprite = new Sprite(titleImage);
        titleSize = new Rectangle();
        titleSize.height = 64;
        titleSize.width = 256;
        titleSize.x = 265;
        titleSize.y = 300;

        normalImg = new Texture(Gdx.files.internal("normal.png"));
        normalSprite = new Sprite(normalImg);
        normalSize = new Rectangle();
        normalSize.height = 32;
        normalSize.width = 128;
        normalSize.x = 425;
        normalSize.y = 225;

        hardImg = new Texture(Gdx.files.internal("easy.png"));
        hardSprite = new Sprite(hardImg);
        hardSize = new Rectangle();
        hardSize.height = 32;
        hardSize.width = 128;
        hardSize.x = 250;
        hardSize.y = 225;

        instructionsImg = new Texture(Gdx.files.internal("instructions.png"));
        instructionsSprite = new Sprite(instructionsImg);
        instructionsSize =  new Rectangle();
        instructionsSize.height = 32;
        instructionsSize.width = 256;
        instructionsSize.x = 275;
        instructionsSize.y = 175;

        aboutImg = new Texture(Gdx.files.internal("aboutus.png"));
        aboutSprite = new Sprite(aboutImg);
        aboutSize =  new Rectangle();
        aboutSize.height = 32;
        aboutSize.width = 256;
        aboutSize.x = 275;
        aboutSize.y = 125;

        scoreImg = new Texture(Gdx.files.internal("scoreboard.png"));
        scoreSprite = new Sprite(scoreImg);
        scoreSize = new Rectangle();
        scoreSize.height = 32;
        scoreSize.width = 256;
        scoreSize.x = 275;
        scoreSize.y = 75;
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
        font.getData().setScale(2,2);
    }
    @Override
        public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundSprite,0,0);
        //font.draw(game.batch, "Snake Java Edition", 250, 350);
        font.draw(game.batch, "Choose a difficulty:", 250, 300);
        game.batch.draw(normalSprite,normalSize.x,normalSize.y,normalSize.width,normalSize.height);
        game.batch.draw(hardSprite,hardSize.x,hardSize.y,hardSize.width,hardSize.height);
        game.batch.draw(titleSprite,titleSize.x,titleSize.y,titleSize.width,titleSize.height);
        game.batch.draw(instructionsSprite,instructionsSize.x,instructionsSize.y,instructionsSize.width,instructionsSize.height);
        game.batch.draw(aboutSprite,aboutSize.x,aboutSize.y,aboutSize.width,aboutSize.height);
        game.batch.draw(scoreSprite,scoreSize.x,scoreSize.y,scoreSize.width,scoreSize.height);
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
            if(normalSize.contains(touchPos.x,touchPos.y)){
                gamescreen.setGameState(State.RUN);
                game.setScreen(gamescreen);
                dispose();
            }else if(hardSize.contains(touchPos.x,touchPos.y)){
                gamescreen.setGameState(State.RUN);
                game.setScreen(gamescreen);
                dispose();
            }else if(aboutSize.contains(touchPos.x,touchPos.y)){
                aboutUsScreen.setGameState(State.RUN);
                game.setScreen(aboutUsScreen);
                dispose();
            }else if(instructionsSize.contains(touchPos.x,touchPos.y)){
                instructionScreen.setGameState(State.RUN);
                game.setScreen(instructionScreen);
                dispose();
            }else if(scoreSize.contains(touchPos.x,touchPos.y)){
                scoreScreen.setGameState(State.RUN);
                game.setScreen(scoreScreen);
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
