// src/test/java/main/classes/LibrarySearchTest.java
package main.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class LibrarySearchTest {
    @Test
    public void testSearchBooksByTitle() {
        Library library = new Library();
        Book book = new Book("Effective Java", "Joshua Bloch", 1);
        library.addBook(book);
        ArrayList<Object> keys = new ArrayList<>();
        keys.add("Effective Java");
        List<Book> result = library.searchBooks(SearchByType.TITLE, keys);
        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
    }

    @Test
    public void testSearchBooksByAuthor() {
        Library library = new Library();
        Book book = new Book("Effective Java", "Joshua Bloch", 1);
        library.addBook(book);
        ArrayList<Object> keys = new ArrayList<>();
        keys.add("Joshua Bloch");
        List<Book> result = library.searchBooks(SearchByType.AUTHOR, keys);
        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
    }
}
