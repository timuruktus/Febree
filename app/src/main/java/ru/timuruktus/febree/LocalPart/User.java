package ru.timuruktus.febree.LocalPart;

public class User {


    private String password;
    private String email;
    private String login;


    public User() {
    }

    public User(String password, String email, String login) {
        this.password = password;
        this.email = email;
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
