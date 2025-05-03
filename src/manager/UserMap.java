package manager;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserMap{
    private final Map<String, User> usersByEmail = new HashMap<>();

    public void save(User user){
        usersByEmail.put(user.getEmail(), user);
    }

    public User findByEmail(String email){
        return usersByEmail.get(email);
    }

    public boolean existsByEmail(String email){
        return usersByEmail.containsKey(email);
    }
}