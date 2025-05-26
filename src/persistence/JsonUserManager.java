package persistence;

import common.interfaces.IUser;


public class JsonUserManager extends JsonTemplateManager<IUser> {
    private static JsonUserManager JUserManagerInstance;

    public JsonUserManager() {
        super("C:\\Users\\marti\\OneDrive\\Desktop\\Digital-Library\\Digital-Library\\database\\users.json", IUser[].class);
    }

    public static JsonUserManager getInstance() {
        if (JUserManagerInstance == null) {
            JUserManagerInstance = new JsonUserManager();
        }
        return JUserManagerInstance;
    }
}

