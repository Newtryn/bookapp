package com.emrestaj.bookapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class);
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(BookDTO bookDTO) {
        logger.info("Saving new book: {}", bookDTO.getTitle());
        Book book = new Book(bookDTO.getTitle(), bookDTO.getAuthor());
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        logger.info("Fetching book with ID: {}", id);
        return bookRepository.findById(id);
    }

    public List<Book> getBooksByAuthor(String author) {
        logger.info("Fetching books by author: {}", author);
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getBooksByTitle(String title) {
        logger.info("Fetching books by title: {}", title);
        return bookRepository.findByTitle(title);
    }

    public Optional<Book> updateBook(Long id, BookDTO bookDTO) {
        logger.info("Updating book with ID: {}", id);
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            Book updated = bookRepository.save(book);
            logger.info("Book updated: {}", updated.getId());
            return updated;
        });
    }

    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
    }
}
