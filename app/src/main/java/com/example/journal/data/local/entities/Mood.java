package com.example.journal.data.local.entities;

public enum Mood {
    HAPPY,
    SAD,
    ANGRY,
    ANXIOUS,
    EXCITED,
    CALM;

    public static Mood fromString(String value) {
        return valueOf(value.toUpperCase());
    }
}