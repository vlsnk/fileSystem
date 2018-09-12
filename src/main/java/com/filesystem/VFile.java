package com.filesystem;

import com.filesystem.implementation.FileAlreadyExistException;
import com.filesystem.implementation.WrongNameException;

public interface VFile extends FileAccess {

    /**
     * rename virtual file/group
     * @param newName
     * @throws WrongNameException if this name is already
     */
    void rename(String newName) throws WrongNameException, FileAlreadyExistException;

    /**
     * @return file/group name
     */
    String getName();

    /**
     * @return true if virtual file is directory
     */
    boolean isDirectory();

    public FileAccess getAccess();
}
