package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import javax.xml.soap.Text;

public class GameScreen implements Screen {


    private RunnerGame runnerGame;
    private SpriteBatch batch;
    private Texture textureBackGround;
    private Texture textureSand;
    private Texture textureCactus;
    private Sound playerJumpSound;


    private Music music;
    private BitmapFont font48;
    private BitmapFont font96;
    private boolean gameOver;
    private float groundHeigth = 190.0f;
    private float playerAncor = 200.0f;
    private float time;

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
        music =Gdx.audio.newMusic(Gdx.files.internal("Jumping bat.wav"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
        playerJumpSound=Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
        player = new Player(this,playerJumpSound);
        enemies = new Cactus[10];
        enemies[0] = new Cactus(textureCactus, new Vector2(1400, getGroundHeigth()));
        for (int i = 1; i < enemies.length; i++) {
            enemies[i] = new Cactus(textureCactus, new Vector2(enemies[i - 1].getPosition().x + MathUtils.random(300, 900), getGroundHeigth()));
        }
        gameOver = false;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor = Color.BLACK;
        font48 = generator.generateFont(parameter);
        parameter.size = 96;
        font96 = generator.generateFont(parameter);
        generator.dispose();
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

        font48.draw(batch, "SCORE : " + (int) player.getScore(), 22, 702);
        if (gameOver) {
            font96.draw(batch, "GAME OVER", 360, 362);
            font48.setColor(1, 1, 1, 0.5f + 0.5f * (float) Math.sin(time * 5.0f));
            font48.draw(batch, "Tap to RESTART", 450, 282);
            font48.setColor(1, 1, 1, 1);
        }
        batch.end();

    }

    public void reStart() {
        gameOver = false;
        player.reStart();
        enemies[0].setPosition(1400, getGroundHeigth());
        for (int i = 1; i < enemies.length; i++) {
            enemies[i].setPosition(enemies[i - 1].getPosition().x + MathUtils.random(300, 900), getGroundHeigth());
        }

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
        music.dispose();
        playerJumpSound.dispose();
    }

    public void update(float dt) {
        time += dt;
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
                    break;
                }

            }
        } else if (Gdx.input.justTouched()) {
            reStart();
        }


    }
}
