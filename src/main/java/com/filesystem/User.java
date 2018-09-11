package com.filesystem;

public class User implements UserInterface {

    String name;

    public User(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
