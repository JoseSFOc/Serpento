package com.example.serpento.model;


// 'X' = muro
// 'S' = serpiente
// 'F' = fruta
// ' ' = vacío

import android.database.sqlite.SQLiteDatabase;

import com.example.serpento.dataBase.MapDBHelper;

import java.util.Random;
import java.util.Scanner;

public class Map {
    char[][] mapa;
    int filIni;
    int colIni;
    char dirIni;

    public Map(String nombre){ //Amigo esto hay que arreglarlo
        //SQLiteDatabase db = dbHelper.
        //MapDBHelper dbHelper = new MapDBHelper();
        Scanner sc = new Scanner("base de datos");

        while (sc.hasNext()) {
            String caracter = sc.nextLine();

            mapa = new char[20][20];

            for (int i = 0; i<20;i++) {
               for(int j=0;j<caracter.length();j++) {
                   mapa[i][j] = caracter.charAt(j);
               }
            }
        }
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
