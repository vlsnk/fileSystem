package com.filesystem;

import com.sun.istack.internal.NotNull;
import java.util.Collection;

public interface Access {

    /**
     * Create user or return user with this name
     * @param name username
     * @return user from users collection
     */
    User createUser(@NotNull String name);

    /**
     * Add user to users collection or return user with this name
     * @param user username
     * @return user from users collection
     */
    User createUser(@NotNull User user);

    /**
     * Create user and add to users collection or return user with this name
     * Add user to groups collection
     * @param name username
     * @param group UserGroup
     * @return user from users collection
     */
    User createUser(@NotNull String name, @NotNull UserGroup group);

    /**
     * Create group or return group with this name
     * @param name groupname
     * @return group from groups collection
     */
    UserGroup createGroup(@NotNull String name);

    /**
     * Create group or return group with this name
     * @param name groupname
     * @param user add this user to new group
     * @return group from groups collection
     */
    UserGroup createGroup(@NotNull String name, @NotNull User user);

    /**
     * @return all users
     */
    Collection<User> getUsers();

    /**
     * @return all groups
     */
    Collection<UserGroup> getGroups();
}
