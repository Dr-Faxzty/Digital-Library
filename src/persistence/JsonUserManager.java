package persistence;

import model.User;


public class JsonUserManager extends JsonTemplateManager<User> {
    public JsonUserManager() {
        super("database/users.json", User[].class);
    }
}

