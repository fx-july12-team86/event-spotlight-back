package org.example.eventspotlightback.exception;

public class DropboxException extends RuntimeException {
    public DropboxException(String message, Exception e) {
        super(message, e);
    }
}
