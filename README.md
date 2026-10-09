> Persian version: [README.fa.md](README.fa.md)

# Exp3: Library project report (testing, debugging and TDD)

Tests were written and the bugs were found in the `TestAndFail` branch. The bugs were fixed and new tests were added in the `debug` branch.

## Bug fixes

### Bug 1: `equals` and `hashCode` in `Book.java`

The `Book` class had no `equals` and `hashCode` methods, which could cause problems when comparing `Book` objects and when using them in data structures such as `ArrayList`.

#### Tests

We first wrote tests for `equals` and `hashCode`:

```java 
// src/test/java/main/classes/BookTest.java 
package main.classes; 
 
import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertNotEquals; 
import org.junit.jupiter.api.Test; 
 
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
```

#### Fix

`equals` and `hashCode` were added to the `Book` class:

```java 
// src/main/java/main/classes/Book.java 
package main.classes; 
 
import java.util.Objects; 
 
public class Book { 
    private int id; 
    private String title; 
    private String author; 
 
    public Book(String title, String author, int id) { 
        this.id = id; 
        this.title = title; 
        this.author = author; 
    } 
 
    public String getTitle() { 
        return title; 
    } 
 
    public String getAuthor() { 
        return author; 
    } 
 
    public int getId() { 
        return id; 
    } 
 
    @Override 
    public String toString() { 
        return title + " by " + author; 
    } 
 
    @Override 
    public boolean equals(Object o) { 
        if (this == o) return true; 
        if (o == null || getClass() != o.getClass()) return false; 
        Book book = (Book) o; 
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(author, book.author); 
    } 
 
    @Override 
    public int hashCode() { 
        return Objects.hash(id, title, author); 
    } 
} 
```

### Bug 2: checking that the student is registered with the library

`lendBook` and `returnBook` did not check whether the student was registered with the library.

#### Tests

We first wrote tests for student registration:

```java 
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
```

#### Fix

`lendBook` and `returnBook` were changed to check whether the student is registered with the library:

```java 
// src/main/java/main/classes/Library.java 
package main.classes; 
 
import java.util.ArrayList; 
 
public class Library { 
    private ArrayList<Book> books; 
    private ArrayList<Student> students; 
 
    public Library() { 
        books = new ArrayList<>(); 
        students = new ArrayList<>(); 
    } 
 
    public void addBook(Book book) { 
        books.add(book); 
    } 
 
    public void addStudent( Student student) { 
        students.add(student); 
    } 
 
    public boolean lendBook(Book book, Student student) { 
        if (!this.books.contains(book)) { 
            System.out.println("!! Book " + book.getTitle() + " not registered."); 
            return false; 
        } 
        if (!this.students.contains(student)) { 
            System.out.println("!! Student " + student.getName() + " not registered."); 
            return false; 
        } 
        if (student.hasBook(book)) { 
            System.out.println("!! Student already has the book."); 
            return false; 
        } 
 
        this.books.remove(book); 
        student.addBook(book); 
        System.out.println(book.getTitle() + " lent to " + student.getName() + "."); 
        return true; 
    } 
 
    public boolean returnBook(Book book, Student student) { 
        if (!this.students.contains(student)) { 
            System.out.println("!! Student " + student.getName() + " not registered."); 
            return false; 
        } 
        if (student.hasBook(book)) { 
            this.books.add(book); 
            System.out.println(student.getName() + " returned " + book.getTitle() + "."); 
            return true; 
        } 
 
        System.out.println("!! " + student.getName() + " doesn't have the book."); 
        return false; 
    } 
}
```

## Completing the search functions with TDD

### Tests

```java 
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
```

### Final implementation of the search functions

