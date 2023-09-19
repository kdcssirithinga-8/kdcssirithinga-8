package com.blackey.fuel_delivery_app2;

public class User {
    private String username;
    private String password;

    public User() {
        // Default constructor required for Firebase Realtime Database
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
