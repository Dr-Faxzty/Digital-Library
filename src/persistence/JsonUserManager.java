package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUserManager {
    private static final String FILE_PATH = "database/users.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static boolean saveUsers(List<User> users) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static List<User> loadUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            User[] array = gson.fromJson(reader, User[].class);
            List<User> list = new ArrayList<>();
            if (array != null) {
                list.addAll(Arrays.asList(array));
            }
            return list;
        } catch (IOException e) {
            System.out.println("Error while loading users");
            return new ArrayList<>();
        }
    }
}
