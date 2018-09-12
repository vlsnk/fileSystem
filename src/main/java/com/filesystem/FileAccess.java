package com.filesystem;

import com.filesystem.implementation.Permission;

public interface FileAccess {

    /**
     * check if user/group has permission to access this file/directory
     * @param p Permission level
     * @param user user/group object
     * @return true if user/group has access or false
     */
    boolean hasAccess(Permission p, UserInterface user);

    /**
     * set permission for user/group to access this file/directory
     * @param p Permission level
     * @param user user/group object
     */
    void setAccess(Permission p, UserInterface user);

    /**
     * remove permission for user/group to access this file/directory
     * @param p Permission level
     * @param user user/group object
     */
    void removeAccess(Permission p, UserInterface user);

    /**
     * remove all permission for user/group to access this file/directory
     * @param user user/group object
     */
    void removeAccess(UserInterface user);

    /**
     * not implemented
     * @return
     */
    FileAccess getDefault();
}
