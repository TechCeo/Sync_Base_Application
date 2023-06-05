package com.xxm.syncbase.core.usermgt.exception;

import com.xxm.syncbase.core.exceptions.SyncBaseException;

public class AppUserServiceException extends SyncBaseException {
    String message;
    Object obj;

    public AppUserServiceException() {
        super("Failed to perform the requested action");
    }

    public AppUserServiceException(Throwable cause) {
        super("Failed to perform the requested action", cause);
    }

    public AppUserServiceException(String message) {
        this.message = message;
    }

    public AppUserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppUserServiceException(String message, Object obj) {
        this.message = message;
        this.obj = obj;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return this.obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
