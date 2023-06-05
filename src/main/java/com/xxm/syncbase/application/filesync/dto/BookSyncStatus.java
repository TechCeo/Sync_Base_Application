package com.xxm.syncbase.application.filesync.dto;

public enum BookSyncStatus {
    STARTED,
    PROCESSING,
    PAUSED,
    COMPLETED,
    STOPPED;

    private BookSyncStatus() {
    }
}