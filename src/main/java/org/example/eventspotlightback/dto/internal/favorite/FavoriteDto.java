package org.example.eventspotlightback.dto.internal.favorite;

import java.util.List;
import lombok.Data;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDto;

@Data
public class FavoriteDto {
    private long id;
    private List<SimpleEventDto> events;
}
