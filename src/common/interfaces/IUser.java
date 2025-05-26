package common.interfaces;

import common.enums.Role;

public interface IUser{
    String getName();
    String getSurname();
    String getTaxIdCode();
    String getEmail();
    String getPassword();
    Role getRole();

    void setName(String name);
    void setSurname(String surname);
    void setTaxIdCode(String taxIdCode);
    void setEmail(String email);
    void setPassword(String password);
    void setRole(Role role);

    boolean isNull();
}
