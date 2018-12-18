package com.snake.java.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameOverScreen implements Screen {
    // Screen Variables
    private MainMenuScreen mainmenu;
    private SnakeGame game;
    private OrthographicCamera camera;
    // Game Variables
    private Texture title;
    private Texture back;
    private Sprite backButton;
    private Rectangle backHitBox;
    private Sound gameoverSound;
    private boolean playGameoverSound;


    public GameOverScreen(final SnakeGame game, MainMenuScreen mainmenu){
        this.game = game;
        this.mainmenu = mainmenu;
        // Create Camera
        camera = new OrthographicCamera(mainmenu.width, mainmenu.height);
        camera.position.set(mainmenu.width / 2, mainmenu.height / 2, 0);
        // Initialize Graphics
        back = new Texture(Gdx.files.internal("arrowLeft.png"));
        backButton = new Sprite(back);
        backHitBox = new Rectangle(50,50,50,50);

        title = new Texture(Gdx.files.internal("game_over.png"));
        gameoverSound = Gdx.audio.newSound(Gdx.files.internal("game_over_sound.wav"));

        playGameoverSound = true;

    }
    @Override
    public void show() {
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // Draw Title
        game.batch.draw(title, (mainmenu.width / 2) - (325 / 2), (mainmenu.height / 2) - (75 / 2));
        game.batch.draw(backButton, backHitBox.x, backHitBox.y, backHitBox.width, backHitBox.height);
        game.batch.end();

        if(playGameoverSound){
            gameoverSound.play();
            playGameoverSound = false;
        }
        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set((float) Gdx.input.getX(), (float) Gdx.input.getY(), 0.0F);
            camera.unproject(touchPos);
            if (backHitBox.contains(touchPos.x, touchPos.y)){
                gameoverSound.stop();
                game.setScreen(mainmenu);
                dispose();
            }
        }
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void hide() {
    }
    @Override
    public void dispose() {
    }
}
