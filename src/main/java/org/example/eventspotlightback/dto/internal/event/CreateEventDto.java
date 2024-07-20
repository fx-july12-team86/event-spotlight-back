package org.example.eventspotlightback.dto.internal.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class CreateEventDto {
    @NotBlank
    private String title;
    @NotNull
    private Long descriptionId;
    @NotNull
    private Long userId;
    @NotNull
    private Long addressId;
    private Set<Long> photoIds;
    private Set<Long> categoryIds;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private BigDecimal price;
    private boolean isOnline;
}
