package com.example.serpento.model;

public class Partida {

    //private vista; La vista
    private int[][] mapa;
    private Snake serpiente;
    private int periodoAcciónRutinariaMs;

    public Partida (Mapa mapaC, int periodoAcciónRutinaria){
        this.mapa = copiarMapaC(mapaC);
        periodoAcciónRutinariaMs = periodoAcciónRutinaria;

    }

    public void crearFruta() { //cambiar para que no salga encima de la serpiente o obstaculo
        Random r = new Random();
        int fila,columna;
        fila = r.nextInt()%mapa.length;
        columna = r.nextInt()%mapa[0].length;
        mapa[fila][columna] = 'F';
    }
}
