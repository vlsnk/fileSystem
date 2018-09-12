package com.filesystem.implementation;

import com.filesystem.UserInterface;

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
