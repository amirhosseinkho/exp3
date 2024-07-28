// src/test/java/main/classes/LibraryTest.java
package main.classes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LibraryTest {
    @Test
    public void testLendBookToNonRegisteredStudent() {
        Library library = new Library();
        Book book = new Book("Title", "Author", 1);
        Student student = new Student("Student", 1);

        library.addBook(book);

        assertFalse(library.lendBook(book, student));
    }

    @Test
    public void testReturnBookByNonRegisteredStudent() {
        Library library = new Library();
        Book book = new Book("Title", "Author", 1);
        Student student = new Student("Student", 1);

        library.addBook(book);

        assertFalse(library.returnBook(book, student));
    }
}
