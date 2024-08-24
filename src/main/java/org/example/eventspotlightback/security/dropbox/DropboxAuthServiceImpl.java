package org.example.eventspotlightback.security.dropbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.config.DropboxTokenProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DropboxAuthServiceImpl implements DropboxAuthService {

    private static final String TOKEN_URL = "https://api.dropboxapi.com/oauth2/token";
    private final DropboxTokenProperties dropboxTokenProperties;
    private String accessToken;
    private long expiryTime;

    @Override
    public String getAccessToken() {
        if (accessToken == null || System.currentTimeMillis() > expiryTime) {
            refreshAccessToken();
        }
        return accessToken;
    }

    @Override
    public void refreshAccessToken() {
        HttpClient httpClient = HttpClient.newHttpClient();
        String encodedAuth = encodeAuth(
                dropboxTokenProperties.getAppKey(),
                dropboxTokenProperties.getAppSecret());
        HttpRequest request = buildHttpRequest(
                encodedAuth,
                dropboxTokenProperties.getRefreshToken());

        try {
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            parseAndSaveToken(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to refresh access token", e);
        }
    }

    private String encodeAuth(String appKey, String appSecret) {
        String auth = appKey + ":" + appSecret;
        return Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
    }

    private HttpRequest buildHttpRequest(String encodedAuth, String refreshToken) {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.dropbox.com/oauth2/token"))
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers
                        .ofString("grant_type=refresh_token&refresh_token=" + refreshToken))
                .build();
    }

    private void parseAndSaveToken(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            accessToken = root.path("access_token").asText();
            int expiresIn = root.path("expires_in").asInt();
            expiryTime = System.currentTimeMillis() + (expiresIn * 1000L);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse token response", e);
        }
    }
}
