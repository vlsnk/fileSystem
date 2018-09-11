package com.filesystem;

import javafx.beans.binding.StringExpression;

public class WrongNameException extends Exception {

    public WrongNameException(String message) {
        super(message);
    }
}
