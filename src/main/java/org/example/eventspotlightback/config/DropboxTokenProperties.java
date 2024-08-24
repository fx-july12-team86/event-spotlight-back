package org.example.eventspotlightback.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "dropbox")
public class DropboxTokenProperties {
    private String appKey;
    private String appSecret;
    private String refreshToken;
}
