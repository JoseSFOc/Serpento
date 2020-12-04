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
    char dirIni;

    public Map(String nombre){
        //MapDBHelper dbHelper = new MapDBHelper();
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

    public char getDirIni() {
        return dirIni;
    }

    public void setDirIni(char dirIni) {
        this.dirIni = dirIni;
    }
}