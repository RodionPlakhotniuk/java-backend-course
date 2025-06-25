package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        library = new Library();
        book1 = new Book("Місто", "Валер'ян Підмогильний");
        book2 = new Book("Java для чайників", "Баррі Берд");
    }

    @Test
    void testAddBook_success() {
        library.addBook(book1);
        assertEquals(1, library.getBookCount());
        assertTrue(library.getBooks().contains(book1));
    }

    @Test
    void testAddBook_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
    }

    @Test
    void testRemoveBook_success() {
        library.addBook(book1);
        boolean removed = library.removeBook(book1);
        assertTrue(removed);
        assertEquals(0, library.getBookCount());
    }

    @Test
    void testRemoveBook_notInLibrary_returnsFalse() {
        boolean result = library.removeBook(book2);
        assertFalse(result);
    }

    @Test
    void testRemoveBook_null_returnsFalse() {
        boolean result = library.removeBook(null);
        assertFalse(result);
    }

    @Test
    void testGetBooks_returnsImmutableList() {
        library.addBook(book1);
        List<Book> books = library.getBooks();
        assertThrows(UnsupportedOperationException.class, () -> books.add(book2));
    }

    @Test
    void testGetBookCount() {
        library.addBook(book1);
        library.addBook(book2);
        assertEquals(2, library.getBookCount());
    }
}