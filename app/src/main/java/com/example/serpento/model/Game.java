package com.example.serpento.model;

import java.util.Random;

public class Game {

    //private vista; La vista
    private int[][] map;
    private Snake serpiente;
    private int periodoAcciónRutinariaMs;

    public Game(Map mapC, int periodoAcciónRutinaria){
        //this.map = copiarMapaC(mapC);
        periodoAcciónRutinariaMs = periodoAcciónRutinaria;

    }

    public void crearFruta() { //cambiar para que no salga encima de la serpiente o obstaculo
        Random r = new Random();
        int fila,columna;
        fila = r.nextInt()%map.length;
        columna = r.nextInt()%map[0].length;
        map[fila][columna] = 'F';
    }
}
