package com.example.musicboxdemo.utils;

public enum Status {
    NOT_INITIALIZED(-1),PLAYING(1),PAUSING(2);

    private int index;

    public int getIndex() {
        return index;
    }

    Status(int index) {
        this.index = index;
    }
}
