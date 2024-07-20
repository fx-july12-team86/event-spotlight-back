package org.example.eventspotlightback.dto.internal.favorite;

import java.util.List;
import lombok.Data;
import org.example.eventspotlightback.dto.internal.event.EventDto;

@Data
public class FavoriteDto {
    private long id;
    private List<EventDto> events;
}
