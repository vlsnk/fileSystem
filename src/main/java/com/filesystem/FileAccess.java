package com.filesystem;

import com.filesystem.implementation.Permission;

public interface FileAccess {

    /**
     *
     * @param p
     * @param user
     * @return
     */
    boolean hasAccess(Permission p, UserInterface user);

    /**
     *
     * @param p
     * @param user
     */
    void setAccess(Permission p, UserInterface user);

    /**
     *
     * @return
     */
    FileAccess getDefault();
}
