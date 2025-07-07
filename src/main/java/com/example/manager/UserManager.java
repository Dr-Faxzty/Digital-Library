package com.example.manager;

import com.example.common.enums.Role;
import com.example.common.interfaces.IUser;
import com.example.common.interfaces.Manager;
import com.example.common.nullObject.NullUser;
import com.example.model.User;
import com.example.utils.HashUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class UserManager implements Manager<IUser> {
    private static UserManager instance;
    private final List<IUser> users = new ArrayList<>();

    public UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setInitialUsers(List<IUser> initialsUsers) {
        this.users.clear();
        this.users.addAll(initialsUsers);
    }

    public void addUser(IUser user){
        users.add(user);
    }

    public void removeUser(IUser user) { users.remove(user); }

    public IUser findByEmail(String email){
        for(IUser user : users){
            if(user.getEmail().equals(email)) return user;
        }
        return new NullUser();
    }

    public boolean existsByEmail(String email) {
        for(IUser user : users) {
            if(user.getEmail().equals(email)) return true;
        }
        return false;
    }

    public IUser register(String name, String surname, String taxIdCode, String email, String password, Role role){
        if(existsByEmail(email)){ return new NullUser(); }
        String hashedPassword = HashUtil.hashPassword(password);
        IUser user = new User(name, surname, taxIdCode, email, hashedPassword, role);
        addUser(user);
        return user;
    }

    public IUser login(String email, String password){
        String hashedPassword = HashUtil.hashPassword(password);
        IUser user = findByEmail(email);
        if(!(user.isNull()) && user.getPassword().equals(hashedPassword)){
            return user;
        }else{
            return new NullUser();
        }
    }

    @Override
    public List<IUser> search(Predicate<IUser> filter) {
        return users.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }


    @Override
    public List<IUser> getAll() { return new ArrayList<>(users); }


}