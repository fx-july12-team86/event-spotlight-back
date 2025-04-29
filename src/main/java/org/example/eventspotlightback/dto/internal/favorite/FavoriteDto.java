package org.example.eventspotlightback.dto.internal.favorite;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.eventspotlightback.dto.internal.event.SimpleEventDtoWithFavorite;

@Data
@Accessors(chain = true)
public class FavoriteDto {
    private long id;
    private List<SimpleEventDtoWithFavorite> events;
}
