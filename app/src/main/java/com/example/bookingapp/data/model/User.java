package com.example.bookingapp.data.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String location;
    private String password;
    private boolean isAdmin;
    public User(){

    }
    public User(String email,String password, int id, String location, String name, boolean isAdmin) {
        this.email = email;
        this.id = id;
        this.location = location;
        this.name = name;
        this.isAdmin = isAdmin;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
