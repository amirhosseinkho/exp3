# Exp3 
# گزارش پروژه کتابخانه 
 در برنچ TestAndFail تست‌ها نوشته شد و ایرادات پیدا شد. و در برنچ debug، ایرادات اصلاح شد و تست‌های جدید هم اضافه شد.
## اصلاح ایرادات 
 
### ایراد 1: `equals` و `hashCode` در `Book.java` 
کلاس `Book` فاقد متدهای `equals` و `hashCode` بود که می‌توانست مشکلاتی در مقایسه و استفاده از اشیا `Book` در ساختارهای داده‌ای مانند `ArrayList` ایجاد کند. 
 
#### تست‌ها 
ابتدا تست‌هایی برای بررسی `equals` و `hashCode` نوشتیم: 
 
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

#### رفع ایراد 
متدهای equals و hashCode به کلاس Book اضافه شدند: 
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


### ایراد 2: بررسی ثبت دانشجو در کتابخانه 
متدهای lendBook و returnBook بررسی نمی‌کردند که آیا دانشجو در کتابخانه ثبت شده است یا خیر. 
 
#### تست‌ها 
ابتدا تست‌هایی برای بررسی ثبت دانشجو نوشتیم: 

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

#### رفع ایراد 
متدهای lendBook و returnBook تغییر یافتند تا بررسی کنند که آیا دانشجو در کتابخانه ثبت شده است یا خیر:
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

 
 
## تکمیل توابع جستجو با استفاده از TDD 
 
### تست‌های نوشته شده 
 
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

### پیاده‌سازی نهایی توابع جستجو 
 
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


## ۱. مقایسه روش TDD با روش تست کردن سنتی
### روش TDD (Test-Driven Development)در روش TDD، ابتدا تست‌ها نوشته می‌شوند و سپس کدهایی که تست‌ها را عبور می‌دهند، نوشته می‌شوند. این روش برای پروژه‌هایی مناسب است که:
- پیچیدگی بالایی دارند و نیاز به اطمینان از صحت عملکرد بخش‌های مختلف دارند.- نیاز به تغییرات مکرر و به‌روزرسانی‌های مستمر دارند.
- اهمیت زیادی در عملکرد صحیح و بدون خطا دارند، مانند سیستم‌های مالی و پزشکی.
### روش تست کردن سنتیدر روش سنتی، ابتدا کدها نوشته می‌شوند و سپس تست‌ها برای بررسی صحت کدها نوشته می‌شوند. این روش برای پروژه‌هایی مناسب است که:
- کوچک و کوتاه‌مدت هستند و زمان زیادی برای نوشتن تست‌های اولیه ندارند.- نیاز به تغییرات کم و به‌روزرسانی‌های محدود دارند.
- اهمیت کمتری در عملکرد بدون خطا دارند و خطاها تاثیرات کمتری دارند.
## ۲. وظایف تیم ایجاد و تیم تضمین کیفیت در تست نرم‌افزار
### تیم ایجاد (Development Team)تیم ایجاد معمولاً با تست‌های زیر سروکار دارد:
- تست‌های واحد (Unit Tests): این تست‌ها کوچک‌ترین واحدهای کد (توابع یا متدها) را بررسی می‌کنند و به توسعه‌دهندگان کمک می‌کنند تا اطمینان حاصل کنند که هر واحد به درستی کار می‌کند.- تست‌های یکپارچه‌سازی (Integration Tests): این تست‌ها تعامل بین چندین واحد کد را بررسی می‌کنند تا مطمئن شوند که بخش‌های مختلف سیستم به درستی با هم کار می‌کنند.
### تیم تضمین کیفیت (QA Team)
تیم تضمین کیفیت معمولاً با تست‌های زیر سروکار دارد:- تست‌های سیستم (System Tests): این تست‌ها کل سیستم را به عنوان یک واحد بررسی می‌کنند و از صحت عملکرد کل سیستم اطمینان حاصل می‌کنند.
- تست‌های پذیرش (Acceptance Tests): این تست‌ها صحت عملکرد سیستم را بر اساس نیازمندی‌ها و معیارهای پذیرش بررسی می‌کنند.- تست‌های کاربری (User Acceptance Testing - UAT): این تست‌ها توسط کاربران نهایی انجام می‌شود تا مطمئن شوند که سیستم نیازمندی‌های آن‌ها را برآورده می‌کند.
### شرح مختصر انواع تست‌ها
- تست واحد (Unit Test): تست‌هایی که برای بررسی عملکرد صحیح و دقیق یک واحد خاص از کد نوشته می‌شوند.- تست یکپارچه‌سازی (Integration Test): تست‌هایی که تعامل بین واحدهای مختلف کد را بررسی می‌کنند.
- تست سیستم (System Test): تست‌هایی که کل سیستم را به عنوان یک واحد بررسی می‌کنند.- تست پذیرش (Acceptance Test): تست‌هایی که بررسی می‌کنند آیا سیستم نیازمندی‌ها و معیارهای پذیرش را برآورده می‌کند.
- تست کاربری (User Acceptance Test - UAT): تست‌هایی که توسط کاربران نهایی انجام می‌شود تا مطمئن شوند سیستم نیازمندی‌های آن‌ها را برآورده می‌کند.
## ۳. روال پروژه json-simple برای به‌دست آوردن اعداد پوشش آزمون
برای به‌دست آوردن اعداد پوشش آزمون در پروژه خود، مراحل زیر را انجام دهید:۱. بر روی پکیج java در مسیر test کلیک راست کنید.
۲. گزینه Coverage with Tests All Run را انتخاب کنید.۳. اعداد پوشش آزمون برای پروژه به دست خواهند آمد.
نیازی به اجرای عادی پروژه نیست و می‌توانید فقط از این روش برای به‌دست آوردن اعداد پوشش آزمون استفاده کنید.
## ۴. بهبود پوشش آزمون با افزودن بخش‌هایی به کد تست
برای بهبود اعداد پوشش آزمون، بخش‌های معناداری به کد تست اضافه کنید. این بخش‌ها باید:
- تعاملات واقعی و کاربردی بین کلاس‌ها و متدها را شامل شوند.- مواردی از کد را پوشش دهند که در تست‌های قبلی پوشش داده نشده‌اند.
- به درک بهتر عملکرد کلی سیستم کمک کنند.
نکته مهم: از اضافه کردن تست‌هایی که تنها یک فراخوانی ساده از کلاس یا متد هستند و بدون استفاده در بخش‌های دیگر کد کاربردی ندارند، خودداری کنید.
