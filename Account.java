import java.util.ArrayList;

public class Account {
    private String username;
    private String password;
    private ArrayList<Book> borrowedBooks;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void borrowBook(Book book) {
        if (book.isAvailable) {
            book.borrowBook();
            borrowedBooks.add(book);
        } else {
            System.out.println(book.title + " is not available.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.returnBook();
            borrowedBooks.remove(book);
        } else {
            System.out.println("You did not borrow this book.");
        }
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public String getUsername() {
        return username;
    }
}
