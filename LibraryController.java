import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class LibraryController {

    private Book[] allBooks = {
        new PrintedBook(1, "1984", "George Orwell", "A1"),
        new EBook(2, "1984", "George Orwell", "https://ebook1984.com"),
        new PrintedBook(3, "Pride and Prejudice", "Jane Austen", "B2"),
        new EBook(4, "Pride and Prejudice", "Jane Austen", "https://ebookpride.com"),
        new EBook(5, "Digital Fortress", "Dan Brown", "https://example1.com"),
        new EBook(6, "Clean Code", "Robert Martin", "https://example2.com")
    };

    @GetMapping("/books")
    public Book[] getAllBooks() {
        return allBooks;
    }

    @PostMapping("/borrow/{bookId}")
    public String borrowBook(@PathVariable int bookId) {
        Book book = allBooks[bookId - 1]; // simple example
        if (book.isAvailable) {
            book.borrowBook();
            return "Borrowed successfully!";
        } else {
            return "Book not available.";
        }
    }
}
