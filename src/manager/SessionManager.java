package manager;

import model.User;
import common.enums.Role;
import common.nullObject.NullUser;

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

    public void logout() { this.loggedUser = new NullUser(); }

    public User getLoggedUser() { return loggedUser; }

    public boolean isLoggedIn() { return !(loggedUser instanceof NullUser); }

    public boolean isAdmin() { return !(loggedUser instanceof NullUser) && loggedUser.getRole().equals(Role.ADMIN); }
}