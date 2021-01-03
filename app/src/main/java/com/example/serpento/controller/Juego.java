package com.example.serpento.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.serpento.view.GameBoardActivity;

import java.util.Random;

public class Juego extends SurfaceView implements Runnable {

    private Thread hilo = null;

    // Para mantener una referencia a la actividad
    private Context context;

    // Direcciones
    public enum direccion {ARRIBA, DERECHA, ABAJO, IZQUIERDA}

    // Direccion inicial derecha
    private direccion direccion = this.direccion.DERECHA;

    // Para mantener el tamaño de la pantalla en píxeles
    private int pantallaX;
    private int pantallaY;

    // Tamaño de la serpiente
    private int tamSerpiente;

    // Posición de la fruta
    private int frutaX;
    private int frutaY;

    // Tamaño en píxeles de un trozo de serpiente, mapa o fruta
    private int tamBloque;

    // Tamaño en segmentos del área jugable
    private final int NUM_BLOQUES_ANCHO = 40;
    private int numBloquesAltura;

    // Controla pausando entre estados del sistema
    private long nextFrameTime;
    // Actualiza el sistema 10 veces por segundo
    private final long FPS = 10;
    private final long MILLIS_POR_SEGUNDO = 1000;
// We will draw the frame much more often

    // Puntos del jugador
    private int puntuacion;

    // Localización de todos los trozos de la serpiente
    private int[] trozosSerpienteX;
    private int[] trozosSerpienteY;

    // Everything we need for drawing
    // Está el juego en marcha?
    private volatile boolean estaJugando;

    // Esto se usa para pintar
    private Canvas canvas;

    // Necesario para el canvas
    private SurfaceHolder surfaceHolder;

    // Mas cosas del canvas
    private Paint paint;

    private GameBoardActivity gameBoardActivity;

    public Juego(Context context, Point size, GameBoardActivity gameBoardActivity) {
        super(context);

        this.context = context;
        this.gameBoardActivity = gameBoardActivity;

        this.pantallaX = size.x;
        this.pantallaY = size.y;

        // Cuantos pixeles hay en cada bloque
        this.tamBloque = pantallaX / NUM_BLOQUES_ANCHO;
        // Cuantos bloques van a entrar en la altura de la pantalla
        this.numBloquesAltura = pantallaY / tamBloque;

        // Inicializar las cosas para dibujar en pantalla
        this.surfaceHolder = gameBoardActivity.getSurfaceView().getHolder();
        this.paint = new Paint();

////////////////////////////////////////////////////////////////////////////////////////////////////

        //ESTO HABRIA QUE CAMBIARLO NO? QUE MIERDA DE QUE SOLO PUEDAS 200 PUNTOS XD

        // If you score 200 you are rewarded with a crash achievement!

        this.trozosSerpienteX = new int[200];
        this.trozosSerpienteY = new int[200];

////////////////////////////////////////////////////////////////////////////////////////////////////
        // Empezamos la partida
        newGame();
    }

    @Override
    public void run() {

        while (estaJugando) {

            // Update 10 times a second
            if (updateRequired()) {
                update();
                draw();
            }

        }
    }

    public void pause() {
        estaJugando = false;
        try {
            hilo.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        estaJugando = true;
        hilo = new Thread(this);
        hilo.start();
    }

    public void newGame() {
        // Start with a single snake segment
        tamSerpiente = 2;
        trozosSerpienteX[0] = NUM_BLOQUES_ANCHO / 2;
        trozosSerpienteY[0] = numBloquesAltura / 2;

        // Get Fruit ready for dinner
        spawnFruit();

        // Reset the score
        puntuacion = 0;

        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }

    public void spawnFruit() {
        Random random = new Random();
        frutaX = random.nextInt(NUM_BLOQUES_ANCHO - 1) + 1;
        frutaY = random.nextInt(numBloquesAltura - 1) + 1;
    }

    private void eatFruit() {
        //  Got him!
        // Increase the size of the snake
        tamSerpiente++;
        //replace Fruit
        // This reminds me of Edge of Tomorrow. Oneday Fruit will be ready!
        spawnFruit();
        //add to the score
        puntuacion = puntuacion + 100;
    }

    private void moveSnake() {
        // Move the body
        for (int i = tamSerpiente; i > 0; i--) {
            // Start at the back and move it
            // to the position of the segment in front of it
            trozosSerpienteX[i] = trozosSerpienteX[i - 1];
            trozosSerpienteY[i] = trozosSerpienteY[i - 1];

            // Exclude the head because
            // the head has nothing in front of it
        }

        // Move the head in the appropriate heading
        switch (direccion) {
            case ARRIBA:
                trozosSerpienteY[0]--;
                break;

            case DERECHA:
                trozosSerpienteX[0]++;
                break;

            case ABAJO:
                trozosSerpienteY[0]++;
                break;

            case IZQUIERDA:
                trozosSerpienteX[0]--;
                break;
        }
    }

    private boolean detectDeath() {
        // Has the snake died?
        boolean dead = false;

        // Hit the screen edge
        if (trozosSerpienteX[0] == -1) dead = true;
        if (trozosSerpienteX[0] >= NUM_BLOQUES_ANCHO) dead = true;
        if (trozosSerpienteY[0] == -1) dead = true;
        if (trozosSerpienteY[0] == numBloquesAltura) dead = true;

        // Eaten itself?
        for (int i = tamSerpiente - 1; i > 0; i--) {
            if ((i > 4) && (trozosSerpienteX[0] == trozosSerpienteX[i]) && (trozosSerpienteY[0] == trozosSerpienteY[i])) {
                dead = true;
            }
        }

        return dead;
    }

    public void update() {
        // Did the head of the snake eat Fruit?
        if (trozosSerpienteX[0] == frutaX && trozosSerpienteY[0] == frutaY) {
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
            gameBoardActivity.runOnUiThread(() -> gameBoardActivity.setScoreText(puntuacion + ""));

            // Draw the snake one block at a time
            for (int i = 0; i < tamSerpiente; i++) {
                canvas.drawRect(trozosSerpienteX[i] * tamBloque,
                        (trozosSerpienteY[i] * tamBloque),
                        (trozosSerpienteX[i] * tamBloque) + tamBloque,
                        (trozosSerpienteY[i] * tamBloque) + tamBloque,
                        paint);
            }

            // Set the color of the paint to draw Fruit red
            paint.setColor(Color.RED);

            // Draw Fruit
            canvas.drawRect(frutaX * tamBloque,
                    (frutaY * tamBloque),
                    (frutaX * tamBloque) + tamBloque,
                    (frutaY * tamBloque) + tamBloque,
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
            nextFrameTime = System.currentTimeMillis() + MILLIS_POR_SEGUNDO / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }

    public void setDireccion(direccion direccion) {
        this.direccion = direccion;
    }

}
