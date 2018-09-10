package com.filesystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Access {

    private final Map<UserGroup, Set<Permission>> accessRights = new HashMap<>();

    public boolean hasAccess(Permission p, UserGroup group){
        boolean result = false;
        if (accessRights.containsKey(group)){
            Set<Permission> pSet = accessRights.get(group);
            if (pSet.contains(p)) result = true;
        }
        return result;
    }

    public void setAccess(Permission p, UserGroup group){
        Set<Permission> permissionSet = accessRights.get(group);
        if (permissionSet == null) {
            permissionSet = new HashSet<>();
        }
        permissionSet.add(p);
        accessRights.put(group, permissionSet);
    }
}