```java 
// src/main/java/main/classes/Library.java 
package main.classes; 
 
import java.util.ArrayList; 
import java.util.List; 
 
public class Library { 
    //... other methods 
 
    public ArrayList<Student> searchStudents(SearchByType searchByType, ArrayList<Object> keys) { 
        ArrayList<Student> result = new ArrayList<>(); 
        if (searchByType == SearchByType.ID) { 
            for (Object key : keys) { 
                for (Student student : students) { 
                    if (student.getId() == (int) key) { 
                        result.add(student); 
                    } 
                } 
            } 
        } else if (searchByType == SearchByType.NAME) { 
            for (Object key : keys) { 
                for (Student student : students) { 
                    if (student.getName().equalsIgnoreCase((String) key)) { 
                        result.add(student); 
                    } 
                } 
            } 
        } 
        return result; 
    } 
 
    public ArrayList<Book> searchBooks(SearchByType searchByType, ArrayList<Object> keys) { 
        ArrayList<Book> result = new ArrayList<>(); 
        if (searchByType == SearchByType.ID) { 
            for (Object key : keys) { 
                for (Book book : books) { 
                    if (book.getId() == (int) key) { 
                        result.add(book); 
                    } 
                } 
            }
} else if (searchByType == SearchByType.TITLE) { 
            for (Object key : keys) { 
                for (Book book : books) { 
                    if (book.getTitle().equalsIgnoreCase((String) key)) { 
                        result.add(book); 
                    } 
                } 
            } 
        } else if (searchByType == SearchByType.AUTHOR) { 
            for (Object key : keys) { 
                for (Book book : books) { 
                    if (book.getAuthor().equalsIgnoreCase((String) key)) { 
                        result.add(book); 
                    } 
                } 
            } 
        } 
        return result; 
    } 
}
```

![Screenshot 2024-07-29 152405](https://github.com/user-attachments/assets/322ab294-9714-401d-9b63-6e115b1df5a7)
![Screenshot 2024-07-29 152450](https://github.com/user-attachments/assets/41fe2906-063b-4a69-b86c-b6644fa5a222)
![Screenshot 2024-07-28 195516](https://github.com/user-attachments/assets/f6c7352e-bedd-4a4e-be53-b164d69982b2)
![Screenshot 2024-07-29 152230](https://github.com/user-attachments/assets/9c8514a2-e6aa-4f5d-96d4-debc27576310)

Finally, we checked the test coverage and generated an HTML coverage report. The HTML files are in the `main.classes` folder.

![Screenshot 2024-07-30 130127](https://github.com/user-attachments/assets/e2d473ad-f21b-4eb9-88c0-ac8d89007e9b)
![Screenshot 2024-07-30 130222](https://github.com/user-attachments/assets/ee855048-15c5-40c2-bba4-131bd52dbef2)
![Screenshot 2024-07-30 130302](https://github.com/user-attachments/assets/a4cc8caf-5081-4d2a-990e-7e16828f2776)
![Screenshot 2024-07-30 130554](https://github.com/user-attachments/assets/ec4460f5-7d8f-43bf-bf52-f726081dc696)

## 1. TDD compared with traditional testing

### TDD (test-driven development)

In TDD, the tests are written first, and then the code that makes them pass. TDD suits projects that:

- are complex and need confidence that each part works correctly;
- change often and are updated continuously;
- must work correctly and without errors, such as financial and medical systems.

### Traditional testing

In traditional testing, the code is written first, and then tests are written to check it. This suits projects that:

- are small and short-lived, with little time for writing tests up front;
- change little and are updated rarely;
- are less critical, so errors have smaller consequences.

## 2. Responsibilities of the development team and the QA team in software testing

### Development team

The development team usually works with:

- **Unit tests:** check the smallest units of code (functions or methods) and help developers make sure each unit works correctly.
- **Integration tests:** check the interaction between several units of code to make sure the parts of the system work together correctly.

### Quality assurance (QA) team

The QA team usually works with:

- **System tests:** check the whole system as one unit and make sure it works correctly as a whole.
- **Acceptance tests:** check that the system works correctly against its requirements and acceptance criteria.
- **User acceptance testing (UAT):** performed by end users to make sure the system meets their needs.

### Short description of the test types

- **Unit test:** checks that one specific unit of code works correctly and precisely.
- **Integration test:** checks the interaction between different units of code.
- **System test:** checks the whole system as one unit.
- **Acceptance test:** checks whether the system meets its requirements and acceptance criteria.
- **User acceptance test (UAT):** performed by end users to make sure the system meets their needs.

## 3. Getting test coverage numbers for the json-simple project

To get the test coverage numbers for the project:

1. Right-click the `java` package under `test`.
2. Choose **Run 'All Tests' with Coverage**.
3. The coverage numbers for the project are shown.

There is no need to run the project normally; this step alone gives the coverage numbers.

## 4. Improving coverage by extending the test code

To improve the coverage numbers, add meaningful parts to the test code. They should:

- exercise real, useful interactions between classes and methods;
- cover code that the existing tests do not cover;
- help in understanding how the system works as a whole.

Important: avoid adding tests that only make a trivial call to a class or method and have no use elsewhere in the code.
