package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import model.User;

public class UserList{
    private final List<User> users = new ArrayList<>();

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

    public Stream<User> stream() {
        return users.stream();
    }

    public List<User> toList() {
        return new ArrayList<>(users);
    }
}