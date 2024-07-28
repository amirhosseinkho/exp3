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

    public void addStudent(Student student) {
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
