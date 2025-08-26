public class Book {
    protected int id;
    protected String title;
    protected String author;
    protected boolean isAvailable = true;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

        public void getDetails() {
        System.out.println("ID: " + id + ", Title: " + title + ", Author: " + author);
    }

    public void borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println(title + " borrowed.");
        } else {
            System.out.println(title + " is not available.");
        }
    }

    public void returnBook() {
        isAvailable = true;
        System.out.println(title + " returned.");
    }
}

