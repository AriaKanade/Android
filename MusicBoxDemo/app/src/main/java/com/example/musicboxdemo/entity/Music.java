package com.example.musicboxdemo.entity;

public class Music {
    private String author;

    private String title;

    private String fileName;

    public Music() {
    }

    public Music(String title, String author, String fileName) {
        this.author = author;
        this.title = title;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
