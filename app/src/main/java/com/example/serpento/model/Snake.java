package com.example.serpento.model;

import java.util.LinkedList;
import java.util.List;

public class Snake {

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

        ultimaDireccion = direccionInicial;
        direccionActual = direccionInicial;

        while(tam > trozosSerpiente.size()){
            avanzar();
        }
    }

    public void girar(String sentido){

        if (sentido.equals(ARRIBA)  && (ultimaDireccion.equals(IZQUIERDA)  || ultimaDireccion.equals(DERECHA))) {
            direccionActual = ARRIBA;
        }else if (sentido.equals(ABAJO)  && (ultimaDireccion.equals(IZQUIERDA)  || ultimaDireccion.equals(DERECHA))) {
            direccionActual = ABAJO;
        }else if (sentido.equals(IZQUIERDA)  && (ultimaDireccion.equals(ABAJO)  || ultimaDireccion.equals(ARRIBA))) {
            direccionActual = IZQUIERDA;
        }else if (sentido.equals(DERECHA)  && (ultimaDireccion.equals(ABAJO)  || ultimaDireccion.equals(ARRIBA))) {
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

    public String getDireccionActual() {
        return direccionActual;
    }

    public void setDireccionActual(String direccionActual) {
        this.direccionActual = direccionActual;
    }

    public String getUltimaDireccion() {
        return ultimaDireccion;
    }

    public void setUltimaDireccion(String ultimaDireccion) {
        this.ultimaDireccion = ultimaDireccion;
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public List<Piece> getTrozosSerpiente() {
        return trozosSerpiente;
    }

    public void setTrozosSerpiente(List<Piece> trozosSerpiente) {
        this.trozosSerpiente = trozosSerpiente;
    }
}
