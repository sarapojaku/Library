public class PrintedBook extends Book {
    private String shelfLocation;

    // Constructor
    public PrintedBook(int id, String title, String author, String shelfLocation) {
        super(id, title, author); // Call the constructor of Book
        this.shelfLocation = shelfLocation;
    }

    public void getDetails() {
        System.out.println("ID: " + id + ", Title: " + title + ", Author: " + author + ", Shelf: " + shelfLocation);
    }

        public String getShelfLocation() {
        return shelfLocation;
    }
}