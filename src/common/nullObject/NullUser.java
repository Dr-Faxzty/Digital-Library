package common.nullObject;

import common.enums.Role;
import model.User;

public class NullUser extends User {
    public NullUser() { super("", "", "", "", "", Role.USER); }

    @Override
    public String toString() { return ""; }
}