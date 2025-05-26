package model;

import java.time.LocalDate;
import common.interfaces.IBook;

public class Book implements IBook {
    private String isbn;
    private String title;
    private String author;
    private LocalDate date;
    private String type;
    private boolean available;
    private String urlImage;

    public Book() {}

    public Book(String isbn, String title, String author, LocalDate date, String type, boolean available, String urlImage) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.date = date;
        this.type = type;
        this.available = available;
        this.urlImage = urlImage;
    }

    @Override public String getIsbn() { return isbn; }
    @Override public String getTitle() { return title; }
    @Override public String getAuthor() { return author; }
    @Override public LocalDate getDate() { return date; }
    @Override public String getType() { return type; }
    @Override public boolean available() { return available; }
    @Override public String getUrlImage() { return urlImage; }
    
    @Override public void setIsbn(String isbn) { this.isbn = isbn; }
    @Override public void setTitle(String title) { this.title = title; }
    @Override public void setAuthor(String author) { this.author = author; }
    @Override public void setDate(LocalDate date) { this.date = date; }
    @Override public void setType(String type) { this.type = type; }
    @Override public void setAvailable(boolean available) { this.available = available; }
    @Override public void setUrlImage(String urlImage) { this.urlImage = urlImage; }

    @Override public boolean isNull(){return false;};

    @Override
    public String toString() {
        return "Book {" +
                "isbn = '" + isbn + '\'' +
                ", title = '" + title + '\'' +
                ", author = '" + author + '\'' +
                ", date = '" + date + '\'' +
                ", type = '" + type + '\'' +
                ", available = " + available +
                ", urlImage = '" + urlImage + '\'' +
                '}';
    }
}