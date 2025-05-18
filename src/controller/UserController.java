package controller;

import manager.UserManager;
import common.nullObject.NullUser;
import common.enums.Role;
import model.User;
import persistence.JsonUserManager;

import java.util.List;

public class UserController {
    private final UserManager userManager;
    private final JsonUserManager jsonUserManager;

    public UserController() {
        this.userManager = UserManager.getInstance();
        this.jsonUserManager = new JsonUserManager();
    }

    public User login(String email, String password) {
        return userManager.login(email, password);
    }

    public User register(String name, String surname, String username, String email, String password, Role role) {
        boolean exists = userManager.getAll().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        if (exists) return null;

        User user = userManager.register(name, surname, username, email, password, role);
        boolean saved = saveUsers();
        return saved ? user : new NullUser();
    }

    public List<User> getAllUsers() {
        return userManager.getAll();
    }

    private List<User> loadUsers() {
        List<User> users = jsonUserManager.load();
        if (users != null) {
            userManager.setAll(users);
        }
        return users;
    }

    private boolean saveUsers() {
        return jsonUserManager.save(userManager.getAll());
    }
}
