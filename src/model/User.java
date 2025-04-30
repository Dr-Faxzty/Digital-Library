package model;

public enum Ruolo{
    ADMIN,
    USER
}

public class User{
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String email;
    private Ruolo ruolo;

    public User() {}

    public User(String nome, String cognome, String codiceFiscale, String email, Ruolo ruolo){
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.ruolo = ruolo;
    }

    public String getNome(){return this.nome;}
    public String getNome(){return this.cognome;}
    public String getNome(){return this.codiceFiscale;}
    public String getNome(){return this.email;}
    public Ruolo getNome(){return this.ruolo;}

    public void setNome(String nome){this.nome = nome;}
    public void setNome(String nome){this.cognome = cognome;}
    public void setNome(String nome){this.codiceFiscale = codiceFiscale;}
    public void setNome(String nome){this.email = email;}
    public void setNome(Ruolo ruolo){this.ruolo = ruolo;}

    @Override
    public String toString(){
        return "User{" +
                "Nome = '" + nome + '\'' +
                "Cognome = '" + cognome + '\'' +
                "Codice Fiscale = '" + codiceFiscale + '\'' +
                "Email = '" + email + '\'' +
                "Ruolo = '" + ruolo + '\'' +
                '}';
    }
}