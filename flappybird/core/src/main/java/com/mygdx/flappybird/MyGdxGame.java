package com.mygdx.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture[] birds;
    Texture topTube, bottomTube, gameOver;
    int flapState = 0;
    float birdY;
    float velocity = 0;
    int gameState = 0;
    float gap = 500;
    Random random;
    int totalTubes = 5;
    float[] tubeX = new float[totalTubes];
    float[] tubeOffset = new float[totalTubes];
    float distanceBetweenTubes;
    float tubeVelocity = 4;
    Circle birdCircle;
    Rectangle[] topTubeRectangles;
    Rectangle[] bottomTubeRectangles;
    int score = 0;
    int scoringTube = 0;
    BitmapFont font;
    int maxScore = 0;
    Preferences prefs; // Para almacenar datos

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        birds = new Texture[2];
        birds[0] = new Texture("flappybirdup.png");
        birds[1] = new Texture("flappybirddown.png");
        gameOver = new Texture("gameover.png");

        birdY = Gdx.graphics.getHeight() / 2 - birds[flapState].getHeight() / 2;
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        random = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() / 1.8f;

        birdCircle = new Circle();
        topTubeRectangles = new Rectangle[totalTubes];
        bottomTubeRectangles = new Rectangle[totalTubes];

        prefs = Gdx.app.getPreferences("FlappyBirdPrefs"); // Crear o cargar preferencias
        maxScore = prefs.getInteger("maxScore", 0); // Cargar el puntaje maximo guardado

        for (int i = 0; i < totalTubes; i++) {
            tubeX[i] = Gdx.graphics.getWidth() + i * distanceBetweenTubes;
            tubeOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }

        font = new BitmapFont();
        font.getData().setScale(5);
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {
            if (Gdx.input.justTouched()) {
                velocity = -30;
            }

            for (int i = 0; i < totalTubes; i++) {
                if (tubeX[i] < -topTube.getWidth()) {
                    tubeX[i] += totalTubes * distanceBetweenTubes;
                    tubeOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
                } else {
                    tubeX[i] -= tubeVelocity;
                }

                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

                topTubeRectangles[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
                bottomTubeRectangles[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
            }

            if (birdY > 0 || velocity < 0) {
                velocity += 2;
                birdY -= velocity;
            }
        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
                StartGame();
            }
        } else if (gameState == 2) {
            batch.draw(gameOver,
                Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2,
                Gdx.graphics.getHeight() / 2);
            if (Gdx.input.justTouched()) {
                gameState = 1;
                velocity = 1;
                score = 0;
                StartGame();
            }
        }

        // Actualizar puntaje
        if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {
            score++;
            if (score > maxScore) {
                maxScore = score;
                prefs.putInteger("maxScore", maxScore); // Guardar puntaje maximo
                prefs.flush(); // Guardar en almacenamiento
            }
            scoringTube++;
            if (scoringTube >= totalTubes) {
                scoringTube = 0;
            }
        }

        font.draw(batch, "Score: " + score, 50, Gdx.graphics.getHeight() - 270);
        font.draw(batch, "M. Score: " + maxScore, 50, Gdx.graphics.getHeight() - 150);

        flapState = flapState == 0 ? 1 : 0;
        batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
        birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

        for (int i = 0; i < totalTubes; i++) {
            if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
                gameState = 2;
            }
        }

        if (birdY <= 0 || birdY >= Gdx.graphics.getHeight()) {
            gameState = 2;
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        birds[0].dispose();
        birds[1].dispose();
        topTube.dispose();
        bottomTube.dispose();
        gameOver.dispose();
    }

    public void StartGame() {
        birdY = Gdx.graphics.getHeight() / 2 - birds[flapState].getHeight() / 2;
        velocity = 0;
        score = 0;
        scoringTube = 0;

        for (int i = 0; i < totalTubes; i++) {
            tubeOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 940);
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;
            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }
    }
}
