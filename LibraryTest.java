import java.util.Scanner;

public class LibraryTest {
    public static void main(String[] args) {
        // Create an account
        Account userAccount = new Account("admin", "1234");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userAccount.login(username, password)) {
            System.out.println("Login successful! Access granted.\n");

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
                b.getDetails();
            }
        } else {
            System.out.println("Invalid username or password. Access denied.");
        }

        scanner.close();
    }
}
