package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {
    final Crossy game;
    OrthographicCamera camera;
    Texture background, instructions;
    Sound click;

    public MainMenuScreen(final Crossy game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1080, 1080);
        background = new Texture(Gdx.files.internal("start.png"));
        instructions = new Texture(Gdx.files.internal("instructions.png"));
        click = Gdx.audio.newSound(Gdx.files.internal("game-click.wav"));
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        //Draw background
        game.batch.draw(background, 0, 0);

        //Button Instructions
        if (Gdx.input.isTouched()){
            System.out.println("X: " + Gdx.input.getX() + " Y: " + Gdx.input.getY());
            //Jika user menekan koordinat tsb (tahan lama), maka gambar instructions akan di draw
            if (Gdx.input.getX() >= 937 && Gdx.input.getX() <= 1039 && Gdx.input.getY() >= 734
                    && Gdx.input.getY() <= 814){
                game.batch.draw(instructions, 35.5f, 171.5f);
            }
        }
        game.batch.end();

        //Button Play
        if (Gdx.input.isTouched() && Gdx.input.getX() >= 444 && Gdx.input.getX() <= 644
                && Gdx.input.getY() >= 516 && Gdx.input.getY() <= 589) {
            click.play();
            //tersambung ke GameScreen
            game.setScreen(new GameScreen(game)); //switch dari main menu to game screen
            dispose(); //dispose main menu screen
        }
    }


    @Override
    public void show() {

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
        background.dispose();
        click.dispose();
    }
}
