package org.example.eventspotlightback.exception;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ValidationError {
    private String field;
    private String errorMessage;
    private LocalDateTime timestamp;

    public ValidationError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}
