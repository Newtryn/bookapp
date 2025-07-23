//BookController.java → This is the front desk 🧑‍💻
//This file waits for someone to visit your app through the internet.
//If you go to:
//POST /books → it says: “Oh! Someone wants to add a book!”
//GET /books → it says: “Oh! Someone wants to see all books!”

package com.emrestaj.bookapp.Controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import com.emrestaj.bookapp.Book;
import com.emrestaj.bookapp.BookDTO;
import com.emrestaj.bookapp.BookService;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LogManager.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book createBook(@RequestBody BookDTO bookDTO) {
        logger.info("POST /books - Creating new book: {}", bookDTO.getTitle());
        return bookService.saveBook(bookDTO);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        logger.info("GET /books - Retrieving all books");
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        logger.info("GET /books/{} - Retrieving book by ID", id);
        return bookService.getBookById(id).orElse(null);
    }

    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        logger.info("GET /books/author/{} - Retrieving books by author", author);
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        logger.info("GET /books/title/{} - Retrieving books by title", title);
        return bookService.getBooksByTitle(title);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        logger.info("PUT /books/{} - Updating book", id);
        return bookService.updateBook(id, bookDTO).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        logger.info("DELETE /books/{} - Deleting book", id);
        bookService.deleteBook(id);
    }
}
