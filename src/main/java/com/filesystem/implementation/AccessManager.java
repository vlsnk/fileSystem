package com.filesystem.implementation;

import com.filesystem.Access;
import com.filesystem.UserInterface;

import java.util.*;

/**
 * Contain all users and groups for VirtualFileSystem
 */
public class AccessManager implements Access {

    private static AccessManager instance;
    private Map<String, User> users;
    private Map<String, UserGroup> groups;
    private final static String DEFAULT_USER = "defaultuser";
    private final static String DEFAULT_GROUP = "defaultgroup";
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

    static AccessManager getInstance(){
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
        return users.get(name.toLowerCase());
    }

    /**
     * remove user/group? return removed object
     * @param u UserInterface object
     */
    @Override
    public UserInterface removeUser(UserInterface u) {
        if (u instanceof User) {
            return removeUser((User) u);
        } else {
            return removeGroup((UserGroup) u);
        }
    }

    /**
     * remove user
     * @param u User object
     */
    private User removeUser(User u){
        User user = users.get(u.getName());
        if (user != null) {
            for (UserGroup g : groups.values()) {
                if (g.containUser(user))
                    g.removeUser(user);
            }
            users.remove(user.getName());
        }
        return user;
    }

    /**
     * remove group
     * @param g UserGroup object
     */
    private UserGroup removeGroup(UserGroup g) {
        UserGroup group = groups.remove(g.getName());
        return group;
    }
    /**
     * Search user with this name
     * @param name username
     * @return user from users collection or null
     */
    @Override
    public User findUser(String name) {
        return users.get(name.toLowerCase());
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
        return groups.get(name.toLowerCase());
    }

    /**
     * Add user to users collection or return user with this name
     * @param group groupname
     * @return user from users collection
     */
    private UserGroup createGroup(UserGroup group) {
        if (!groups.containsKey(group.getName())) {
            groups.put(group.getName(), group);
        }
        return groups.get(group.getName());
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

    /**
     *
     * @return default user
     */
    @Override
    public User getDefaultUser() {
        return users.get(DEFAULT_USER);
    }

    /**
     * Default group contains default user
     * @return default group
     */
    @Override
    public UserGroup getDefaultGroup() {
        return groups.get(DEFAULT_GROUP);
    }

    /**
     * add user to group
     * @param user
     * @param group
     */
    @Override
    public void addToGroup(User user, UserGroup group) {
        UserGroup g = groups.get(group.getName());
        if (g == null){
            g = createGroup(group);
        }
        User u = users.get(user.getName());
        if (u == null) {
            u = createUser(user);
        }
        g.addUser(u);
    }

    /**
     * remove user from group
     * @param user
     * @param group
     */
    @Override
    public void removeFromGroup(User user, UserGroup group) {
        UserGroup g = groups.get(group.getName());
        if (g == null){
            g = createGroup(group);
        }
        User u = users.get(user.getName());
        if (u == null) {
            u = createUser(user);
        }
        g.removeUser(u);
    }

    /**
     * Check if user consists in group
     * @param user
     * @param group
     * @return
     */
    @Override
    public boolean consistIn(User user, UserGroup group) {
        UserGroup g = groups.get(group.getName());
        User u = users.get(user.getName());
        if (g.getUsers().contains(u)) {
            return true;
        }
        return false;
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
        if (name != null){
            String n = name.replaceAll("\\s+","");
            if (!n.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
