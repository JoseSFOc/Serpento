package com.example.serpento.model;

public class Partida {

    public void crearFruta() { //cambiar para que no salga encima de la serpiente o obstaculo
        Random r = new Random();
        int fila,columna;
        fila = r.nextInt()%mapa.length;
        columna = r.nextInt()%mapa[0].length;
        mapa[fila][columna] = 'F';
    }
}
