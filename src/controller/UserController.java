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

    public User register(String name, String surname, String taxCode, String email, String password, Role role) {
        boolean exists = userManager.getAll().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        if (exists) return new NullUser();

        User user = userManager.register(name, surname, taxCode, email, password, role);
        boolean saved = saveUsers();
        return saved ? user : new NullUser();
    }

    public boolean removeUser(String TaxIdCode) {
        User toRemove = userManager.getAll().stream().filter(u -> u.getTaxIdCode().equalsIgnoreCase(TaxIdCode)).findFirst().orElse(new NullUser());
        if (toRemove instanceof NullUser) return false;

        userManager.removeUser(toRemove);
        return saveUsers();
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

    public void loadUsersAsync(java.util.function.Consumer<List<User>> onSuccess, Runnable onError) {
        jsonUserManager.loadAsync(users -> {
            userManager.setAll(users);
            onSuccess.accept(users);
        }, onError);
    }

}
