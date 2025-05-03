package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import model.Role;
import model.User;

public class UserManager {
    private final List<User> users = new ArrayList<>();
    private final UserMap userMap;

    public UserManager(List<User> initialUsers, UserMap userMap) {
        if (initialUsers != null) {
            users.addAll(initialUsers);
        }
        this.userMap = userMap;
    }

    public boolean register(String name, String surname, String taxIdCode, String email, String password, Role role){
        if(userMap.existsByEmail(email)){
            System.out.println("Registration failed: Email already exists.");
            return false;
        }

        User user = new User(name, surname, taxIdCode, email, password, role);
        userMap.save(user);
        System.out.println("User registered successfully.");
        return true;
    }

    public User login(String email, String password){
        User user = userMap.findByEmail(email);
        if(user != null && user.getPassword().equals(password)){
            System.out.println("Login successful.");
            return user;
        }else{
            System.out.println("Login failed: Incorrect email or password.");
            return null;
        }
    }

    public List<User> search(Predicate<User> filter) {
        return users.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers() { return new ArrayList<>(users); }
}
