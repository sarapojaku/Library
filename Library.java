import java.io.*;
import java.util.*;

public class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    Map<String, List<Book>> borrowed = new HashMap<>();

    public List<Book> getBooks() { return books; }
    public List<User> getUsers() { return users; }

    public void addBook(Book book) { books.add(book); save(); }
    public void removeBook(int id) { books.removeIf(b -> b.getId() == id); save(); }

    public Optional<Book> findBook(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst();
    }

    public void addUser(User user) { users.add(user); save(); }
    public void removeUser(String username) { users.removeIf(u -> u.getUsername().equals(username)); borrowed.remove(username); save(); }

    public User findUser(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.checkPassword(password))
                .findFirst()
                .orElse(null);
    }

    public void borrowBook(User user, int bookId) {
        Optional<Book> ob = findBook(bookId);
        if (ob.isPresent() && ob.get().isAvailable()) {
            ob.get().setAvailable(false);
            borrowed.computeIfAbsent(user.getUsername(), k -> new ArrayList<>()).add(ob.get());
            save();
        }
    }

    public void returnBook(User user, int bookId) {
        List<Book> list = borrowed.getOrDefault(user.getUsername(), new ArrayList<>());
        for (Book b : new ArrayList<>(list)) {
            if (b.getId() == bookId) {
                b.setAvailable(true);
                list.remove(b);
                break;
            }
        }
        save();
    }

    public List<Book> borrowedBooks(User user) {
        return borrowed.getOrDefault(user.getUsername(), new ArrayList<>());
    }
    
    public static Library load() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library.dat"))) {
        Library lib = (Library) ois.readObject();
        if (lib.borrowed == null) {
            lib.borrowed = new HashMap<>();  // initialize map if null
        }
        return lib;
    } catch (Exception e) {
        Library lib = new Library();
        lib.seed();
        lib.save();
        return lib;
    }
}

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error saving library: " + e.getMessage());
        }
    }

    private void seed() {
        users.add(new User("admin", "admin123", User.Role.ADMIN));
        users.add(new User("alice", "alice123", User.Role.MEMBER));
        users.add(new User("bob", "bob123", User.Role.MEMBER));

        books.add(new Book("1984", "George Orwell"));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien"));
    }
}
