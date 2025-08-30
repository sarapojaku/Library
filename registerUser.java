private void registerUser() {
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
    library.addUser(new User(username, password, User.Role.MEMBER));

    // Save the library immediately so new user persists
    library.save();

    System.out.println("Registration successful. You can now log in.");
}
