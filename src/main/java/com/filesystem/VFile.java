package com.filesystem;

public interface VFile extends FileAccess {

    void rename(String newName) throws WrongNameException;
    String getName();
    boolean isDirectory();
}
