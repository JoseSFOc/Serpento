<<<<<<< HEAD
package com.example.serpento.model;


import java.util.LinkedList;
import java.util.List;

public class Snake {

    private String hola = "hola german";

    public static String ARRIBA = "ARRIBA";
    public static String IZQUIERDA = "IZQUIERDA";
    public static String DERECHA = "DERECHA";
    public static String ABAJO = "ABAJO";

    String direccionActual;
    String ultimaDireccion;
    int tam;
    List<Piece> trozosSerpiente;

    public Snake(int tam, int filaInicial, int columnaInicial, String direccionInicial){
        this.tam = tam;
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
=======
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
>>>>>>> parent of 7d3a332... Avances
