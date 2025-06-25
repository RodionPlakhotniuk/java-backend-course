package org.example;

import java.util.Objects;

public class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        if (title == null || author == null || title.isBlank() || author.isBlank()) {
            throw new IllegalArgumentException("Назва та автор не можуть бути порожнім.");
        }
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Заголовок не може бути порожнім.");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Автор не може бути null або порожнім.");
        }
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return title.equalsIgnoreCase(book.title) && author.equalsIgnoreCase(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), author.toLowerCase());
    }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "'}";
    }
}
