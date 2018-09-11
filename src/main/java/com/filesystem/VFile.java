package com.filesystem;

public interface VFile extends FileAccess {

    /**
     * rename virtual file/group
     * @param newName
     * @throws WrongNameException if this name is already
     */
    void rename(String newName) throws WrongNameException;

    /**
     * @return file/group name
     */
    String getName();

    /**
     * @return true if virtual file is directory
     */
    boolean isDirectory();
}
