//BookDTO.java → This is the delivery box 📦
//When someone wants to send a new book to your system (like via a form or JSON),
//this file helps receive the data safely.

package com.emrestaj.bookapp;

public class BookDTO {

    private String title;
    private String author;

    // 🟡 Empty constructor (needed for JSON parsing)
    public BookDTO() {}

    // 🟡 Constructor
    public BookDTO(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // 🟡 Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
