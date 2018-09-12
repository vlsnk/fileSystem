package com.filesystem.implementation;

public class FileAlreadyExistException extends Exception {

    /**
     * throws when virtual file/directory has wrong name value
     * @param message
     */
    public FileAlreadyExistException(String message) {
        super(message);
    }
}
