package com.filesystem;

import com.sun.istack.internal.NotNull;

import java.util.HashSet;
import java.util.Set;

public class UserGroup implements UserInterface {

    private String name;
    private Set<User> users;

//    public UserGroup() {
//        this.name = "default";
//        this.users = new HashSet<>();
//    }

    public UserGroup(@NotNull String name) {
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

}
