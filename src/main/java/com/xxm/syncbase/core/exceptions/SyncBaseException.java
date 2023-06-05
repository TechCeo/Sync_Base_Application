package com.xxm.syncbase.core.exceptions;


public class SyncBaseException extends RuntimeException {
    public SyncBaseException() {
        super("Failed to perform the requested action");
    }

    public SyncBaseException(Throwable cause) {
        super("Failed to perform the requested action", cause);
    }

    public SyncBaseException(String message) {
        super(message);
    }

    public SyncBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
