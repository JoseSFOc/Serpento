package com.example.serpento.model;

import java.util.LinkedList;
import java.util.List;

public class Snake {

<<<<<<< Updated upstream
=======
    private String hola = "hola german";

>>>>>>> Stashed changes
    public static String ARRIBA = "ARRIBA";
    public static String IZQUIERDA = "IZQUIERDA";
    public static String DERECHA = "DERECHA";
    public static String ABAJO = "ABAJO";

    String direccionActual;
    String ultimaDireccion;
    int tam;
    List<Piece> trozosSerpiente;

    public Snake(int filaInicial, int columnaInicial, String direccionInicial){
        this.tam = 3;
        trozosSerpiente = new LinkedList<Piece>();
        trozosSerpiente.add(new Piece(filaInicial,columnaInicial));
        while(tam > trozosSerpiente.size()){
            avanzar();
        }
        ultimaDireccion = "direccionInicial";
        direccionActual = "direccionInicial";
    }

    public void girar(String sentido){

        if (sentido == ARRIBA && (ultimaDireccion == IZQUIERDA || ultimaDireccion == DERECHA)) {
            direccionActual = ARRIBA;
        }else if (sentido == ABAJO && (ultimaDireccion == IZQUIERDA || ultimaDireccion == DERECHA)) {
            direccionActual = ABAJO;
        }else if (sentido == IZQUIERDA && (ultimaDireccion == ABAJO || ultimaDireccion == ARRIBA)) {
            direccionActual = IZQUIERDA;
        }else if (sentido == DERECHA && (ultimaDireccion == ABAJO || ultimaDireccion == ARRIBA)) {
            direccionActual = DERECHA;
        }
    }

    public void avanzar(){
        ultimaDireccion = direccionActual;
        if (tam > trozosSerpiente.size()) {
            añadirTrozoDelante(direccionActual);
        }else if (tam < trozosSerpiente.size()) {
            quitarUltimoTrozo();
            quitarUltimoTrozo();
            añadirTrozoDelante(direccionActual);
        }else{
            quitarUltimoTrozo();
            añadirTrozoDelante(direccionActual);
        }
    }

    public void añadirTrozoDelante(String sentido){ //Punto de referencia arriba izquierda
        switch (direccionActual){
            case "ARRIBA":      trozosSerpiente.add(0, new Piece(trozosSerpiente.get(0).fila-1,trozosSerpiente.get(0).columna));
                                break;
            case "ABAJO":       trozosSerpiente.add(0, new Piece(trozosSerpiente.get(0).fila+1,trozosSerpiente.get(0).columna));
                                break;
            case "IZQUIERDA":   trozosSerpiente.add(0, new Piece(trozosSerpiente.get(0).fila,trozosSerpiente.get(0).columna-1));
                                break;
            case "DERECHA":     trozosSerpiente.add(0, new Piece(trozosSerpiente.get(0).fila,trozosSerpiente.get(0).columna+1));
                                break;
        }
    }

    public void quitarUltimoTrozo() {
        trozosSerpiente.remove(trozosSerpiente.size()-1);
    }
}
