import java.util.Optional;

public class AuthService {
    private final Library library;

    public AuthService(Library library) {
        this.library = library;
    }

    public Optional<User> login(String username, String password) {
        return Optional.ofNullable(library.findUser(username, password));
    }
}
