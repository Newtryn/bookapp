package com.emrestaj.bookapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.emrestaj.bookapp.Controllers.BookController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookControllerUnitTest {

    private BookService bookService;
    private BookController bookController;

    @BeforeEach
    void setup() {
        bookService = Mockito.mock(BookService.class);
        bookController = new BookController(bookService);
    }

    @Test
    void testCreateBook() {
        BookDTO bookDTO = new BookDTO("Title", "Author");
        Book book = new Book("Title", "Author");

        when(bookService.saveBook(bookDTO)).thenReturn(book);

        Book result = bookController.createBook(bookDTO);

        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        assertEquals("Author", result.getAuthor());

        verify(bookService, times(1)).saveBook(bookDTO);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(
            new Book("Title1", "Author1"),
            new Book("Title2", "Author2")
        );

        when(bookService.getAllBooks()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Title1", result.get(0).getTitle());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookByIdFound() {
        Book book = new Book("Title", "Author");

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        Book result = bookController.getBookById(1L);

        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookByIdNotFound() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        Book result = bookController.getBookById(1L);

        assertNull(result);
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBooksByAuthor() {
        List<Book> books = Arrays.asList(new Book("Title", "Author"));

        when(bookService.getBooksByAuthor("Author")).thenReturn(books);

        List<Book> result = bookController.getBooksByAuthor("Author");

        assertEquals(1, result.size());
        assertEquals("Author", result.get(0).getAuthor());
        verify(bookService, times(1)).getBooksByAuthor("Author");
    }

    @Test
    void testGetBooksByTitle() {
        List<Book> books = Arrays.asList(new Book("Title", "Author"));

        when(bookService.getBooksByTitle("Title")).thenReturn(books);

        List<Book> result = bookController.getBooksByTitle("Title");

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
        verify(bookService, times(1)).getBooksByTitle("Title");
    }

    @Test
    void testUpdateBookFound() {
        BookDTO updateDTO = new BookDTO("Updated Title", "Updated Author");
        Book updatedBook = new Book("Updated Title", "Updated Author");

        when(bookService.updateBook(1L, updateDTO)).thenReturn(Optional.of(updatedBook));

        Book result = bookController.updateBook(1L, updateDTO);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(bookService, times(1)).updateBook(1L, updateDTO);
    }

    @Test
    void testUpdateBookNotFound() {
        BookDTO updateDTO = new BookDTO("Updated Title", "Updated Author");

        when(bookService.updateBook(1L, updateDTO)).thenReturn(Optional.empty());

        Book result = bookController.updateBook(1L, updateDTO);

        assertNull(result);
        verify(bookService, times(1)).updateBook(1L, updateDTO);
    }

    @Test
    void testDeleteBook() {
        // deleteBook returns void so just verify call

        doNothing().when(bookService).deleteBook(1L);

        bookController.deleteBook(1L);

        verify(bookService, times(1)).deleteBook(1L);
    }
}
