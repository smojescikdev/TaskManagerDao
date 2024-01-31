package pl.coderslab.entity;

public class User {

    public User() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int id;
    private String userName;
    private String email;
    private String password;


    public User(int id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.userName = username;
        this.password = password;
    }

}
