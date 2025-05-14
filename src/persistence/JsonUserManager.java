package persistence;

import model.User;


public class JsonUserManager extends JsonTemplateManager<User> {
    public JsonUserManager() {
        super("C:\\Users\\marti\\OneDrive\\Desktop\\Digital-Library\\Digital-Library\\database\\users.json", User[].class);
    }
}

