package com.filesystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class VirtualFileSystem {

    private static VirtualFileSystem fileSystem;
    private VirtualDirectory root;
    private Access accessManager;

    private VirtualFileSystem() {
        this.root = VirtualFileImpl.createRootDirectory("root");
        accessManager = AccessManager.getInstance();
    }

    public static VirtualFileSystem getFileSystem() {
        if (fileSystem == null) {
            fileSystem = new VirtualFileSystem();
        }
        return fileSystem;
    }

    public VirtualDirectory getRoot() {
        return root;
    }

}
