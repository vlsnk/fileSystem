package com.filesystem;

import com.sun.istack.internal.NotNull;
import com.sun.javafx.binding.StringFormatter;

import java.util.*;

/**
 * Virtual file implementation
 *
 */
public class VirtualFileImpl implements VirtualFile, VirtualDirectory {

    private VirtualDirectory parent;
    private String name;
    private boolean isDirectory;
    private Map<String, VFile> children;
    private byte[] content;
    private FileAccess access;
    private static final String COPY = "_copy";
    private static final String FILE_EXISTS = "File with name - %s already exists";
    private static final String WRONG_NAME = "File name is not valid";

    /**
     * Create VirtualFile
     * @param parent parent virtual directory
     * @param name file name
     * @throws WrongNameException if name value is not valid (isEmpty or null)
     */
    public VirtualFileImpl(VirtualDirectory parent, String name) throws WrongNameException{
        new VirtualFileImpl(parent, name, false);
//        validateName(name);
//        this.parent = parent;
//        this.name = generateName(parent, name).toLowerCase();
//        this.isDirectory = false;
//        this.children = null;
//        this.content = null;
//        this.access = ((VirtualFileImpl) parent).getAccess();
    }

    /**
     *
     * @param parent parent virtual directory
     * @param name file/directory name
     * @param isDirectory
     * @throws WrongNameException
     */
    public VirtualFileImpl(VirtualDirectory parent, String name, boolean isDirectory) throws WrongNameException {
        validateName(name);
        this.parent = parent;
        this.name = generateName(parent,name).toLowerCase();
        this.isDirectory = isDirectory;
        this.children = new HashMap();
        this.content = null;
        this.access = ((VirtualFileImpl) parent).getAccess();
    }

    public VirtualFileImpl(VirtualDirectory parent, String name, boolean isDirectory, FileAccess access) throws WrongNameException {
        validateName(name);
        this.parent = parent;
        this.name = generateName(parent, name).toLowerCase();
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
        this.access = new FileAccessImpl();
    }

    /**
        FileAccess implementation
     */

    @Override
    public boolean hasAccess(Permission p,  UserInterface user) {
        return access.hasAccess(p, user);
    }

    @Override
    public void setAccess(Permission p, UserInterface user) {
        this.access.setAccess(p, user);
        if (isDirectory) {
            for (VFile vf : children.values()) {
                vf.setAccess(p,user);
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
        validateName(name);
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
        validateName(name);
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
    public void rename(String newName) throws WrongNameException{
        validateName(newName);
        if ((parent != null) && !parent.hasFile(newName)){
            this.name = newName.toLowerCase();
        } else throw new WrongNameException(StringFormatter.format(FILE_EXISTS, name).toString());

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
    * Check if File\directory with this name is already exists in parent directory,
    * if true it generates new name
     */
    private String generateName(VirtualDirectory dir, String name){
        String result = name;
        if (dir.hasFile(name)) {
            result = name + COPY;
        }
        return result;
    }

    private FileAccess getAccess(){
        return this.access;
    }

    /**
     *
     * @param name root directory name
     * @return virtual directory
     */
    public static VirtualDirectory createRootDirectory(String name) {
        VirtualDirectory vd = new VirtualFileImpl(name);
        return vd;
    }

    private void validateName(String name) throws WrongNameException {
        if (!validName(name))
            throw new WrongNameException(WRONG_NAME);
    }
    private boolean validName(String name) {
        if ((name == null) || name.isEmpty()) {
            return false;
        }
        return true;
    }
}
