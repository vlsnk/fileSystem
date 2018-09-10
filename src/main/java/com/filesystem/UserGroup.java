package com.filesystem;

import com.sun.istack.internal.NotNull;

public class UserGroup {

    String name;

    public UserGroup(@NotNull String name) {
        this.name = name.toLowerCase();
    }

}
