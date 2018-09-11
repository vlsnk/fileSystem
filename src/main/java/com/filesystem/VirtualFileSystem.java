package com.filesystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class VirtualFileSystem {

    private static VirtualFileSystem fileSystem;
    private VirtualDirectory root;
    private Map<String, UserGroup> groups;

    private VirtualFileSystem() {
        this.root = VirtualFileImpl.createRootDirectory("root");
        this.groups = new HashMap<>();
    }

    public static VirtualFileSystem getFileSystem() {
        if (fileSystem == null) {
            fileSystem = new VirtualFileSystem();
        }
        return fileSystem;
    }

    public Collection<UserGroup> getUserGroups() {
        VirtualFileSystem system = getFileSystem();
        system.setGroups();
        return groups.values();
    }

    public VirtualDirectory getRoot() {
        return root;
    }

    public void setGroups(){
        UserGroup group = new UserGroup();
        groups.put(group.getName(), group);
    }
}
