public class Account {
    protected String username;
    protected String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

        public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}