package com.example.serpento.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.serpento.view.GameBoardActivity;

import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    private Thread thread = null;

    // To hold a reference to the Activity
    private Context context;

    // For tracking movement Heading
    public enum Heading {UP, RIGHT, DOWN, LEFT}

    // Start by heading to the right
    private Heading heading = Heading.RIGHT;

    // To hold the screen size in pixels
    private int screenX;
    private int screenY;

    // How long is the snake
    private int snakeLength;

    // Where is Fruit hiding?
    private int fruitX;
    private int fruitY;

    // The size in pixels of a snake segment
    private int blockSize;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;

    // Control pausing between updates
    private long nextFrameTime;
    // Update the game 10 times per second
    private final long FPS = 10;
    // There are 1000 milliseconds in a second
    private final long MILLIS_PER_SECOND = 1000;
// We will draw the frame much more often

    // How many points does the player have
    private int score;

    // The location in the grid of all the segments
    private int[] snakeXs;
    private int[] snakeYs;

    // Everything we need for drawing
    // Is the game currently playing?
    private volatile boolean isPlaying;

    // A canvas for our paint
    private Canvas canvas;

    // Required to use canvas
    private SurfaceHolder surfaceHolder;

    // Some paint for our canvas
    private Paint paint;

    private GameBoardActivity gameBoardActivity;

    public GameEngine(Context context, Point size, GameBoardActivity gameBoardActivity) {
        super(context);

        this.context = context;
        this.gameBoardActivity = gameBoardActivity;

        this.screenX = size.x;
        this.screenY = size.y;

        // Work out how many pixels each block is
        this.blockSize = screenX / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        this.numBlocksHigh = screenY / blockSize;

        // Initialize the drawing objects
        this.surfaceHolder = gameBoardActivity.getSurfaceView().getHolder();
        this.paint = new Paint();

        // If you score 200 you are rewarded with a crash achievement!
        this.snakeXs = new int[200];
        this.snakeYs = new int[200];

        // Start the game
        newGame();
    }

    @Override
    public void run() {

        while (isPlaying) {

            // Update 10 times a second
            if (updateRequired()) {
                update();
                draw();
            }

        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void newGame() {
        // Start with a single snake segment
        snakeLength = 2;
        snakeXs[0] = NUM_BLOCKS_WIDE / 2;
        snakeYs[0] = numBlocksHigh / 2;

        // Get Fruit ready for dinner
        spawnFruit();

        // Reset the score
        score = 0;

        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }

    public void spawnFruit() {
        Random random = new Random();
        fruitX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        fruitY = random.nextInt(numBlocksHigh - 1) + 1;
    }

    private void eatFruit() {
        //  Got him!
        // Increase the size of the snake
        snakeLength++;
        //replace Fruit
        // This reminds me of Edge of Tomorrow. Oneday Fruit will be ready!
        spawnFruit();
        //add to the score
        score = score + 100;
    }

    private void moveSnake() {
        // Move the body
        for (int i = snakeLength; i > 0; i--) {
            // Start at the back and move it
            // to the position of the segment in front of it
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1];

            // Exclude the head because
            // the head has nothing in front of it
        }

        // Move the head in the appropriate heading
        switch (heading) {
            case UP:
                snakeYs[0]--;
                break;

            case RIGHT:
                snakeXs[0]++;
                break;

            case DOWN:
                snakeYs[0]++;
                break;

            case LEFT:
                snakeXs[0]--;
                break;
        }
    }

    private boolean detectDeath() {
        // Has the snake died?
        boolean dead = false;

        // Hit the screen edge
        if (snakeXs[0] == -1) dead = true;
        if (snakeXs[0] >= NUM_BLOCKS_WIDE) dead = true;
        if (snakeYs[0] == -1) dead = true;
        if (snakeYs[0] == numBlocksHigh) dead = true;

        // Eaten itself?
        for (int i = snakeLength - 1; i > 0; i--) {
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                dead = true;
            }
        }

        return dead;
    }

    public void update() {
        // Did the head of the snake eat Fruit?
        if (snakeXs[0] == fruitX && snakeYs[0] == fruitY) {
            eatFruit();
        }

        moveSnake();

        if (detectDeath()) {
            //start again
            newGame();
        }
    }

    public void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Fill the screen with Game Code School blue
            canvas.drawColor(Color.WHITE);

            // Set the color of the paint to draw the snake white
            paint.setColor(Color.GREEN);

            // Update score in main thread
            gameBoardActivity.runOnUiThread(() -> gameBoardActivity.setScoreText(score + ""));

            // Draw the snake one block at a time
            for (int i = 0; i < snakeLength; i++) {
                canvas.drawRect(snakeXs[i] * blockSize,
                        (snakeYs[i] * blockSize),
                        (snakeXs[i] * blockSize) + blockSize,
                        (snakeYs[i] * blockSize) + blockSize,
                        paint);
            }

            // Set the color of the paint to draw Fruit red
            paint.setColor(Color.RED);

            // Draw Fruit
            canvas.drawRect(fruitX * blockSize,
                    (fruitY * blockSize),
                    (fruitX * blockSize) + blockSize,
                    (fruitY * blockSize) + blockSize,
                    paint);

            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired() {

        // Are we due to update the frame
        if (nextFrameTime <= System.currentTimeMillis()) {
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

}
