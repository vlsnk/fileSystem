package com.filesystem;

import com.sun.istack.internal.NotNull;

public class UserGroup {

    private String name;

    public UserGroup() {
        this.name = "default";
    }

    public UserGroup(@NotNull String name) {
        this.name = name.toLowerCase();
    }

    public String getName() {
        return name;
    }

}
