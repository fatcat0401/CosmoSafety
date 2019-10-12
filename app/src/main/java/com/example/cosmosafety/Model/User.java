package com.example.cosmosafety.Model;

public class User {
    private String username;
    private String password, image;

    public User(String username, String password, String image) {
        this.username = username;
        this.password = password;
        this.image = image;
    }

    public User()
    {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
