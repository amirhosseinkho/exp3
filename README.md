# exp3
markdown 
# گزارش پروژه کتابخانه 
 
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
