// src/test/java/main/classes/BookTest.java
package main.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BookTest {
    @Test
    public void testEquals() {
        Book book1 = new Book("Title", "Author", 1);
        Book book2 = new Book("Title", "Author", 1);
        Book book3 = new Book("Different Title", "Author", 2);

        assertEquals(book1, book2);
        assertNotEquals(book1, book3);
    }

    @Test
    public void testHashCode() {
        Book book1 = new Book("Title", "Author", 1);
        Book book2 = new Book("Title", "Author", 1);

        assertEquals(book1.hashCode(), book2.hashCode());
    }
}
