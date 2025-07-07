package com.example.model;

import com.example.common.enums.Role;
import com.example.common.interfaces.IUser;

public class User implements IUser {
    private String name;
    private String surname;
    private String taxIdCode;
    private String email;
    private String password;
    private Role role;

    public User() {}

    public User(String name, String surname, String taxIdCode, String email, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.taxIdCode = taxIdCode;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override public String getName() { return this.name; }
    @Override public String getSurname() { return this.surname; }
    @Override public String getTaxIdCode() { return this.taxIdCode; }
    @Override public String getEmail() { return this.email; }
    @Override public String getPassword() { return this.password; }
    @Override public Role getRole() { return this.role; }

    @Override public void setName(String name) { this.name = name; }
    @Override public void setSurname(String surname) { this.surname = surname; }
    @Override public void setTaxIdCode(String taxIdCode) { this.taxIdCode = taxIdCode; }
    @Override public void setEmail(String email) { this.email = email; }
    @Override public void setPassword(String password) { this.password = password; }
    @Override public void setRole(Role role) { this.role = role; }

    @Override public boolean isNull(){return false;};

    @Override
    public String toString() {
        return "User {" +
                "name = '" + name + '\'' +
                ", surname = '" + surname + '\'' +
                ", taxIdCode = '" + taxIdCode + '\'' +
                ", email = '" + email + '\'' +
                ", password = '" + password + '\'' +
                ", role = '" + role + '\'' +
                '}';
    }
}