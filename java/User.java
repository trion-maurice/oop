public abstract class User {

    private final String username;

    public User(String username, String password) {
        this.username = username;
        // Password validation is handled by the login screen for this demo.
    }

    public String getUsername() {
        return username;
    }

}
