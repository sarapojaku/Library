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
            new PrintedBook(2, "Pride and Prejudice", "Jane Austen", "B2"),
            new EBook(3, "Digital Fortress", "Dan Brown", "https://example1.com"),
            new EBook(4, "Clean Code", "Robert Martin", "https://example2.com")
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
                        System.out.print("Enter book ID to borrow: ");
                        int borrowId = scanner.nextInt();
                        boolean foundBorrow = false;
                        for (Book b : allBooks) {
                            if (b.id == borrowId) {
                                loggedUser.borrowBook(b);
                                foundBorrow = true;
                                break;
                            }
                        }
                        if (!foundBorrow) System.out.println("Book ID not found.");
                        break;

                    case 3:
                        System.out.print("Enter book ID to return: ");
                        int returnId = scanner.nextInt();
                        boolean foundReturn = false;
                        for (Book b : allBooks) {
                            if (b.id == returnId) {
                                loggedUser.returnBook(b);
                                foundReturn = true;
                                break;
                            }
                        }
                        if (!foundReturn) System.out.println("Book ID not found.");
                        break;

                    case 4:
                        System.out.println("\nYour Borrowed Books:");
                        if (loggedUser.getBorrowedBooks().isEmpty()) {
                            System.out.println("You have not borrowed any books.");
                        } else {
                            for (Book b : loggedUser.getBorrowedBooks()) {
                                b.getDetails();
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Logging out...\n");
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
