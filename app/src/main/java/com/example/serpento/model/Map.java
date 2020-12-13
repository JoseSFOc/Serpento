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
    char dirIni;

    public Map() {
        this.name = "";
        this.filIni = 0;
        this.colIni = 0;
        this.dirIni = 'a';
        this.mapa = new char[20][20];
    }

    public Map(String name, int row, int col, String direction, String mapString) {
        this.name = name;
        this.filIni = row;
        this.colIni = col;
        this.dirIni = direction.charAt(0);

        this.mapa = new char[20][20];
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

    public char getDirIni() {
        return dirIni;
    }

    public void setDirIni(char dirIni) {
        this.dirIni = dirIni;
    }

    public String toString() {
        return name;
    }
}
