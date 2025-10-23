package com.sashaprylutskyy.squidgamems.model.enums;

public enum Sex {
    MALE("Male"),
    FEMALE("Female");

    private final String title;

    Sex(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
