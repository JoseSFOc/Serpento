package com.example.serpento.model;

import java.util.Random;

public class Game {

    //private vista; La vista
    private char[][] map;
    private Snake serpiente;
    private int periodoAcciónRutinariaMs;

    public Game(Map mapC, int periodoAcciónRutinaria){
        this.map = copiarMapaC(mapC);
        periodoAcciónRutinariaMs = periodoAcciónRutinaria;
        serpiente = new Snake(3, mapaC.getFilIni(), mapaC.getColIni(), mapaC.getDirIni());
    }

    public loop (){
        boolean dead=false;
        while(!dead){
            dead= comprobarSiguiente();
            serpiente.avanzar();
        }
    }

   /* public boolean comprobarSiguiente(){
        switch (serpiente.getDireccionActual()){
            case "arriba" : return (map[serpiente.getC]);
        }
    } */

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
