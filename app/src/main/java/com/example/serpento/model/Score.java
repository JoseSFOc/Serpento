package com.example.serpento.model;

public class Score {
    String nick;
    int score;

    public Score(String nick, int score) {
        this.nick = nick;
        this.score = score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick(){
        return nick;
    }

    public String toString() {
        return nick + " | " + score;
    }
}
