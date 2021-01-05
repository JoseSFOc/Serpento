package com.example.serpento.model;

import java.io.Serializable;

public class Difficulty implements Serializable {
    private String name;
    private float scoreMultiplier;
    private float speedMultiplier;

    public Difficulty() {
        this.name = "";
        this.scoreMultiplier = 1;
        this.speedMultiplier = 1;
    }

    public Difficulty(String name, float score, float speed) {
        this.name = name;
        this.scoreMultiplier = score;
        this.speedMultiplier = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScoreMultiplier() {
        return scoreMultiplier;
    }

    public void setScoreMultiplier(float scoreMultiplier) {
        this.scoreMultiplier = scoreMultiplier;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(float speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    @Override
    public String toString() {
        return name;
    }
}
