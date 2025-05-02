package manager;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserManager {
    private final List<User> users = new ArrayList<>();

    public UserManager(List<User> initialUsers) {
        if (initialUsers != null) {
            users.addAll(initialUsers);
        }
    }

    public List<User> search(Predicate<User> filter) {
        return users.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers() { return new ArrayList<>(users); }
}
