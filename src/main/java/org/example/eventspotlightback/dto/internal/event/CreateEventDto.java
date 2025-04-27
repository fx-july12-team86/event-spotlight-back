package org.example.eventspotlightback.dto.internal.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateEventDto {
    @NotBlank
    @Size(max = 14, message = "The event name cannot exceed 14 characters")
    private String title;
    @NotNull
    private Long descriptionId;
    @NotNull
    private Long userId;
    @NotNull
    private Long contactId;
    @NotNull
    private Long addressId;
    @NotNull
    private Set<Long> photoIds;
    private Set<Long> categoryIds;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    @Positive
    private BigDecimal price;
    private Boolean isOnline = false;
}
