package com.emrestaj.bookapp;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Test
    public void testSaveBook() {
        BookRepository mockRepo = mock(BookRepository.class);
        BookService bookService = new BookService(mockRepo);

        BookDTO bookDTO = new BookDTO("Test Title", "Test Author");
        Book book = new Book("Test Title", "Test Author");

        when(mockRepo.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.saveBook(bookDTO);

        assertEquals("Test Title", savedBook.getTitle());
        assertEquals("Test Author", savedBook.getAuthor());
    }

    @Test
    public void testGetAllBooks() {
        BookRepository mockRepo = mock(BookRepository.class);
        BookService bookService = new BookService(mockRepo);

        List<Book> books = Arrays.asList(
                new Book("Title1", "Author1"),
                new Book("Title2", "Author2")
        );

        when(mockRepo.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }
    
    @Test
public void testGetBookById() {
    BookRepository mockRepo = mock(BookRepository.class);
    BookService bookService = new BookService(mockRepo);

    Book book = new Book("Sample", "Author");
    when(mockRepo.findById(1L)).thenReturn(Optional.of(book));

    Optional<Book> result = bookService.getBookById(1L);
    assertTrue(result.isPresent());
    assertEquals("Sample", result.get().getTitle());
}

@Test
public void testUpdateBook() {
    BookRepository mockRepo = mock(BookRepository.class);
    BookService bookService = new BookService(mockRepo);

    Book existingBook = new Book("Old", "Author");
    BookDTO updateDTO = new BookDTO("New", "Updated Author");

    when(mockRepo.findById(1L)).thenReturn(Optional.of(existingBook));
    when(mockRepo.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);

    Optional<Book> result = bookService.updateBook(1L, updateDTO);

    assertTrue(result.isPresent());
    assertEquals("New", result.get().getTitle());
    assertEquals("Updated Author", result.get().getAuthor());
}

@Test
public void testGetBooksByAuthor() {
    BookRepository mockRepo = mock(BookRepository.class);
    BookService bookService = new BookService(mockRepo);

    List<Book> books = Arrays.asList(
        new Book("Book One", "John"),
        new Book("Book Two", "John")
    );

    when(mockRepo.findByAuthor("John")).thenReturn(books);

    List<Book> result = bookService.getBooksByAuthor("John");

    assertEquals(2, result.size());
    assertEquals("John", result.get(0).getAuthor());
}

@Test
public void testGetBooksByTitle() {
    BookRepository mockRepo = mock(BookRepository.class);
    BookService bookService = new BookService(mockRepo);

    List<Book> books = Arrays.asList(
        new Book("Spring Boot", "Author A"),
        new Book("Spring Boot", "Author B")
    );

    when(mockRepo.findByTitle("Spring Boot")).thenReturn(books);

    List<Book> result = bookService.getBooksByTitle("Spring Boot");

    assertEquals(2, result.size());
    assertEquals("Spring Boot", result.get(0).getTitle());
}

@Test
public void testDeleteBook() {
    BookRepository mockRepo = mock(BookRepository.class);
    BookService bookService = new BookService(mockRepo);

    Long idToDelete = 5L;

    // Just run the method, verify it interacts with the repository
    bookService.deleteBook(idToDelete);

    verify(mockRepo, times(1)).deleteById(idToDelete);
}

}
