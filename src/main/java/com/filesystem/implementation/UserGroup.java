package com.filesystem.implementation;

import com.filesystem.UserInterface;
import com.sun.istack.internal.NotNull;

import java.util.HashSet;
import java.util.Set;

public class UserGroup implements UserInterface {

    private String name;
    private Set<User> users;


    UserGroup(String name) {
        this.name = name.toLowerCase();
        this.users = new HashSet<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public Set<User> getUsers(){
        return this.users;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        this.users.remove(user);
    }

    public boolean containUser(User user){
        return users.contains(user);
    }
}
