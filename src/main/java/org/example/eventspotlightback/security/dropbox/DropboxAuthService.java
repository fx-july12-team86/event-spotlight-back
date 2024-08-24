package org.example.eventspotlightback.security.dropbox;

public interface DropboxAuthService {
    String getAccessToken();

    void refreshAccessToken();
}
