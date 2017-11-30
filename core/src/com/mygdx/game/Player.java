package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.xml.soap.Text;

public class Player {
    private GameScreen gameScreen;
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle rectangle;

    private Sound jumpSound;

    private float score;
    private float time;

    private final int WIDTH = 100;
    private final int HEIGTH = 100;

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Player(GameScreen gameScreen,Sound jumpSound) {
        this.jumpSound=jumpSound;
        this.gameScreen = gameScreen;
        this.texture = new Texture("runner.png");
        this.position = new Vector2(0, 190.0f);
        this.velocity = new Vector2(240.0f, 0);
        this.score = 0;
        this.rectangle=new Rectangle(position.x+WIDTH/4,position.y,WIDTH/2,HEIGTH);


    }

    public void render(SpriteBatch batch) {
        int frame = (int) (time / 0.1f);
        frame = frame % 6;
        batch.draw(texture, gameScreen.getPlayerAncor(), position.y, WIDTH * frame, 0, WIDTH, HEIGTH);
    }

    public void update(float dt) {
        if (position.y > gameScreen.getGroundHeigth()) {
            velocity.y -= 720.0f * dt;
        } else {
            position.y=gameScreen.getGroundHeigth();
            time += velocity.x * dt / 300.0f;
            velocity.y=0.0f;
            if (Gdx.input.justTouched()) {
                velocity.y = 420.0f;
                jumpSound.play();
            }
        }
        score+=0.1;
        position.mulAdd(velocity, dt);
        velocity.x+=15.0f*dt;
        rectangle.setPosition(position.x+WIDTH/4,position.y);
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void reStart(){
        position.set(0,gameScreen.getGroundHeigth());
        score=0;
        velocity.set(240.0f,0.0f);
        rectangle.setPosition(position);
    }
}
