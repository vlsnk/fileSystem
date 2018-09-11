package com.filesystem;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.binding.StringFormatter;

import java.util.*;

public class VirtualFileImpl implements VirtualFile, VirtualDirectory {

    private VirtualDirectory parent;
    private String name;
    private boolean isDirectory;
    private final Map<String, VFile> children;
    private byte[] content;
    private final Access access;
    private static final String COPY = "_copy";
    private static final String FILE_EXISTS = "File with name - %s already exists";

    public VirtualFileImpl(@NotNull VirtualDirectory parent, String name) {
        this.parent = parent;
        this.name = generateName(parent, name).toLowerCase();
        this.isDirectory = false;
        this.children = null;
        this.access = ((VirtualFileImpl) parent).getAccess();
    }

    public VirtualFileImpl(@NotNull VirtualDirectory parent, String name, boolean isDirectory) {
        this.parent = parent;
        this.name = generateName(parent,name).toLowerCase();
        this.isDirectory = isDirectory;
        this.children = new HashMap();
        this.content = null;
        this.access = ((VirtualFileImpl) parent).getAccess();
    }

    /**
     * Constructor for root directory
     *
     * @param name
     */
    private VirtualFileImpl(String name) {
        this.parent = null;
        this.name = name.toLowerCase();
        this.isDirectory = true;
        this.children = new HashMap();
        this.content = null;
        this.access = new Access();
    }

    public VirtualFileImpl(@NotNull VirtualDirectory parent, String name, boolean isDirectory, Access access) {
        this.parent = parent;
        this.name = generateName(parent, name).toLowerCase();
        this.isDirectory = isDirectory;
        this.children = new HashMap();
        this.content = null;
        this.access = ((VirtualFileImpl) parent).getAccess();
    }

    /**
        FileAccess implementation
     */

    @Override
    public boolean hasAccess(@NotNull Permission p, @NotNull UserGroup group) {
        return access.hasAccess(p, group);
    }

    @Override
    public void setAccess(@NotNull Permission p, @NotNull UserGroup group) {
        this.access.setAccess(p, group);
        if (isDirectory) {
            for (VFile vf : children.values()) {
                vf.setAccess(p,group);
            }
        }
    }

    @Override
    public FileAccess getDefault() {
        return access.getDefault();
    }

    /**
        VirtualDirectory implementation
     */
    @Override
    public VirtualFile createFile(String name) throws WrongNameException {
        if (this.children.containsKey(name.toLowerCase()))
            throw new WrongNameException(StringFormatter.format(FILE_EXISTS, name).toString());

        VirtualFile vf = new VirtualFileImpl(this, name, false, this.access);
        if (vf != null) {
            children.put(vf.getName(), vf);
        }
        return vf;
    }

    @Override
    public VirtualDirectory createDirectory(String name) throws WrongNameException {

        VirtualDirectory vd = new VirtualFileImpl(this, name, true, this.access);
        if (vd != null) {
            children.put(vd.getName(), vd);
        }
        return vd;
    }

    @Override
    public VFile getFile(String name) {
        return this.children.get(name);
    }

    @Override
    public Collection<VFile> getAllFiles() {
        if (children != null) {
            return children.values();
        }
        return null;
    }

    @Override
    public boolean hasFile(String name) {
        if (children != null && children.containsKey(name)){
            return true;
        }
        return false;
    }

    /**
        VirtualFile implementation
     */
    @Override
    public void loadData(byte[] data) {
        this.content = data;
    }

    @Override
    public byte[] getData() {
        return this.content;
    }

    /**
        VFile implementation
     */
    @Override
    public void rename(String newName) {
        this.name = newName.toLowerCase();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isDirectory() {
        return isDirectory;
    }

    /**
    Check if File\directory with this name is already exists in parent directory,
    if true it generates new name
     */
    private String generateName(VirtualDirectory dir, String name){
        String result = name;
        if (dir.hasFile(name)) {
            result = name + COPY;
        }
        return result;
    }

    private Access getAccess(){
        return this.access;
    }

    public static VirtualDirectory createRootDirectory(String name) {
        VirtualDirectory vd = new VirtualFileImpl(name);
        return vd;
    }
}
