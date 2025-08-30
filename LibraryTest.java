import java.util.ArrayList;
import java.util.Scanner;

public class LibraryTest {
    public static void main(String[] args) {
        // Create multiple accounts
        Account[] users = {
            new Account("admin", "1234"),
            new Account("user1", "pass1"),
            new Account("user2", "pass2")
        };

        // Create books
        Book[] allBooks = {
            new PrintedBook(1, "1984", "George Orwell", "A1"),
            new EBook(2, "1984", "George Orwell", "https://ebook1984.com"),
            new PrintedBook(3, "Pride and Prejudice", "Jane Austen", "B2"),
            new EBook(4, "Pride and Prejudice", "Jane Austen", "https://ebookpride.com"),
            new EBook(5, "Digital Fortress", "Dan Brown", "https://example1.com"),
            new EBook(6, "Clean Code", "Robert Martin", "https://example2.com")
        };

        Scanner scanner = new Scanner(System.in);
        boolean continueProgram = true;

        while (continueProgram) {
            // Login process
            Account loggedUser = null;
            while (loggedUser == null) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                for (Account user : users) {
                    if (user.login(username, password)) {
                        loggedUser = user;
                        break;
                    }
                }

                if (loggedUser == null) {
                    System.out.println("Invalid username or password. Try again.\n");
                }
            }

            System.out.println("\nLogin successful! Welcome " + loggedUser.getUsername() + "\n");

            int choice;
            do {
                System.out.println("===== Library Menu =====");
                System.out.println("1. View all books");
                System.out.println("2. Borrow a book");
                System.out.println("3. Return a book");
                System.out.println("4. View my borrowed books");
                System.out.println("5. Logout");
                System.out.println("6. Search books by title or author");
                System.out.print("Choose an option: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("\nAll Books:");
                        for (Book b : allBooks) {
                            b.getDetails();
                            System.out.println(b.isAvailable ? "Status: Available\n" : "Status: Not Available\n");
                        }
                        break;

                    case 2:
                        // List available books with index
                        ArrayList<Book> availableBooks = new ArrayList<>();
                        System.out.println("\nAvailable Books:");
                        int index = 1;
                        for (Book b : allBooks) {
                            if (b.isAvailable) {
                                String type = (b instanceof EBook) ? "EBook" : "PrintedBook";
                                System.out.println(index + ". " + b.title + " by " + b.author + " (" + type + ")");
                                availableBooks.add(b);
                                index++;
                            }
                        }
                        if (availableBooks.isEmpty()) {
                            System.out.println("No books available to borrow.\n");
                            break;
                        }

                        System.out.print("Enter the number of the book to borrow: ");
                        int bookChoice = scanner.nextInt();
                        if (bookChoice >= 1 && bookChoice <= availableBooks.size()) {
                            Book selectedBook = availableBooks.get(bookChoice - 1);
                            loggedUser.borrowBook(selectedBook);
                        } else {
                            System.out.println("Invalid choice.\n");
                        }
                        break;

                    case 3:
                        // List user's borrowed books with index
                        ArrayList<Book> borrowed = loggedUser.getBorrowedBooks();
                        if (borrowed.isEmpty()) {
                            System.out.println("\nYou have not borrowed any books.\n");
                            break;
                        }

                        System.out.println("\nYour Borrowed Books:");
                        for (int i = 0; i < borrowed.size(); i++) {
                            Book b = borrowed.get(i);
                            String type = (b instanceof EBook) ? "EBook" : "PrintedBook";
                            System.out.println((i + 1) + ". " + b.title + " by " + b.author + " (" + type + ")");
                        }

                        System.out.print("Enter the number of the book to return: ");
                        int returnChoice = scanner.nextInt();
                        if (returnChoice >= 1 && returnChoice <= borrowed.size()) {
                            Book selectedBook = borrowed.get(returnChoice - 1);
                            loggedUser.returnBook(selectedBook);
                        } else {
                            System.out.println("Invalid choice.\n");
                        }
                        break;

                    case 4:
                        System.out.println("\nYour Borrowed Books:");
                        borrowed = loggedUser.getBorrowedBooks();
                        if (borrowed.isEmpty()) {
                            System.out.println("You have not borrowed any books.\n");
                        } else {
                            for (Book b : borrowed) {
                                b.getDetails();
                                System.out.println();
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Logging out...\n");
                        break;

                    case 6:
                        scanner.nextLine(); // consume leftover newline
                        System.out.print("Enter keyword to search (title or author): ");
                        String keyword = scanner.nextLine().toLowerCase();
                        boolean foundAny = false;
                        System.out.println("\nSearch Results:");
                        for (Book b : allBooks) {
                            if (b.title.toLowerCase().contains(keyword) || b.author.toLowerCase().contains(keyword)) {
                                b.getDetails();
                                System.out.println(b.isAvailable ? "Status: Available\n" : "Status: Not Available\n");
                                foundAny = true;
                            }
                        }
                        if (!foundAny) {
                            System.out.println("No books found matching \"" + keyword + "\".\n");
                        }
                        break;

                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } while (choice != 5);

            // Ask if the user wants to log in again
            System.out.print("Do you want to log in again? (yes/no): ");
            scanner.nextLine(); // consume leftover newline
            String again = scanner.nextLine().trim().toLowerCase();
            if (!again.equals("yes")) {
                continueProgram = false;
                System.out.println("Exiting program. Goodbye!");
            }
        }

        scanner.close();
    }
}
