package com.example.common.nullObject;

import com.example.common.enums.Role;
import com.example.common.interfaces.IUser;

public class NullUser implements IUser {

    @Override public String getName() {return "";};
    @Override public String getSurname() {return "";};
    @Override public String getTaxIdCode() {return "";};
    @Override public String getEmail() {return "";};
    @Override public String getPassword() {return "";};
    @Override public Role getRole() {return Role.USER;};

    @Override public void setName(String name) {};
    @Override public void setSurname(String surname) {};
    @Override public void setTaxIdCode(String taxIdCode) {};
    @Override public void setEmail(String email) {};
    @Override public void setPassword(String password) {};
    @Override public void setRole(Role role) {};


    @Override public boolean isNull() {return true;};

    @Override
    public String toString() { return ""; }
}