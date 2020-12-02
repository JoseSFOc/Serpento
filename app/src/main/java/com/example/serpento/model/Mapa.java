package com.example.serpento.model;


// 'X' = muro
// 'S' = serpiente
// 'F' = fruta
// ' ' = vacío

import java.util.Random;

public class Mapa {
    char[][] mapa;

    public void crearFruta() {
        Random r = new Random();
        int fila,columna;
        fila = r.nextInt()%mapa.length;
        columna = r.nextInt()%mapa[0].length;
        mapa[fila][columna] = 'F';
    }
}
