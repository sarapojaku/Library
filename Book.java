import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int counter = 1;

    private int id;
    private String title;
    private String author;
    private boolean available = true;

    public Book(String title, String author) {
        this.id = counter++;
        this.title = title;
        this.author = author;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return id + ": " + title + " by " + author + " (" + (available ? "Available" : "Borrowed") + ")";
    }
}
