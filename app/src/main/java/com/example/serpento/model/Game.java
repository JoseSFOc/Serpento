package com.example.serpento.model;

// 'X' = muro
// 'S' = serpiente
// 'F' = fruta
// ' ' = vacío

import java.util.Random;

public class Game {

    //private vista; La vista
    private char[][] map;
    private Snake serpiente;
    private int periodoAcciónRutinariaMs;
    private double
    public Game(Map mapC, int periodoAcciónRutinaria){
        this.map = copiarMapaC(mapC);
        periodoAcciónRutinariaMs = periodoAcciónRutinaria;
        serpiente = new Snake(3, mapaC.getFilIni(), mapaC.getColIni(), mapaC.getDirIni());
    }

    public void loop (){
        boolean dead=false;
        while(!dead){
            // comprobar si se ha llegado al periodo.

            //comprobar cambios de dirección

            dead= comprobarSiguiente(); // comprueba si la serpiente se acaba de meter un tortazo
            serpiente.avanzar();


            //comprobar si se ha comido una fruta/ crear otra

            //actualizar puntuación

            //actualizar tiempo de periodo;
        }
    }

   public boolean comprobarSiguiente(){
        int filacabeza = serpiente.getTrozosSerpiente().get(0).getFila();
        int colcabeza = serpiente.getTrozosSerpiente().get(0).getColumna();

        switch (serpiente.getDireccionActual()){ //actualizar el método para que funcione con SNAKE.ARRIBA
            case "arriba" : return (map[filacabeza-1][colcabeza]=="X" || map[filacabeza-1][colcabeza]=="S"); break;
            case "izquierda" : return (map[filacabeza][colcabeza+1]=="X" || map[filacabeza][colcabeza+1]=="S"); break;
            case "derecha" : return (map[filacabeza][colcabeza-1]=="X" || map[filacabeza][colcabeza-1]=="S"); break;
            case "abajo" : return (map[filacabeza+1][colcabeza]=="X" || map[filacabeza+1][colcabeza]=="S"); break;
        }
    }

    public int[][] copiarMapaC (Map mapC){
        char[][] result = new char[mapC.getMap().length()][mapC.getMap()[0].length()];

        for(int f = 0; f<result.length(); f++){
            for(int c= 0; c<result[0].length(); c++){
                result[f][c] = mapC.getMap()[f][c];
            }
        }

        return result;
    }

    public void crearFruta() { //cambiar para que no salga encima de la serpiente o obstaculo
        Random r = new Random();
        int fila,columna;
        fila = r.nextInt()%map.length;
        columna = r.nextInt()%map[0].length;
        map[fila][columna] = 'F';
    }
}
