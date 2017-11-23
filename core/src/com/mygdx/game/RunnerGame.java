package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.prism.image.ViewPort;

public class RunnerGame extends Game {
	private SpriteBatch batch;
	private GameScreen gameScreen;
	private Viewport viewPort;

	public Viewport getViewPort() {
		return viewPort;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen=new GameScreen(this,batch);
		viewPort=new FitViewport(1280,720);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		float dt=Gdx.graphics.getDeltaTime();
		batch.begin();
		batch.end();
		screen.render(dt);
	}


	@Override
	public void dispose () {
		batch.dispose();
		getScreen().dispose();
	}
}
