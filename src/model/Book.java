public class Book {
    private String isbn;
    private String title;
    private String author;
    private String date;
    private String type;
    private boolean isAvailable;
    private String urlImage;

    public Book() {}

    public Book(String isbn, String title, String author, String date, String type, boolean isAvailable, String urlImage) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.date = date;
        this.type = type;
        this.isAvailable = isAvailable;
        this.urlImage = urlImage;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDate() { return date; }
    public String getType() { return type; }
    public boolean isAvailable() { return isAvailable; }
    public String getUrlImage() { return urlImage; }
    
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setDate(String date) { this.date = date; }
    public void setType(String type) { this.type = type; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", isAvailable=" + isAvailable +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }
}