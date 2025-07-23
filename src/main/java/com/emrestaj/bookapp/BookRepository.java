//BookRepository.java → This is the librarian 👨‍💼
//This file is like your helper that talks to the database.
//You can tell it:
//“Save this book!”
//“Give me all the books!”

package com.emrestaj.bookapp;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repository interface with custom finder methods
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);
    List<Book> findByTitle(String title);
}
