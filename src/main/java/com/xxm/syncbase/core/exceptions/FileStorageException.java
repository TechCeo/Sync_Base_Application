package com.xxm.syncbase.core.exceptions;


public class FileStorageException extends RuntimeException {
    public FileStorageException() {
        super("Failed to perform the requested action");
    }

    public FileStorageException(Throwable cause) {
        super("Failed to perform the requested action", cause);
    }

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}