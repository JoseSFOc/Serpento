package com.example.serpento.model;

// 'X' = muro
// 'S' = serpiente
// 'F' = fruta
// ' ' = vacío

import java.io.Serializable;
import java.util.Scanner;

public class Map implements Serializable {
    char[][] mapa;
    String name;
    int filIni;
    int colIni;
    String dirIni;

    public Map() {
        this.name = "";
        this.filIni = 0;
        this.colIni = 0;
        this.dirIni = "ARRIBA";
        this.mapa = new char[20][20];
    }

    public Map(String name, int row, int col, String direction, int width, int height) {
        this.name = name;
        this.filIni = row;
        this.colIni = col;
        this.dirIni = direction;

        this.mapa = new char[width][height];

        for(int i = 0; i < height; i++) {
            if(i == 0 || i == height-1) {
                for(int j = 0; j < width; j++) {
                    mapa[i][j] = 'X';
                }
            } else {
                for(int j = 0; j < width; j++) {
                    if(j == 0 || j == width-1) {
                        mapa[i][j] = 'X';
                    } else {
                        mapa[i][j] = ' ';
                    }
                }
            }
        }

    }

    public Map(String name, int row, int col, String direction,/*int tamfil,int tamcol,*/  String mapString) {
        this.name = name;
        this.filIni = row;
        this.colIni = col;
        this.dirIni = direction;

        this.mapa = new char[100][100];
        readMap(mapString);
    }

    private void readMap(String mapString) {
        Scanner sc = new Scanner(mapString);
        String linea;
        int i = 0;
        while(sc.hasNextLine()){
            linea = sc.nextLine();
            for(int j = 0; j<linea.length();j++) {
                mapa[i][j] = linea.charAt(j);
            }
            i++;
        }
    }

    public void showMap(){
        for (int i=0;i<mapa.length;i++) {
            for(int j=0;j<mapa[0].length;j++){
                System.out.print(mapa[i][j]);
            }
            System.out.println();
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

    public String getDirIni() {
        return dirIni;
    }

    public void setDirIni(String dirIni) {
        this.dirIni = dirIni;
    }

    public String toString() {
        return name;
    }
}
