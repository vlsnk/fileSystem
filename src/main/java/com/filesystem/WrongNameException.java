package com.filesystem;

public class WrongNameException extends Exception {

    /**
     * throws when virtual file/directory has wrong name value
     * @param message
     */
    public WrongNameException(String message) {
        super(message);
    }
}
