package com.filesystem.implementation;

import com.filesystem.FileAccess;
import com.filesystem.UserInterface;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Contain access rights for VirtualFile
 */
class FileAccessImpl implements FileAccess {

    private final Map<UserInterface, Set<Permission>> accessRights;

    public FileAccessImpl() {
        this.accessRights = new HashMap<>();
    }

    private FileAccessImpl(Map<UserInterface, Set<Permission>> rights) {
        this.accessRights = rights;
    }


    /**
     * check if user/group has permission to access this file/directory
     * @param p Permission
     * @param user user/group object
     * @return true if user/group has access or false
     */
    @Override
    public boolean hasAccess(Permission p, UserInterface user){
        boolean result = false;
        if (accessRights.containsKey(user)){
            Set<Permission> pSet = accessRights.get(user);
            boolean b1 = (pSet != null);
            boolean b2 = pSet.contains(p);
            if (b1 && b2) {
                result = true;
            }
        }
        else {
            if (user instanceof User){
                result = hasAccessGroup(p, (User) user);
            }
        }
        return result;
    }

    @Override
    public void setAccess(Permission p, UserInterface user){
        Set<Permission> permissionSet = accessRights.get(user);
        if (permissionSet == null) {
            permissionSet = new HashSet<>();
        }
        permissionSet.add(p);
        accessRights.put(user, permissionSet);
    }

    @Override
    public void removeAccess(Permission p, UserInterface user) {
        Set<Permission> permissionSet = accessRights.get(user);
        if (permissionSet != null) {
            permissionSet.remove(p);
        }
    }

    @Override
    public void removeAccess(UserInterface user) {
        accessRights.remove(user);
    }

    @Override
    public FileAccess getDefault() {
        return null;
    }

    static FileAccess copyAccess(FileAccess oldAccess){
        Map<UserInterface, Set<Permission>> oldRights = ((FileAccessImpl) oldAccess).accessRights;
        Map<UserInterface, Set<Permission>> newRights = new HashMap<>();

        for (Entry<UserInterface, Set<Permission>> entry : oldRights.entrySet()) {
            Set<Permission> newSet = new HashSet<>();
            Set<Permission> oldSet = entry.getValue();
            oldSet.forEach(newSet::add);
            newRights.putIfAbsent(entry.getKey(), newSet);
        }
        FileAccessImpl newAccess = new FileAccessImpl(newRights);
        return newAccess;
    }

    /**
     *
     * @param p
     * @param user
     * @return
     */
    private boolean hasAccessGroup(Permission p, User user){
        UserGroup group = searchGroup(user);
        if (group == null) return  false;
        return hasAccess(p, group);
    }

    /**
     * search group that contains user
     * @param user
     * @return group
     */
    private UserGroup searchGroup(User user){
        UserGroup result = null;
        for (UserInterface i : accessRights.keySet()) {
            if (i instanceof UserGroup) {
                UserGroup g = (UserGroup) i;
                if (g.containUser(user)) {
                    result = g;
                    if (result != null) break;
                }
            }
        }
        return  result;
    }
}
