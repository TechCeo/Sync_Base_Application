package com.xxm.syncbase.core.usermgt.exception;

import com.xxm.syncbase.core.exceptions.SyncBaseException;

public class UserNotFoundException extends SyncBaseException {
    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(Throwable cause) {
        super("User not found", cause);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}