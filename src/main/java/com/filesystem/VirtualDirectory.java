package com.filesystem;

import java.util.Collection;

public interface VirtualDirectory extends VFile {

    VirtualFile createFile(String name) throws WrongNameException;
    VirtualDirectory createDirectory(String name) throws WrongNameException;
//    VirtualDirectory createRootDirectory(String name) throws WrongNameException;
    VFile getFile(String name);
    Collection<VFile> getAllFiles();
    boolean hasFile(String name);
}
