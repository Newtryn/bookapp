//Book.java → This is like a book template 🧾
//This file says:
//“Every book should have a title, an author, and an ID.”

package com.emrestaj.bookapp;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    // 🟡 Empty constructor (required by JPA)
    public Book() {}

    // 🟡 Constructor to create a book
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // 🟡 Getters and setters (Java’s version of Python's   property access)
    public Long getId() {
        return id;
    }

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
