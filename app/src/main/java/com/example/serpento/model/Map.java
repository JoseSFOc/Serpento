package com.example.serpento.model;


// 'X' = muro
// 'S' = serpiente
// 'F' = fruta
// ' ' = vacío

import android.database.sqlite.SQLiteDatabase;
import java.util.Random;

public class Map {
    char[][] mapa;
    int filIni;
    int colIni;
    String dirIni; // Esto lo he cambiado porque era de tipo char

    public Map(String nombre){

    }

    public char[][] getMapa() {
        return mapa;
    }

    public void setMapa(char[][] mapa) {
        this.mapa = mapa;
    }

    public int getFilIni() {
        return filIni;
    }

    public void setFilIni(int filIni) {
        this.filIni = filIni;
    }

    public int getColIni() {
        return colIni;
    }

    public void setColIni(int colIni) {
        this.colIni = colIni;
    }

    public String getDirIni() {
        return dirIni;
    }

    public void setDirIni(String dirIni) {
        this.dirIni = dirIni;
    }
}
