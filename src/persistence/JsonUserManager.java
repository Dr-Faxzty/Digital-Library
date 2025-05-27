package persistence;

import model.User;


public class JsonUserManager extends JsonTemplateManager<User> {
    private static JsonUserManager JUserManagerInstance;

    public JsonUserManager() {
        super("C:\\Users\\marti\\OneDrive\\Desktop\\Digital-Library\\database\\users.json", User[].class);
    }

    public static JsonUserManager getInstance() {
        if (JUserManagerInstance == null) {
            JUserManagerInstance = new JsonUserManager();
        }
        return JUserManagerInstance;
    }
}

