package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Crossy extends Game {
	public SpriteBatch batch;
	public BitmapFont font, fontScore;
	public FreeTypeFontGenerator generator; //tambahan extension
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter; //untuk memasukkan properties dari font


	@Override
	public void create () {
		batch = new SpriteBatch();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("arco.otf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 100;
		parameter.shadowColor = new Color(0,0,0,0.75f); //warna hitam
		parameter.shadowOffsetX = 3;
		parameter.shadowOffsetY = 3;
		font = new BitmapFont();
		fontScore = generator.generateFont(parameter);
		this.setScreen(new MainMenuScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		generator.dispose();
	}
}
