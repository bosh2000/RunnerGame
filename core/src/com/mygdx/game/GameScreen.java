package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import javax.xml.soap.Text;

public class GameScreen implements Screen {


    private RunnerGame runnerGame;
    private SpriteBatch batch;
    private Texture textureBackGround;
    private Texture textureSand;
    private Texture textureCactus;

    private float worldX;

    private boolean gameOver;
    private float groundHeigth = 190.0f;
    private float playerAncor = 200.0f;

    private Player player;
    private Cactus[] enemies;

    public float getGroundHeigth() {
        return groundHeigth;
    }

    public float getPlayerAncor() {
        return playerAncor;
    }

    public GameScreen(RunnerGame runnerGame, SpriteBatch batch) {

        this.batch = batch;
        this.runnerGame = runnerGame;
    }

    @Override
    public void show() {
        textureBackGround = new Texture("bg.png");
        textureSand = new Texture("ground.png");
        textureCactus = new Texture("cactus.png");
        player = new Player(this);
        enemies = new Cactus[10];
        enemies[0] = new Cactus(textureCactus, new Vector2(1400, getGroundHeigth()));
        for (int i = 1; i < enemies.length; i++) {
            enemies[i] = new Cactus(textureCactus, new Vector2(enemies[i - 1].getPosition().x + MathUtils.random(300, 900), getGroundHeigth()));
        }
        gameOver = false;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(textureBackGround, 0, 0);
        for (int x = 0; x < 8; x++) {
            batch.draw(textureSand, 200 * x - player.getPosition().x % 200, 0);
        }
        player.render(batch);
        for (int i = 0; i < enemies.length; i++) {
            enemies[i].render(batch, player.getPosition().x - playerAncor);
        }
        batch.end();

    }

    public float getRigthEnemie() {
        float maxValue = 0.0f;
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].getPosition().x > maxValue) {
                maxValue = enemies[i].getPosition().x;

            }
        }
        return maxValue;
    }


    @Override
    public void resize(int width, int height) {
        runnerGame.getViewPort().update(width, height, true);
        runnerGame.getViewPort().apply();
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
        textureBackGround.dispose();
        textureSand.dispose();
        textureCactus.dispose();
    }

    public void update(float dt) {
        //worldX+=200.f*dt;
        if (!gameOver) {
            player.update(dt);
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].getPosition().x < player.getPosition().x - playerAncor - 80) {
                    enemies[i].setPosition(getRigthEnemie() + MathUtils.random(300, 900), getGroundHeigth());
                }
            }
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].getRectangle().overlaps(player.getRectangle())) {
                    gameOver = true;
                }

            }
        }

    }
}
