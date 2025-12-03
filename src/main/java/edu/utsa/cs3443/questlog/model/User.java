package edu.utsa.cs3443.questlog.model;

public class User {
    //private String id;          // UUID or generated      // I don't really think we need the ID
    private String username;
    private String email;
    private String password;      // or plain for class project

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return username + " (" + email + ")";
    }

}
