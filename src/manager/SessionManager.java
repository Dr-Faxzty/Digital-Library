package manager;

import model.User;
import model.Role;

public class SessionManager {
    private static SessionManager instance;
    private User loggedUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) { this.loggedUser = user; }

    public void logout() { this.loggedUser = null; }

    public User getLoggedUser() { return loggedUser; }

    public boolean isLoggedIn() { return loggedUser != null; }

    public boolean isAdmin() { return loggedUser != null && loggedUser.getRole().equals(Role.ADMIN); }
}