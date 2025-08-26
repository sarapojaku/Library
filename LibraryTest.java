public class LibraryTest {
    public static void main(String[] args) {
        // Create PrintedBook objects
        PrintedBook printed1 = new PrintedBook(1, "1984", "George Orwell", "A1");
        PrintedBook printed2 = new PrintedBook(2, "Pride and Prejudice", "Jane Austen", "B2");

        // Create EBook objects
        EBook ebook1 = new EBook(3, "Digital Fortress", "Dan Brown", "https://example1.com");
        EBook ebook2 = new EBook(4, "Clean Code", "Robert Martin", "https://example2.com");

        // Store all books in a Book array
        Book[] allBooks = { printed1, printed2, ebook1, ebook2 };

        // Borrow and print details for all books
        for (Book b : allBooks) {
            b.borrowBook();
            b.getDetails(); // Polymorphism calls the correct getDetails()
        }
    }
}