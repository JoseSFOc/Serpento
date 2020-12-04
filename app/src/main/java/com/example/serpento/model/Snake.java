package com.example.serpento.model;

//Crear un enum de direcciones, también se pueden crear variables rollo public static final String ARRIBA = "arriba" para poder llamarlos con SNAKE.ARRIBA

import java.util.LinkedList;
import java.util.List;

public class Snake {

    String direccionActual;
    String ultimaDireccion;
    int tam;
    List<Piece> trozosSerpiente;


    public Snake(int tam, int filaInicial, int columnaInicial, String direccionInicial){
        this.tam = tam;
        trozosSerpiente = new LinkedList<Piece>>();
        trozosSerpiente.add(new Piece(filaInicial,columnaInicial)); //crear tantos como tam. Comprobar si pueden crearse en esos puntos
        ultimaDireccion = "derecha";
        direccionActual = "derecha"; //direccionInicial
    }

    public void girar(String sentido){

        if (sentido == "arriba" && (ultimaDireccion == "izquierda" || ultimaDireccion == "derecha")) {
            direccionActual = "arriba";
        }else if (sentido == "abajo" && (ultimaDireccion == "izquierda" || ultimaDireccion == "derecha")) {
            direccionActual = "abajo";
        }else if (sentido == "izquierda" && (ultimaDireccion == "abajo" || ultimaDireccion == "arriba")) {
            direccionActual = "izquierda";
        }else if (sentido == "derecha" && (ultimaDireccion == "abajo" || ultimaDireccion == "arriba")) {
            direccionActual = "derecha";
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

    public void añadirTrozoDelante(String sentido){
        //Comprobar que no se cree en un obstaculo
        switch (direccionActual){
            case "arriba":      trozosSerpiente.add(0, new Piece(trozosSerpiente.get(0).fila+1,trozosSerpiente.get(0).columna));
                                break;
            case "abajo":       trozosSerpiente.add(0, new Piece(trozosSerpiente.get(0).fila-1,trozosSerpiente.get(0).columna));
                                break;
            case "izquierda":   trozosSerpiente.add(0, new Piece(trozosSerpiente.get(0).fila,trozosSerpiente.get(0).columna-1));
                                break;
            case "derecha":     trozosSerpiente.add(0, new Piece(trozosSerpiente.get(0).fila,trozosSerpiente.get(0).columna+1));
                                break;
        }
    }

    public void quitarUltimoTrozo() {
        trozosSerpiente.remove(trozosSerpiente.size()-1);
    }

}
