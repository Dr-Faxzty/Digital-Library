package com.example.controller;

import com.example.common.enums.Role;
import com.example.common.interfaces.IUser;
import com.example.common.nullObject.NullUser;
import com.example.manager.UserManager;
import com.example.model.User;
import com.example.persistence.JsonUserManager;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static UserController UserInstance;
    private final UserManager userManager;
    private final JsonUserManager jsonUserManager;

    private UserController() {
        this.userManager = UserManager.getInstance();
        this.jsonUserManager = JsonUserManager.getInstance();

        List<IUser> initialUsers = loadUsers();
        userManager.setInitialUsers(initialUsers);
    }

    public static UserController getInstance() {
        if (UserInstance == null) {
            UserInstance = new UserController();
        }
        return UserInstance;
    }

    public IUser login(String email, String password) {
        return userManager.login(email, password);
    }

    public IUser register(String name, String surname, String username, String email, String password, Role role) {
        boolean exists = userManager.getAll().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        if (exists) return new NullUser();

        IUser user = userManager.register(name, surname, username, email, password, role);
        boolean saved = saveUsers();
        return saved ? user : new NullUser();
    }

    public boolean removeUser(String TaxIdCode) {
        IUser toRemove = userManager.getAll().stream().filter(u -> u.getTaxIdCode().equalsIgnoreCase(TaxIdCode)).findFirst().orElse(new NullUser());
        if (toRemove.isNull()) return false;

        userManager.removeUser(toRemove);
        return saveUsers();
    }

    public List<IUser> getAllUsers() {
        return userManager.getAll();
    }

    private List<IUser> loadUsers() {
        List<User> concreteUsers = jsonUserManager.load();
        return new ArrayList<>(concreteUsers);
    }

    private boolean saveUsers() {
        List<User> users = userManager.getAll().stream()
                .map(user -> (User) user)
                .toList();
        return jsonUserManager.save(users);
    }

    public void loadUsersAsync(java.util.function.Consumer<List<IUser>> onSuccess, Runnable onError) {
        jsonUserManager.loadAsync(users -> {
            List<IUser> iusers = users.stream()
                    .map(user -> (IUser) user)
                    .toList();
            userManager.setInitialUsers(iusers);
            onSuccess.accept(iusers);
        }, onError);
    }

}
