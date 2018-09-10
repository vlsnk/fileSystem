package com.filesystem;

import com.sun.istack.internal.NotNull;

import java.util.List;

public class VirtualFile implements FileAccess {

    private String name;
    private boolean isDirectory;
    private List<VirtualFile> children;
    private byte[] content;
    private final Access access = new Access();

    public VirtualFile(String path) {

    }


    @Override
    public boolean hasAccess(@NotNull Permission p, @NotNull UserGroup group) {
        return access.hasAccess(p, group);
    }

    @Override
    public void setAccess(@NotNull Permission p, @NotNull UserGroup group) {
        access.setAccess(p, group);
    }
}
