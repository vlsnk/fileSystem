package com.filesystem;

import com.filesystem.implementation.User;
import com.filesystem.implementation.UserGroup;
import com.filesystem.implementation.WrongNameException;

import java.util.Collection;

public interface Access {

    /**
     * Create user or return user with this name
     * @param name username
     * @return user from users collection
     * @throws WrongNameException if user name is null or empty string
     */
    User createUser(String name) throws WrongNameException;

    /**
     * Search user with this name
     * @param name username
     * @return user from users collection or null
     */
    User findUser(String name);

    /**
     * Create user and add to users collection or return user with this name
     * Add user to groups collection
     * @param name username
     * @param group UserGroup
     * @return user from users collection
     * @throws WrongNameException if user name is null or empty string
     */
    User createUser(String name, UserGroup group) throws WrongNameException;

    /**
     * Create group or return group with this name
     * @param name groupname
     * @return group from groups collection
     * @throws WrongNameException if group name is null or empty string
     */
    UserGroup createGroup(String name) throws WrongNameException;

    /**
     * Search group with this name
     * @param name groupname
     * @return group from groups collection or null
     */
    UserGroup findGroup(String name);

    /**
     * Create group or return group with this name
     * @param name groupname
     * @param user add this user to new group
     * @return group from groups collection
     * @throws WrongNameException if group name is null or empty string
     */
    UserGroup createGroup(String name, User user) throws WrongNameException;

    /**
     * @return all users
     */
    Collection<User> getUsers();

    /**
     * @return all groups
     */
    Collection<UserGroup> getGroups();

    /**
     *
     * @return default user
     */
    User getDefaultUser();

    /**
     * Default group contains default user
     * @return default group
     */
    UserGroup getDefaultGroup();
}
