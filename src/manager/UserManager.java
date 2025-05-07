package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import model.Role;
import model.User;
import persistence.JsonUserManager;
import utils.HashUtil;

public class UserManager {
    private static UserManager instance;
    private final List<User> users = new ArrayList<>();

    public UserManager() {
        List<User> initialUsers = JsonUserManager.loadUsers();
        users.addAll(initialUsers);
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void save(User user){
        users.add(user);
    }

    public User findByEmail(String email){
        for(User user : users){
            if(user.getEmail().equals(email)) return user;
        }
        return null;
    }

    public boolean existsByEmail(String email) {
        for(User user : users) {
            if(user.getEmail().equals(email)) return true;
        }
        return false;
    }

    public User register(String name, String surname, String taxIdCode, String email, String password, Role role){
        if(existsByEmail(email)){ return null; }
        String hashedPassword = HashUtil.hashPassword(password);
        User user = new User(name, surname, taxIdCode, email, hashedPassword, role);
        save(user);
        return user;
    }

    public User login(String email, String password){
        String hashedPassword = HashUtil.hashPassword(password);
        User user = findByEmail(email);
        if(user != null && user.getPassword().equals(hashedPassword)){
            return user;
        }else{
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
