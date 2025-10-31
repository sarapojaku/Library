import java.util.Scanner;

public class registerUser {
    private Library library;

    // Constructor to inject the library instance
    public registerUser(Library library) {
        this.library = library;
    }

    // Helper method to get user input
    private String ask(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    // Main method to register new users
    public void registerUser() {
        System.out.println("\n=== Register New User ===");
        String username = ask("Choose a username: ");

        // Check if username already exists
        boolean exists = library.getUsers().stream()
                .anyMatch(u -> u.getUsername().equals(username));

        if (exists) {
            System.out.println("Username already exists. Try another.");
            return;
        }

        String password = ask("Choose a password: ");
        library.addUser(new User(username, password, Role.MEMBER));

        // Persist the updated library
        library.save();

        System.out.println("Registration successful. You can now log in.");
    }

    // Optional: test entry point
    public static void main(String[] args) {
        Library library = Library.load();
        registerUser reg = new registerUser(library);
        reg.registerUser();
    }
}
