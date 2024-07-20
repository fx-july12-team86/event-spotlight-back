package org.example.eventspotlightback.dto.internal.favorite;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestFavoriteDto {
    @NotNull
    private Long eventId;
    @NotNull
    private Long userId;
}
