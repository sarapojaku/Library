import java.util.*;

public class LibraryApp {
    private final Scanner sc = new Scanner(System.in);
    private final Library library = Library.load();
    private final AuthService authService = new AuthService(library);

    public static void main(String[] args) {
        new LibraryApp().run();
    }

    private void run() {
        System.out.println("=== Welcome to the Library System ===");

        while (true) {
            System.out.println("\n1) Login");
            System.out.println("2) Register (new user)");
            int choice = askInt("Choose: ");

            switch (choice) {
                case 1 -> loginUser();
                case 2 -> registerUser();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // --- Login flow ---
    private void loginUser() {
        String username = ask("Username: ");
        String password = ask("Password: ");
        Optional<User> ou = authService.login(username, password);
        if (ou.isPresent()) {
            User u = ou.get();
            if (u.getRole() == User.Role.ADMIN) adminMenu(u);
            else userMenu(u);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    // --- Register new user ---
    private void registerUser() {
        System.out.println("\n=== Register New User ===");
        String username = ask("Choose a username: ");

        // Check if username exists
        boolean exists = library.getUsers().stream().anyMatch(u -> u.getUsername().equals(username));
        if (exists) {
            System.out.println("Username already exists. Try another.");
            return;
        }

        String password = ask("Choose a password: ");
        library.addUser(new User(username, password, User.Role.MEMBER));
        System.out.println("Registration successful. You can now log in.");
    }

    // --- Admin Menu ---
    private void adminMenu(User admin) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1) List books");
            System.out.println("2) Add book");
            System.out.println("3) Remove book");
            System.out.println("4) Edit book");
            System.out.println("5) List users");
            System.out.println("6) Add user");
            System.out.println("7) Remove user");
            System.out.println("8) See who borrowed a book");
            System.out.println("9) Change my username/password");
            System.out.println("10) Logout");
            int choice = askInt("Choose: ");
            switch (choice) {
                case 1 -> listBooks();
                case 2 -> addBook();
                case 3 -> removeBook();
                case 4 -> editBook();
                case 5 -> listUsers();
                case 6 -> addUser();
                case 7 -> removeUser(admin);
                case 8 -> whoBorrowedBook();
                case 9 -> changeAdminCredentials(admin);
                case 10 -> { library.save(); return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // --- User Menu ---
    private void userMenu(User u) {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1) Search books");
            System.out.println("2) Borrow book");
            System.out.println("3) Return book");
            System.out.println("4) View borrowed books");
            System.out.println("5) Change password");
            System.out.println("6) Logout");
            int choice = askInt("Choose: ");
            switch (choice) {
                case 1 -> listBooks();
                case 2 -> borrowBook(u);
                case 3 -> returnBook(u);
                case 4 -> viewBorrowed(u);
                case 5 -> changePassword(u);
                case 6 -> { library.save(); return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // --- Admin methods ---
    private void listBooks() { library.getBooks().forEach(System.out::println); }

    private void addBook() { library.addBook(new Book(ask("Title: "), ask("Author: "))); }

    private void removeBook() {
        library.removeBook(askInt("Book ID to remove: "));
        System.out.println("Book removed.");
    }

    private void editBook() {
        int id = askInt("Book ID to edit: ");
        Optional<Book> ob = library.findBook(id);
        if (ob.isEmpty()) { System.out.println("Book not found."); return; }
        Book b = ob.get();
        String t = ask("New title (leave blank to keep \"" + b.getTitle() + "\"): ");
        String a = ask("New author (leave blank to keep \"" + b.getAuthor() + "\"): ");
        if (!t.isEmpty()) b.setTitle(t);
        if (!a.isEmpty()) b.setAuthor(a);
        library.save();
        System.out.println("Book updated: " + b);
    }

    private void listUsers() { library.getUsers().forEach(System.out::println); }

    private void addUser() {
        String u = ask("Username: ");
        String p = ask("Password: ");
        String r = ask("Role (admin/member): ");
        User.Role role = r.equalsIgnoreCase("admin") ? User.Role.ADMIN : User.Role.MEMBER;
        library.addUser(new User(u, p, role));
        System.out.println("User added.");
    }

    private void removeUser(User admin) {
        String u = ask("Username to remove: ");
        if (u.equals(admin.getUsername())) { System.out.println("Cannot remove yourself."); return; }
        library.removeUser(u);
        System.out.println("User removed.");
    }

    private void changeAdminCredentials(User admin) {
        String newUsername = ask("New username (leave blank to keep \"" + admin.getUsername() + "\"): ");
        String newPassword = ask("New password (leave blank to keep current): ");

        if (!newUsername.isEmpty()) admin.setUsername(newUsername);
        if (!newPassword.isEmpty()) admin.setPassword(newPassword);

        library.save();
        System.out.println("Admin credentials updated.");
    }

    private void whoBorrowedBook() {
        int bookId = askInt("Enter Book ID to check: ");
        Optional<Book> ob = library.findBook(bookId);
        if (ob.isEmpty()) { System.out.println("Book not found."); return; }
        Book b = ob.get();
        String borrower = null;
        for (Map.Entry<String, List<Book>> entry : library.borrowed.entrySet()) {
            if (entry.getValue().contains(b)) {
                borrower = entry.getKey();
                break;
            }
        }
        if (borrower != null) System.out.println("Book is borrowed by: " + borrower);
        else System.out.println("Book is currently available.");
    }

    // --- User methods ---
    private void borrowBook(User u) { library.borrowBook(u, askInt("Book ID to borrow: ")); }
    private void returnBook(User u) { library.returnBook(u, askInt("Book ID to return: ")); }
    private void viewBorrowed(User u) { library.borrowedBooks(u).forEach(System.out::println); }
    private void changePassword(User u) {
        String newPass = ask("New password: ");
        u.setPassword(newPass);
        library.save();
        System.out.println("Password updated.");
    }

    // --- Helpers ---
    private String ask(String prompt) { System.out.print(prompt); return sc.nextLine().trim(); }
    private int askInt(String prompt) { try { return Integer.parseInt(ask(prompt)); } catch(Exception e){ return -1; } }
}
