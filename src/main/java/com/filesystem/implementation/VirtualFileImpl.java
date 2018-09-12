package com.filesystem.implementation;

import com.filesystem.*;
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
     */
    private VirtualFileImpl(VirtualDirectory parent, String name){
        this(parent, name, false);
    }

    /**
     *
     * @param parent parent virtual directory
     * @param name file/directory name
     * @param isDirectory
     */
    private VirtualFileImpl(VirtualDirectory parent, String name, boolean isDirectory){
        this.parent = parent;
        this.name = generateName(parent,name).toLowerCase();
        this.isDirectory = isDirectory;
        if (isDirectory) {
            this.children = new HashMap();
        }
        this.content = null;
//        VirtualFileImpl p = (VirtualFileImpl) parent;
        this.access = parent.getAccess();
        System.out.println("hello");
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
    public VirtualFile createFile(String name) throws WrongNameException, FileAlreadyExistException {
        validateName(name);
        if (this.children.containsKey(name.toLowerCase()))
            throw new FileAlreadyExistException(StringFormatter.format(FILE_EXISTS, name).toString());

        VirtualFileImpl vf = new VirtualFileImpl(this, name);
        if (vf != null) {
            children.put(vf.getName(), vf);
        }
        return vf;
    }

    @Override
    public VirtualDirectory createDirectory(String name) throws WrongNameException, FileAlreadyExistException {
        validateName(name);
        VirtualDirectory vd = new VirtualFileImpl(this, name, true);
        if (vd != null) {
            children.put(vd.getName(), vd);
        }
        return vd;
    }

    @Override
    public VFile getFile(String name) {
        VFile result = this.children.get(name);
        if (result == null){
            for (VFile f : this.children.values()) {
                if (f.isDirectory()){
                    result = ((VirtualDirectory) f).getFile(name);
                }
            }
        }
        return result;
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
    public void rename(String newName) throws WrongNameException, FileAlreadyExistException {
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

    public FileAccess getAccess(){
        return this.access;
    }

    /**
     *
     * @param name root directory name
     * @return virtual directory
     */
    static VirtualDirectory createRootDirectory(String name) {
        VirtualDirectory vd = new VirtualFileImpl(name);
        return vd;
    }

    /**
     *
     * @param name
     * @throws WrongNameException if name is null or empty string
     * @throws FileAlreadyExistException if file/directory with this name already exists in this directory
     */
    private void validateName(String name) throws WrongNameException, FileAlreadyExistException {
        if (!validName(name))
            throw new WrongNameException(WRONG_NAME);
        if ((this.children != null) && this.children.containsKey(name.toLowerCase()))
            throw new FileAlreadyExistException(StringFormatter.format(FILE_EXISTS, name).toString());

    }

    /**
     * Check if name is null or empty string
     * @param name
     * @return false if name is null or empty string, or true
     */
    private boolean validName(String name) {
        if ((name == null) || name.isEmpty()) {
            return false;
        }
        return true;
    }
}
