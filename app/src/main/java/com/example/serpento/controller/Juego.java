package com.example.serpento.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.serpento.R;
import com.example.serpento.model.Difficulty;
import com.example.serpento.model.Score;
import com.example.serpento.model.SingletonMap;
import com.example.serpento.view.GameBoardActivity;
import com.example.serpento.view.MainActivity;

import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class Juego extends SurfaceView implements Runnable {

    private Thread hilo = null;

    // Para mantener una referencia a la actividad
    private GameBoardActivity gameBoardActivity;

    // Direcciones
    public enum Direccion {ARRIBA, DERECHA, ABAJO, IZQUIERDA}

    // Direccion inicial derecha
    private Direccion direccion;

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
    private long tiempoSiguienteFrame;

    // Actualizaciones del sistema por segundo
    // Facil - 7
    // Medio - 10
    // Dificil - 15
    private int FPS;
    private final long MILLIS_POR_SEGUNDO = 1000;

    // Puntos del jugador
    private Score puntuacion;

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

    private Random rnd = new Random();
    private SortedMap<String, Object> singletonMap;
    private Difficulty dificultad;

    public Juego(Context context, Point size, GameBoardActivity gameBoardActivity) {
        super(context);

        this.gameBoardActivity = gameBoardActivity;

        this.puntuacion = new Score("", 0);

        initMap();
        this.dificultad = (Difficulty)singletonMap.get("selectedDifficulty");

        if(dificultad.getName().equals(getResources().getString(R.string.easy))) FPS = 7;
        else if(dificultad.getName().equals(getResources().getString(R.string.normal))) FPS = 10;
        else FPS = 15;

        this.pantallaX = size.x;
        this.pantallaY = size.y;

        // Cuantos pixeles hay en cada bloque
        this.tamBloque = pantallaX / NUM_BLOQUES_ANCHO;
        // Cuantos bloques van a entrar en la altura de la pantalla
        this.numBloquesAltura = pantallaY / tamBloque;

        // Inicializar las cosas para dibujar en pantalla
        this.surfaceHolder = gameBoardActivity.getSurfaceView().getHolder();
        this.paint = new Paint();

        this.trozosSerpienteX = new int[numBloquesAltura*NUM_BLOQUES_ANCHO];
        this.trozosSerpienteY = new int[numBloquesAltura*NUM_BLOQUES_ANCHO];

        int dir = rnd.nextInt() % 4;
        if(dir == 0) this.direccion = Direccion.ARRIBA;
        else if(dir == 1) this.direccion = Direccion.DERECHA;
        else if(dir == 1) this.direccion = Direccion.ABAJO;
        else this.direccion = Direccion.IZQUIERDA;

            // Empezamos la partida
        newGame();
    }

    private void initMap() {
        singletonMap = (SortedMap<String, Object>) SingletonMap.getInstance().get(MainActivity.SHARED_DATA_KEY);
        if (singletonMap == null) {
            singletonMap = new TreeMap<>();
            SingletonMap.getInstance().put(MainActivity.SHARED_DATA_KEY, singletonMap);
        }
    }

    @Override
    public void run() {

        while (estaJugando) {

            // Actualizamos 10 veces por segundo
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
        // Empezamos con una serpiente chiquita de tamaño 2
        tamSerpiente = 2;
        trozosSerpienteX[0] = NUM_BLOQUES_ANCHO / 2;
        trozosSerpienteY[0] = numBloquesAltura / 2;

        // Ponemos fruta en el mapa
        spawnFruit();

        // Reiniciamos la puntuacion a cero
        puntuacion.setPuntos(0);

        // Ponemos el tiempoSiguienteFrame en el valor que toca
        tiempoSiguienteFrame = System.currentTimeMillis();
    }

    public void spawnFruit() {
        Random random = new Random();
        frutaX = random.nextInt(NUM_BLOQUES_ANCHO - 1) + 1;
        frutaY = random.nextInt(numBloquesAltura - 1) + 1;
    }

    private void eatFruit() {
        int nueva = (int)(puntuacion.getPuntos() + (100*dificultad.getScoreMultiplier()));

        // Aumentamos tamaño de la serpiente
        tamSerpiente++;
        // Y ponemos la fruta en otro sitio
        spawnFruit();
        // Añadimos puntos
        puntuacion.setPuntos(nueva);

        // Si llega a una puntuacion en concreto, se aumenta la dificultad
        if((nueva % (1000*dificultad.getScoreMultiplier())) == 0) {
            FPS = (int)Math.ceil((double)FPS*dificultad.getSpeedMultiplier());
        }

    }

    private void moveSnake() {
        // Movemos el cuerpo
        for (int i = tamSerpiente; i > 0; i--) {
            // Empezamos a mover desde la cola hacia delante
            trozosSerpienteX[i] = trozosSerpienteX[i - 1];
            trozosSerpienteY[i] = trozosSerpienteY[i - 1];
        }

        // Y ahora la cabeza la movemos para donde apunte la direccion
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
        // Ha muerto la serpiente?
        boolean dead = false;

        // Aqui comprobamos si se ha dado con el borde de la pantalla
        if (trozosSerpienteX[0] == -1) dead = true;
        if (trozosSerpienteX[0] >= NUM_BLOQUES_ANCHO) dead = true;
        if (trozosSerpienteY[0] == -1) dead = true;
        if (trozosSerpienteY[0] == numBloquesAltura) dead = true;

        // Se come a sí misma?
        for (int i = tamSerpiente - 1; i > 0; i--) {
            //Esto antes era (i > 4) ||||    (i >= 2) &&
            if ((trozosSerpienteX[0] == trozosSerpienteX[i]) && (trozosSerpienteY[0] == trozosSerpienteY[i])) {
                dead = true;
            }
        }

        return dead;
    }

    public void update() {
        // Se ha comido la fruta?
        if (trozosSerpienteX[0] == frutaX && trozosSerpienteY[0] == frutaY) {
            eatFruit();
        }

        moveSnake();

        if (detectDeath()) {
            // Dialog para la puntuacion
            gameBoardActivity.runOnUiThread(() -> gameBoardActivity.deathDialog(puntuacion));
        }
    }

    public void draw() {
        // Metemos lock al canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Rellenamos la pantalla con el color que sea
            canvas.drawColor(Color.WHITE);

            // Setteamos el color para la serpiente
            paint.setColor(Color.GREEN);

            // Actualizamos puntuación en la hebra/hilo
            gameBoardActivity.runOnUiThread(() -> gameBoardActivity.setScoreText(puntuacion.getPuntos() + ""));

            // Pintamos la serpiente trocito a trocito
            for (int i = 0; i < tamSerpiente; i++) {
                canvas.drawRect(trozosSerpienteX[i] * tamBloque,
                        (trozosSerpienteY[i] * tamBloque),
                        (trozosSerpienteX[i] * tamBloque) + tamBloque,
                        (trozosSerpienteY[i] * tamBloque) + tamBloque,
                        paint);
            }

            // Ahora ponemos el color a Rojo para la fruta
            paint.setColor(Color.RED);

            // Dibujamos fruta
            canvas.drawRect(frutaX * tamBloque,
                    (frutaY * tamBloque),
                    (frutaX * tamBloque) + tamBloque,
                    (frutaY * tamBloque) + tamBloque,
                    paint);

            // Quitamos el lock/hacemos unlock al canvas y lo pintamos entero
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired() {

        // Necesitamos actualizar la pantalla?
        if (tiempoSiguienteFrame <= System.currentTimeMillis()) {

            // Decimos cuándo va a tener que actualizarse de nuevo
            tiempoSiguienteFrame = System.currentTimeMillis() + MILLIS_POR_SEGUNDO / FPS;

            //Si retornamos true, la pantalla se actualiza
            return true;
        }

        return false;
    }

    public void setDireccion(Direccion direccion) {
        if (this.direccion.ARRIBA == direccion && this.direccion != this.direccion.ABAJO) {
            this.direccion = direccion;
        }else if (this.direccion.ABAJO == direccion && this.direccion != this.direccion.ARRIBA) {
            this.direccion = direccion;
        }else if (this.direccion.IZQUIERDA == direccion && this.direccion != this.direccion.DERECHA) {
            this.direccion = direccion;
        }else if (this.direccion.DERECHA == direccion && this.direccion != this.direccion.IZQUIERDA) {
            this.direccion = direccion;
        }
    }
}
