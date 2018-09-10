package com.filesystem;

public interface FileAccess {

    boolean hasAccess(Permission p, UserGroup group);
    void setAccess(Permission p, UserGroup group);
}
