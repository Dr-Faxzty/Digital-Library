package model;

public class User{
    private String name;
    private String surname;
    private String taxIdCode;
    private String email;
    private String password;
    private Role role;

    public User() {}

    public User(String name, String surname, String taxIdCode, String email, String password, Role role){
        this.name = name;
        this.surname = surname;
        this.taxIdCode = taxIdCode;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName(){return this.name;}
    public String getSurname(){return this.surname;}
    public String gettaxIdCode(){return this.taxIdCode;}
    public String getEmail(){return this.email;}
    public String getPassword(){return this.password;}
    public Role getRole(){return this.role;}

    public void setName(String name){this.name = name;}
    public void setSurname(String surname){this.surname = surname;}
    public void settaxIdCode(String taxIdCode){this.taxIdCode = taxIdCode;}
    public void setEmail(String email){this.email = email;}
    public void setPassword(String password){this.password = password;}
    public void setRole(Role role){this.role = role;}

    @Override
    public String toString(){
        return "User{" +
                "name = '" + name + '\'' +
                ", surname = '" + surname + '\'' +
                ", taxIdCode = '" + taxIdCode + '\'' +
                ", email = '" + email + '\'' +
                ", password = '" + password + '\'' +
                ", role = '" + role + '\'' +
                '}';
    }
}