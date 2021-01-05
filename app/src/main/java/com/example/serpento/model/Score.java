package com.example.serpento.model;

public class Score {
    private String nick;
    private int puntos;

    public Score(String nick, int puntos) {
        this.nick = nick;
        this.puntos = puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick(){
        return nick;
    }

    public String toString() {
        return nick + " | " + puntos;
    }
}
