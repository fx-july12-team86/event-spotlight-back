package org.example.eventspotlightback.dto.internal.my.events;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddMyEventDto {
    @NotNull
    private Long eventId;
    @NotNull
    private Long userId;
}
