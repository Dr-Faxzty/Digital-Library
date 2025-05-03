import java.util.ArrayList;
import java.util.List;
import manager.UserManager;
import manager.UserMap;
import model.Role;
import model.User;

public class Main {
    public static void main(String[] args) { 
        System.out.println("Hello World"); 

        List<User> users = new ArrayList<>();
        
        UserMap userMap = new UserMap();
        UserManager userM = new UserManager(users, userMap);

        userM.register("John", "Smith", "ABC123", "john@example.com", "password123", Role.USER);

        User loggedInUser = userM.login("john@example.com", "password123");

        if(loggedInUser != null){ System.out.println("Logged in user: " + loggedInUser); }
    }
}
