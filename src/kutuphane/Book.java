package kutuphane;

public class Book implements Searchable {
    private String isbn;
    private String title;
    private BookStatus status;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
        this.status = BookStatus.AVAILABLE;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    @Override
    public boolean search(String keyword) {
        return title.toLowerCase().contains(keyword.toLowerCase());
    }

    
    @Override
    public String toString() {
        return isbn + ";" + title + ";" + status;
    }
}