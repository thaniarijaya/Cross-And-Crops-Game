package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameOver implements Screen {
    final Crossy game;
    OrthographicCamera camera;
    Texture winbackground, losebackground;
    Sound win, lose;

    public GameOver(final Crossy game) { //constructor
        this.game = game;
        winbackground = new Texture(Gdx.files.internal("success.png"));
        losebackground = new Texture(Gdx.files.internal("fail.png"));
        win = Gdx.audio.newSound(Gdx.files.internal("completion-of-level.wav"));
        lose = Gdx.audio.newSound(Gdx.files.internal("losemusic.wav"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1080, 1080);
    }

    @Override
    public void show() { //menentukan sound yg dimainkan
        if (GameScreen.lives > 0) //jika menang
            win.play();
        else
            lose.play(); //jika kalah
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        if (GameScreen.lives > 0) //jika menang
            game.batch.draw(winbackground, 0, 0);
        else //jika kalah
            game.batch.draw(losebackground, 0, 0);

        //menggunakan extension FreeFontTypeGenerator (instance ada di class Crossy)
        game.fontScore.draw(game.batch, String.valueOf(GameScreen.cropsCollected), 460, 500);
        game.batch.end();


        if (Gdx.input.isTouched()) {

            //BUTTON REPLAY
            if (Gdx.input.getX() >= 341 && Gdx.input.getX() <= 457 &&
                    Gdx.input.getY() >= 665 && Gdx.input.getY() <= 756) {
                game.setScreen(new GameScreen(game));
            }

            //BUTTON HOME
            else if (Gdx.input.getX() >= 649 && Gdx.input.getX() <= 747 &&
                    Gdx.input.getY() >= 665 && Gdx.input.getY() <= 756){
                game.setScreen(new MainMenuScreen(game));
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
        winbackground.dispose();
        losebackground.dispose();
        win.dispose();
        lose.dispose();
    }
}
