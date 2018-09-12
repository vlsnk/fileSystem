package com.filesystem.implementation;

import com.filesystem.FileAccess;
import com.filesystem.UserInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Contain access rights for VirtualFile
 */
class FileAccessImpl implements FileAccess {

    private final Map<UserInterface, Set<Permission>> accessRights = new HashMap<>();

    @Override
    public boolean hasAccess(Permission p, UserInterface group){
        boolean result = false;
        if (accessRights.containsKey(group)){
            Set<Permission> pSet = accessRights.get(group);
            if ((pSet != null) && pSet.contains(p)) result = true;
        }
        return result;
    }

    @Override
    public void setAccess(Permission p, UserInterface group){
        Set<Permission> permissionSet = accessRights.get(group);
        if (permissionSet == null) {
            permissionSet = new HashSet<>();
        }
        permissionSet.add(p);
        accessRights.put(group, permissionSet);
    }

    @Override
    public FileAccess getDefault() {
        return null;
    }
}
