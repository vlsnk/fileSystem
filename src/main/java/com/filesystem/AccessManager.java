package com.filesystem;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Contain all users and groups for VirtualFileSystem
 */
public class AccessManager implements Access {

    private static AccessManager instance;
    private Map<String, User> users;
    private Map<String, UserGroup> groups;
    private final static String DEFAULT_USER = "DefaultUser";
    private final static String DEFAULT_GROUP = "DefaultGroup";

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
    public User createUser(@NotNull String name){
        if (!users.containsKey(name)) {
            User user = new User(name);
            users.put(user.getName(), user);
        }
        return users.get(name);
    }

    /**
     * Add user to users collection or return user with this name
     * @param user username
     * @return user from users collection
     */
    @Override
    public User createUser(@NotNull User user) {
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
    public User createUser(@NotNull String name, @NotNull UserGroup group) {
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
    public UserGroup createGroup(@NotNull String name) {
        if (!groups.containsKey(name)) {
            UserGroup g = new UserGroup(name);
            groups.put(g.getName(), g);
        }
        return groups.get(name);
    }


    /**
     * Create group or return group with this name
     * @param name groupname
     * @param user add this user to new group
     * @return group from groups collection
     */
    @Override
    public UserGroup createGroup(@NotNull String name, @NotNull User user) {
        User u = createUser(user);
        UserGroup g = createGroup(name);
        g.addUser(u);
        groups.put(g.getName(), g);
        return groups.get(name);
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
}
