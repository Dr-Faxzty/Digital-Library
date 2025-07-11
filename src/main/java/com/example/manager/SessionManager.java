package com.example.manager;

import com.example.common.enums.Role;
import com.example.common.interfaces.IUser;
import com.example.common.nullObject.NullUser;

public class SessionManager {
    private static SessionManager instance;
    private IUser loggedUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(IUser user) { this.loggedUser = user; }

    public void logout() { this.loggedUser = new NullUser(); }

    public IUser getLoggedUser() { return loggedUser; }

    public boolean isLoggedIn() { return !(loggedUser.isNull()); }

    public boolean isAdmin() { return !(loggedUser.isNull()) && loggedUser.getRole().equals(Role.ADMIN); }
}