package com.example.androidproject01;

import java.io.Serializable;

public class User implements Serializable {
    String id;
    String pass;
    String name;

    public User(String id, String pass, String name) {
        this.id = id;
        this.pass = pass;
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public String getPass() {
        return pass;
    }
    public String getName() {
        return name;
    }
}
