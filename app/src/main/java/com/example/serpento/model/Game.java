package com.example.serpento.model;

// 'X' = muro
// 'S' = serpiente
// 'F' = fruta
// ' ' = vacío

import java.util.Random;
import java.time.*;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.serpento.R;
import com.example.serpento.view.GameBoardActivity;

public class Game {

    private char[][] map;
    private Snake serpiente;

    private int periodoAcciónRutinariaMs;
    private long tiempoUltimaAcción;
    private int puntuacion;

    private Bitmap bitmap;
    private ImageView view;
    private TextView score;

    public Game(Map mapC, int periodoAcciónRutinaria, ImageView view, TextView scoreview){
        this.map = copiarMapaC(mapC);
        serpiente = new Snake(mapC.getFilIni(), mapC.getColIni(), mapC.getDirIni()+"");


        periodoAcciónRutinariaMs = periodoAcciónRutinaria;
        tiempoUltimaAcción = System.currentTimeMillis();
        puntuacion= 0;


        bitmap = Bitmap.createBitmap(mapC.getMapa().length,mapC.getMapa()[0].length,Bitmap.Config.ARGB_8888);
        dibujarbitmap(map, view);
        this.view = view;
        score = scoreview;
        score.setText(0);
    }

    public void dibujarbitmap(char[][] map, ImageView view){

        for(int i=0; i<bitmap.getWidth(); i++){
            for(int j=0; i<bitmap.getHeight(); j++){
                int p = bitmap.getPixel(i,j);
                if(map[i][j]==' '){
                    bitmap.setPixel(i,j, Color.WHITE);
                }else if(map[i][j]=='S'){
                    bitmap.setPixel(i,j, Color.GREEN);
                }else if (map[i][j] == 'X'){
                    bitmap.setPixel(i,j,Color.BLACK);
                }else if(map[i][j] == 'F'){
                    bitmap.setPixel(i,j,Color.RED);
                }else { //por si acaso
                    bitmap.setPixel(i, j, Color.BLUE);
                }
            }
        }
        view.setImageBitmap(bitmap);
    }
    public void loop (){
        boolean dead=false;
        puntuacion= 0;
        boolean generarFruta;

        while(!dead){
            // comprobar si se ha llegado al periodo.
            if(System.currentTimeMillis()> tiempoUltimaAcción + periodoAcciónRutinariaMs) {

                //comprobar cambios de dirección


                dead = comprobarSiguiente('X') || comprobarSiguiente('S'); // comprueba si la serpiente se va a meter un tortazo

                //comprobar si se ha comido una fruta/ crear otra / aumentar tamaño
                if(generarFruta=comprobarSiguiente('F')){ //actualiza el valor de generarfruta por si hay que generarla
                    //Por ahora he puesto que sólo aumente el tamaño y que aumente en 1 la puntuación

                    puntuacion++;//actualizar puntuación

                    serpiente.setTam(serpiente.tam+1); //Aumentar tamaño
                }

                //Actualizar el sistema
                //Actualizar la serpiente
                //Si no come, eliminar el último
                map[serpiente.getTrozosSerpiente().get(serpiente.getTrozosSerpiente().size()-1).getFila()][serpiente.getTrozosSerpiente().get(serpiente.getTrozosSerpiente().size()-1).getColumna()] = generarFruta? 'S' : ' ';
                //Mover
                serpiente.avanzar();
                //Actualizar donde está la cabeza
                map[serpiente.getTrozosSerpiente().get(0).getFila()][serpiente.getTrozosSerpiente().get(0).getColumna()]='S';
                //Con la serpiente actualizada, genera la fruta
                if(generarFruta){
                    crearFruta();
                }

                //dibujar
                //Dibujar Imagen
                dibujarbitmap(map,view);

                //Cambiar puntuación
                score.setText(puntuacion);


                //actualizar tiempo de periodo
                tiempoUltimaAcción += periodoAcciónRutinariaMs;
            }
        }

        // acciones post muerte
        // insertar puntuación en la bd
        // reiniciar nivel/menú principal

    }

   public boolean comprobarSiguiente(char c){
        int filacabeza = serpiente.getTrozosSerpiente().get(0).getFila();
        int colcabeza = serpiente.getTrozosSerpiente().get(0).getColumna();

        switch (serpiente.getDireccionActual()){ //actualizado el método para que funcione con SNAKE.ARRIBA
            case "ARRIBA" : return (map[filacabeza-1][colcabeza]==c);
            case "IZQUIERDA" : return (map[filacabeza][colcabeza+1]==c);
            case "DERECHA" : return (map[filacabeza][colcabeza-1]==c);
            case "ABAJO" : return (map[filacabeza+1][colcabeza]==c);
        }

        throw new RuntimeException("ERROR EN EL COMPROBAR SIGUIENTE. BLOQUE A COMPROBAR: [" + filacabeza +"][" + colcabeza + "] Tipo: " +c+ ". Supuesta dirección: "+ serpiente.getDireccionActual());
    }

    public char[][] copiarMapaC (Map mapC){
        char[][] result = new char[mapC.getMapa().length][mapC.getMapa()[0].length];

        for(int f = 0; f<result.length; f++){
            for(int c= 0; c<result[0].length; c++){
                result[f][c] = mapC.getMapa()[f][c];
            }
        }

        return result;
    }

    public void crearFruta() { //Actualizado para que no salga encima de la serpiente o obstaculo
        Random r = new Random();
        int fila,columna;
        do {
            fila = r.nextInt() % map.length;
            columna = r.nextInt() % map[0].length;
        }while(map[fila][columna]!= ' ');

        map[fila][columna] = 'F';
    }


    public void cambiarDireccion(String sentido){
        serpiente.girar(sentido);
    }
}
