package com.filesystem.implementation;

import com.filesystem.Access;

import java.util.*;

/**
 * Contain all users and groups for VirtualFileSystem
 */
public class AccessManager implements Access {

    private static AccessManager instance;
    private Map<String, User> users;
    private Map<String, UserGroup> groups;
    private final static String DEFAULT_USER = "defaultuser";
    private final static String DEFAULT_GROUP = "defaultdroup";
    private static final String WRONG_NAME = "Name is not valid";

    /**
     * Constructor creates AccessManager
     * Create Default user and default group
     */
    private AccessManager() {
        users = new HashMap<>();
        User defUser = new User(DEFAULT_USER);
        users.put(defUser.getName(), defUser);
        groups = new HashMap<>();
        UserGroup defGroup = new UserGroup(DEFAULT_GROUP);
        defGroup.addUser(defUser);
        groups.put(defGroup.getName(), defGroup);
    }

    public static AccessManager getInstance(){
        if (instance == null) {
            instance = new AccessManager();
        }
        return instance;
    }

    /**
     * Create user or return user with this name
     * @param name username
     * @return user from users collection
     */
    @Override
    public User createUser(String name) throws WrongNameException {
        validateName(name);
        if (!users.containsKey(name.toLowerCase())) {
            User user = new User(name);
            users.put(user.getName(), user);
        }
        return users.get(name);
    }

    /**
     * Search user with this name
     * @param name username
     * @return user from users collection or null
     */
    @Override
    public User findUser(String name) {
        return users.get(name);
    }

    /**
     * Add user to users collection or return user with this name
     * @param user username
     * @return user from users collection
     */
    private User createUser(User user) {
        if (!users.containsKey(user.getName())) {
            users.put(user.getName(), user);
        }
        return users.get(user.getName());
    }

    /**
     * Create user and add to users collection or return user with this name
     * Add user to groups collection
     * @param name username
     * @param group UserGroup
     * @return user from users collection
     */
    @Override
    public User createUser(String name, UserGroup group) throws WrongNameException {
        User user = createUser(name);
        if (groups.containsKey(group.getName())){
            UserGroup g = groups.get(group.getName());
            g.addUser(user);
            groups.put(g.getName(), g);
        }
        return user;
    }

    /**
     * Create group or return group with this name
     * @param name groupname
     * @return group from groups collection
     */
    @Override
    public UserGroup createGroup(String name) throws WrongNameException {
        validateName(name);
        if (!groups.containsKey(name.toLowerCase())) {
            UserGroup g = new UserGroup(name);
            groups.put(g.getName(), g);
        }
        return groups.get(name);
    }

    /**
     * Search group with this name
     * @param name groupname
     * @return group from groups collection or null
     */
    @Override
    public UserGroup findGroup(String name) {
        return groups.get(name.toLowerCase());
    }


    /**
     * Create group or return group with this name
     * @param name groupname
     * @param user add this user to new group
     * @return group from groups collection
     */
    @Override
    public UserGroup createGroup(String name, User user) throws WrongNameException {
        User u = createUser(user);
        UserGroup g = createGroup(name);
        g.addUser(u);
        groups.put(g.getName(), g);
        return groups.get(name.toLowerCase());
    }

    /**
     * @return all users
     */
    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    /**
     * @return all groups
     */
    @Override
    public Collection<UserGroup> getGroups() {
        return groups.values();
    }

    @Override
    public User getDefaultUser() {
        return users.get(DEFAULT_USER);
    }

    @Override
    public UserGroup getDefaultGroup() {
        return groups.get(DEFAULT_GROUP);
    }

    /**
     * Check name is null or empty string
     * @param name
     * @throws WrongNameException if name is null or empty string
     */
    private void validateName(String name) throws WrongNameException{
        if (!validName(name))
            throw new WrongNameException(WRONG_NAME);
    }

    /**
     * Check if name is null or empty string
     * @param name
     * @return false if name is null or empty string, or true
     */
    private boolean validName(String name) {
        if ((name == null) || name.isEmpty()) {
            return false;
        }
        return true;
    }
}
