package com.filesystem;

import com.filesystem.implementation.FileAlreadyExistException;
import com.filesystem.implementation.WrongNameException;

import java.util.Collection;

public interface VirtualDirectory extends VFile {

    /**
     * Create file inside virtual directory
     * @param name file name
     * @return created virtual file
     * @throws WrongNameException if file/directory with this name already exists inside virtual directory
     */
    VirtualFile createFile(String name) throws WrongNameException, FileAlreadyExistException;

    /**
     * Create new directory inside virtual directory
     * @param name directory name
     * @return created virtual directory
     * @throws WrongNameException if file/directory with this name already exists inside virtual directory
     */
    VirtualDirectory createDirectory(String name) throws WrongNameException, FileAlreadyExistException;

    /**
     * Search file/directory with this name inside virtual directory and sub-directories
     * @param name file/directory name
     * @return found file/directory or null if not exists
     */
    VFile getFile(String name);

    /**
     * @return all virtual files and directories inside this virtual directory
     */
    Collection<VFile> getAllFiles();

    /**
     * @param name file/directory name
     * @return true if file/directory with this name exists inside virtual direcroty
     *        of false is not
     */
    boolean hasFile(String name);
}
